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
	private long beginParaPos, endParaPos;
	
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
		
		
		
		try {
			Long[] blockBLs = temp.currentBlockPosition(pos);
			for (int i =0, size = blockBLs.length; i < size; i ++) {
				if (blockBLs[i] != null && blockBLs[i] > pos) {
					if (i > 0) {
						beginParaPos = blockBLs[i - 1];
					} else {
						beginParaPos = 0;
					}
					endParaPos = blockBLs[i];
					break;
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] data = new byte[(int) (endParaPos - beginParaPos)];
		try {
			mEngine.getCurParagraph(beginParaPos, data);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		String str = new String(data, mCharset);
		long offset = getCharPosOfPara(pos, beginParaPos, endParaPos, str.length());
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
			
			if (offset == start) {
				
			}
			
		} while (start < length);
		String[] result = new String[list.size()];
		list.toArray(result);
		list.clear();
		return result;
		
	}
	
	private long getCharPosOfPara(long bytePos, long beginBytePos, long endBytePos, int strLength) {
		return (bytePos - beginBytePos) * strLength / (endBytePos - beginBytePos);
	}
	
}
