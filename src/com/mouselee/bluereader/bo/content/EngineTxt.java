/**
 * 
 */
package com.mouselee.bluereader.bo.content;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author zuokang.li
 *
 */
public class EngineTxt {

	private RandomAccessFile mAccess;
	
	private long cursorPosition;
	
	public EngineTxt(String file) throws FileNotFoundException {
		mAccess = new RandomAccessFile(file, "r");
		
	}
	
	public long getCurParagraph(long position, byte[] dst) throws IOException {
		mAccess.seek(position);
		mAccess.readFully(dst);
		cursorPosition = mAccess.getFilePointer();
		return cursorPosition;
	} 
}
