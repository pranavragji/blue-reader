/**
 * 
 */
package com.mouselee.bluereader.bo.content;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.mouselee.bluereader.util.TempleFileConifgs;

import android.text.TextUtils;

/**
 * @author zuokang.li
 *
 */
public class ExtraLBPositionsTask {
	
	private String bookPath;
	private String bookName;
	private String charsetString;
	
	
	public ExtraLBPositionsTask(String bookPath, String bookName, String charsetString) {
		this.bookName = bookName;
		this.bookPath = bookPath;
		this.charsetString = charsetString;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void extraing() {
		if (TextUtils.isEmpty(bookName) || TextUtils.isEmpty(bookPath)) {
			return;
		}
		InputStream is = null;
		try {
			is = new FileInputStream(bookPath);
			List<Long> positions =  getBookLBPositions(is, Charset.forName(charsetString));
			savePositionsToFile(positions, TempleFileConifgs.getBookTempPath(0, bookName, bookPath)) ;
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
		}
		

	}
	
	private List<Long> getBookLBPositions(InputStream is, Charset charset) throws IOException {
			
		byte[] b ;
		if (isTwoBytesCharset(charset)) {
			
			b= new byte[2];
		} else {
			b = new byte[1];
		}
		long fileLength = is.available();
		long temp = -1;
		List<Long> indexes = new ArrayList<Long>();
		while(is.read(b) > -1) {
			String str = new String(b, charset);
			if ("\r".equals(str)) {
				long curPosition = fileLength - is.available();
				indexes.add(curPosition);
				temp = curPosition;
			} else if ("\n".equals(str)) {
				long curPosition = fileLength - is.available();
				if (!(temp == curPosition - b.length)) {
					indexes.add(curPosition);
				}
				temp = -1;
			} else {
				temp = -1;
			}
		}
		return indexes;
	}
	
	private boolean isTwoBytesCharset(Charset charset) {
		String name = charset.name();
		if (name.equals("UTF-16BE") || name.equals("UTF-16LE") || name.equals("UTF-16")) {
			return true;
		}
		return false;
	}
	
	private void savePositionsToFile(List<Long> positions, File file) throws IOException {
//			RandomAccessFile file = new RandomAccessFile("D:\\test.txt", "rw");
//			new File("D:\\Documents", name+".txt");
			
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
			for (Long v : positions) {
				dos.writeLong(v);
			}
			dos.flush();
			dos.close();
	}
	
	

}
