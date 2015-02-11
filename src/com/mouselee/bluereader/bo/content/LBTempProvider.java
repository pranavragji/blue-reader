/**
 * 
 */
package com.mouselee.bluereader.bo.content;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import android.view.View;

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
	private int currBlockIndex;
	
	private long blockBegin, blockEnd;

	public LBTempProvider(String tempPath) throws FileNotFoundException {
		super();
		mAccess = new RandomAccessFile(tempPath, "r");
	}
	

	@Deprecated
	public synchronized long getParagraphPosition(long curIndex) throws IOException {
		mAccess.seek(0);
		long prev = -1L, flag = -1L;
		byte[] buffer = new byte[4];
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
	
	public boolean invalidatePos(long curPos) throws IOException {
		if (curPos >= mAccess.length()) {
			return false;
		}
		currBlockIndex = getBlockPos(curPos) - 1 ;
		return true;
	}
	
	@Deprecated
	public synchronized Long[] currentBlockPosition(long curIndex) throws IOException {
		if (curIndex >= mAccess.length()) {
			return null;
		}
		mAccess.seek(0);
		long  flag = -1L;
		int blockPos = getBlockPos(curIndex);
		currBlockIndex = blockPos;
		long blockBegin = BLOCK_SIZE * (long)blockPos;
		long blockEnd = BLOCK_SIZE * (long)(blockPos + 1);
		List<Long> temp = new ArrayList<Long>();
		byte[] buffer = new byte[4];
		while ((flag = readLongValueOfTmpFile(buffer)) > -1) {
			flag = readLongValueOfTmpFile(buffer);
			if (flag > blockEnd) {
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
	
	public Integer[] nextBlockPosition() throws IOException {
		return getBlockPositionFromId(currBlockIndex + 1);
	}
	
	public synchronized Integer[] getBlockPositionFromId(int blockIndex) throws IOException {
		long blockBegin = BLOCK_SIZE * (long)blockIndex;
		if (blockBegin > mAccess.length()) {
			return null;
		}
		long blockEnd = BLOCK_SIZE * (long)(blockIndex + 1);
		mAccess.seek(0);
		int  flag = -1;
		currBlockIndex = blockIndex;
		List<Integer> temp = new ArrayList<Integer>();
		byte[] buffer = new byte[4];
		while ((flag = readLongValueOfTmpFile(buffer)) > -1) {
			flag = readLongValueOfTmpFile(buffer);
			if (flag > blockEnd) {
				temp.add(flag);
				break;
			} else if (flag > blockBegin) {
				temp.add(flag);
			}
		}
		currPosition = mAccess.getFilePointer();
		Integer[] arr = new Integer[temp.size()];
		temp.toArray(arr);
		temp.clear();
		return arr;
	}

	public synchronized long previousParagraphPosition() throws IOException {
		if (currPosition >= 8L) {
			mAccess.seek(currPosition - 8L);
		} else {
			mAccess.seek(0L);
		}
		byte[] buf = new byte[4];
		return readLongValueOfTmpFile(buf);
	}
	
	public synchronized long nextParagraphPosition() throws IOException {
		if (currPosition >= 4L) {
			mAccess.seek(currPosition - 4L);
		} else {
			mAccess.seek(0L);
		}
		byte[] buf = new byte[4];
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

	private int readLongValueOfTmpFile(byte[] buf) throws IOException {
		if (mAccess.read(buf) == 4) {
			return bytesToInt(buf);
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
	
	private int bytesToInt(byte[] src) {
		return (((src[0] & 0xff) << 24) |
                ((src[1] & 0xff) << 16) |
                ((src[2] & 0xff) <<  8) |
                ((src[3] & 0xff) <<  0));

	}

}
