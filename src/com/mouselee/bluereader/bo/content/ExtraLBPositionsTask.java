/**
 * 
 */
package com.mouselee.bluereader.bo.content;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;










import com.mouselee.bluereader.util.TempleFileConifgs;

import android.annotation.SuppressLint;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

/**
 * @author zuokang.li
 *
 */
public class ExtraLBPositionsTask {
	
	private String bookPath;
	private String bookName;
	private String tempsFilePath;
	private String charsetString;
	
	
	private final static Charset UTF16BE = Charset.forName("UTF-16BE");
	private final static Charset UTF16LE = Charset.forName("UTF-16LE");
	private final static byte CR = '\r';
	private final static byte LB = '\n';
	private final static int ANSI_BUFF_SIZE = 1024 << 2;
	private final static int UTF16_BUFF_SIZE = ANSI_BUFF_SIZE << 1;
	
	
	public ExtraLBPositionsTask(String bookPath, String tempsFilePath, String charsetString) {
		this.bookPath = bookPath;
		this.tempsFilePath = tempsFilePath;
		this.charsetString = charsetString;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void extraing() {
//		long time1 = SystemClock.elapsedRealtime();
		if (TextUtils.isEmpty(bookPath)) {
			return;
		}
		InputStream is = null;
		DataOutputStream os = null;
		try {
			is = new FileInputStream(bookPath);
			os = new DataOutputStream(new FileOutputStream(tempsFilePath));
			getBookLBPositions(is, os,  Charset.forName(charsetString));
			//savePositionsToFile(positions, new File("/sdcard/bluereader/temps/temp.tmp")) ;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
//			long time2 = SystemClock.elapsedRealtime();
//			Log.d("aaron", "cost time :" + (time2 - time1));
		}
		

	}
	
	private void getBookLBPositions(InputStream is, DataOutputStream os, Charset charset) throws IOException {
			
		byte[] b ;
		if (isTwoBytesCharset(charset)) {
			
			b= new byte[UTF16_BUFF_SIZE];
		} else {
			b = new byte[ANSI_BUFF_SIZE];
		}
		long fileLength = is.available();
		long curPosition = 0;
		List<Integer> indexes = new ArrayList<Integer>();
		while(is.read(b) > -1) {
			//String str = new String(b, charset);
			/*if (equalsCR(b)) {
				long curPosition = fileLength - is.available();
//				indexes.add(curPosition);
				temp = curPosition;
			} else if (equalsLB(b)) {
				long curPosition = fileLength - is.available();
				if (!(temp == curPosition - b.length)) {
//					indexes.add(curPosition);
				}
				temp = -1;
			} else {
				temp = -1;
			}*/
			getReturnIndex(b, indexes, curPosition);
			for (Integer v : indexes) {
				os.writeInt(v);
			}
			curPosition = fileLength - is.available();
			//Log.d("aaron", "indexes "+indexes.toString());
			indexes.clear();
		}
		os.flush();
		return ;
	}
	
	private void getReturnIndex(byte[] buf, List<Integer> indexList, long firstIndex) {
		int size = buf.length;
		if (size == ANSI_BUFF_SIZE) {
			// for one byte
			int loop = 1;
			int temp = -1;
			for (int i = 0 ; i < size; i +=loop ) {
				if (buf[i] == CR) {
					indexList.add((int) (firstIndex + i * loop));
					temp = i;
				} else if (buf[i] == LB) {
					if (!(temp == i - loop)) {
						indexList.add((int) (firstIndex + i * loop));
					}
					temp = -1;
				} else {
					temp = -1;
				}
			}
		} else {
			// for two bytes
			int loop = 2;
			int temp = -1;
			for (int i = 0 ; i < size; i +=loop ) {
				if (buf[i] == CR || buf[i + 1]== CR) {
					indexList.add((int) (firstIndex + i * loop));
					temp = i;
				} else if (buf[i] == LB || buf[i + 1]== LB) {
					if (!(temp == i - loop)) {
						indexList.add((int) (firstIndex + i * loop));
					}
					temp = -1;
				} else {
					temp = -1;
				}
			}
		}
	}
	
	private boolean isTwoBytesCharset(Charset charset) {
 		String name = charset.name();
		if (name.equals(UTF16BE)|| name.equals(UTF16LE)) {
			return true;
		}
		return false;
	}
	
	private static boolean equalsCR(byte[] b) {
		if (b.length == 1) {
			if (b[0] == CR);
				return true;
		} else if (b.length == 2) {
			if (b[0] == CR || b[1] == CR);
				return true;
		}
		return false;
	}
	
	private static boolean equalsLB(byte[] b) {
		if (b.length == 1) {
			if (b[0] == LB);
				return true;
		} else if (b.length == 2) {
			if (b[0] == LB || b[1] == LB);
				return true;
		}
		return false;
	}
	
	private void savePositionsToFile(List<Integer> positions, File file) throws IOException {
//			RandomAccessFile file = new RandomAccessFile("D:\\test.txt", "rw");
//			new File("D:\\Documents", name+".txt");
			
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
			for (Integer v : positions) {
				dos.writeInt(v);
			}
			dos.flush();
			dos.close();
	}
	
	

}
