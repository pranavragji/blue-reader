/**
 * 
 */
package com.mouselee.bluereader.bo.content;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Paint;
import android.sax.StartElementListener;

/**
 * @author zuokang.li
 *
 */
public class BookCore {

	private EngineTxt mEngine;
	private LBTempProvider temp;
	private long cursor;
	private Charset mCharset;
	private int showWidth;
	private int[] curLines = new int[23];
	
	public BookCore(String path) {
		try {
			mEngine = new EngineTxt(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String[] parseCurrentPage(long pos) {
		if (mEngine == null) {
			return null;
		}
		long beginPara = 0;
		try {
			beginPara = temp.getParagraphPosition(pos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] data = new byte[1024 * 4];
		try {
			mEngine.getCurParagraph(beginPara, data);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		long offset = beginPara;
		String str = new String(data, mCharset);
		Paint paint = new Paint();
		float[] measuredWidth = new float[1];
		int start  = 0;
		int length = str.length();
		List<String> list = new ArrayList<String>(20);
		do {
			int bPoint = paint.breakText(str, start, length, true, showWidth, measuredWidth);
			int breakIndex = str.indexOf("\r\n", start);
			if (breakIndex <= 0 || breakIndex >= bPoint) {
				breakIndex = str.indexOf('\n', start);
			} 
			if (breakIndex <= 0 || breakIndex >= bPoint) {
				breakIndex = str.indexOf('\r', start);
			} 
			
			if (breakIndex <= 0 || breakIndex >= bPoint) {
				start += bPoint;
				list.add(str.substring(start, bPoint -1));
			} else {
				//If the breakIndex is between the start and bpoint, We need to break it.
				start += (breakIndex + 1);
				list.add(str.substring(start, breakIndex));
			}
			
			offset += start;
			
			
		} while (start < length -1);
		String[] result = new String[list.size()];
		list.toArray(result);
		list.clear();
		return result;
		
	}
	
}
