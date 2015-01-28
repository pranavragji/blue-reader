/**
 * 
 */
package com.mouselee.bluereader.bo.content;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zuokang.li
 *
 */
public class LBTempProvider {
	
	private static final int BLOCK_SIZE = 1024 * 10;

	private RandomAccessFile mAccess;

	private long seek;

	private long currPosition;
	
	private int curBlockPosition;
	private long currBlockBegin;
	private long currBlockEnd;

	public LBTempProvider(String tempPath) throws FileNotFoundException {
		super();
		mAccess = new RandomAccessFile(tempPath, "r");
	}

	@Deprecated
	public synchronized long getParagraphPosition(long curIndex) throws IOException {
		mAccess.seek(0);
		long prev = -1L, flag = -1L;
		byte[] buffer = new byte[8];
		while (true) {
			flag = readLongValueOfTmpFile(buffer);
			// begin to check if the flag position;
			if (flag > curIndex) {
				break;
			}
			prev = flag;
		}
		currPosition = mAccess.getFilePointer();
		return prev;
	}
	
	public synchronized Long[] currentBlockPosition(long curIndex) throws IOException {
		mAccess.seek(0);
		long  flag = -1L;
		int blockPos = getBlockPos(curIndex);
		long blockBegin = BLOCK_SIZE * (long)blockPos;
		long blockEnd = BLOCK_SIZE * (long)(blockPos + 1);
		List<Long> temp = new ArrayList<Long>();
		byte[] buffer = new byte[8];
		while ((flag = readLongValueOfTmpFile(buffer)) > -1) {
			flag = readLongValueOfTmpFile(buffer);
			if (flag > blockEnd) {
				currBlockEnd = mAccess.getFilePointer();
				break;
			} else if (flag > blockBegin) {
				temp.add(flag);
			}
		}
		currPosition = mAccess.getFilePointer();
		Long[] arr = new Long[temp.size()];
		temp.toArray(arr);
		temp.clear();
		return arr;
	}
	
	public synchronized Long[] getBlockPositionFromId(int blockId) throws IOException {
		mAccess.seek(0);
		long  flag = -1L;
		long blockBegin = BLOCK_SIZE * (long)blockId;
		long blockEnd = BLOCK_SIZE * (long)(blockId + 1);
		List<Long> temp = new ArrayList<Long>();
		byte[] buffer = new byte[8];
		while ((flag = readLongValueOfTmpFile(buffer)) > -1) {
			flag = readLongValueOfTmpFile(buffer);
			if (flag > blockEnd) {
				temp.add(flag);
				currBlockEnd = mAccess.getFilePointer();
				break;
			} else if (flag > blockBegin) {
				temp.add(flag);
			}
		}
		currPosition = mAccess.getFilePointer();
		Long[] arr = new Long[temp.size()];
		temp.toArray(arr);
		temp.clear();
		return arr;
	}

	public synchronized long previousParagraphPosition() throws IOException {
		if (currPosition >= 16L) {
			mAccess.seek(currPosition - 16L);
		} else {
			mAccess.seek(0L);
		}
		byte[] buf = new byte[8];
		return readLongValueOfTmpFile(buf);
	}
	
	public synchronized long nextParagraphPosition() throws IOException {
		if (currPosition >= 8L) {
			mAccess.seek(currPosition - 8L);
		} else {
			mAccess.seek(0L);
		}
		byte[] buf = new byte[8];
		return readLongValueOfTmpFile(buf);
	}

	public void onClose() {
		if (mAccess != null) {
			try {
				mAccess.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private long readLongValueOfTmpFile(byte[] buf) throws IOException {
		if (mAccess.read(buf) == 8) {
			return bytesToLong(buf);
		} else {
			return -1;
		}
		
	}
	
	private int getBlockPos(long pos) {
		return (int) (pos/BLOCK_SIZE);
	}
	
	private long bytesToLong(byte[] b) {
		 return ((long)b[0] << 56) + ((long)b[1] << 48)
		     + ((long)b[2] << 40) + ((long)b[3] << 32)
		     + ((long)b[4] << 24) + ((long)b[5] << 16)
		     + ((long)b[6] << 8) + b[7];

	}

}
