/**
 * 
 */
package com.mouselee.bluereader.bo.content;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;

import com.mouselee.bluereader.act.ReaderApplication;
import com.mouselee.bluereader.util.TempleFileConifgs;
import com.mouselee.bluereader.util.Tools;

import android.graphics.Paint;
import android.sax.StartElementListener;

/**
 * @author zuokang.li
 *
 */
public class BookCore {
	
	private static final String STRCRLB = "\r\n";
	private static final char CHARCR = '\r';
	private static final char CHARLB = '\n';

	private String bookPath;
	private EngineTxt mEngine;
	private LBTempProvider temp;
	private Charset mCharset;
	private int showWidth;	// The showing area' width
	private int showHeight;	// The showing area' height
	private float textSize;	//The current font size, the unit is pix
	private long beginParaPos, endParaPos;	// 本段的开始位置以及结束位置，这个位置指相对于文件的byte position.
	private int curPageCharPos;	// 当前页的相对于本paragraph的char索引；
	private int lineCount;	// The line's count of one page.
	
	private String[] currPageContent;
	private String[] prevPageContent;
	private String[] nextPageContent;
	
	public BookCore(String path) {
		try {
			bookPath = path;
			mEngine = new EngineTxt(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void setShowSize(int width, int height) {
		showWidth = width;
		showHeight = height;
	}

	public void setTextSize(float textSizeSp) {
		textSize = Tools.sp2px(ReaderApplication.getApplication(), textSizeSp);
	}
	
	public void setCharast(String set) {
		mCharset = Charset.forName(set);
	}
	
	public void refreshFontSetting() {
		getLineCount(showHeight, textSize);
		currPageContent = new String[lineCount];
		prevPageContent = new String[lineCount];
		nextPageContent = new String[lineCount];
		
		File temFile = TempleFileConifgs.getBookTempPath(0, "BookName", bookPath);
		if (!temFile.exists()) {
			new ExtraLBPositionsTask(bookPath, temFile.getPath(), mCharset.name()).extraing();;
		}
		try {
			temp = new LBTempProvider(temFile.getPath());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String[] parseCurrentPage(long pos) {
		if (mEngine == null) {
			return null;
		}
		try {
			temp.invalidatePos(pos);
			List<String> content = new ArrayList<String>(lineCount);
			Integer[] blockBLs;
			while((blockBLs = temp.nextBlockPosition()) != null) {	//最外循环，关于blocks的循环；
				int blIndex = 0;
				for (int size = blockBLs.length; blIndex < size; blIndex ++) { 
					if (blockBLs[blIndex] != null && blockBLs[blIndex] > pos) {
						if (blIndex > 0) {
							beginParaPos = blockBLs[blIndex - 1];
						} else {
							beginParaPos = 0;
						}
						endParaPos = blockBLs[blIndex];
						break;
					}
					
				}
				
				parseParagraph(pos, content, blockBLs, blIndex);
				if (content.size() >= lineCount) {
					break;
				}
				
			}
			String[] result = new String[content.size()];
			content.toArray(result);
			content.clear();
			return result;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
		/*try {
			Long[] blockBLs = temp.currentBlockPosition(pos);
			int blsIndex = 0;
			for (int size = blockBLs.length; blsIndex < size; blsIndex ++) {
				if (blockBLs[blsIndex] != null && blockBLs[blsIndex] > pos) {
					if (blsIndex > 0) {
						beginParaPos = blockBLs[blsIndex - 1];
					} else {
						beginParaPos = 0;
					}
					endParaPos = blockBLs[blsIndex];
					break;
				}
				
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
			List<String> list = getPageLinesPosWithOffset(str, offset);
			
			if (list.size() < lineCount) {
				beginParaPos = endParaPos;
				if (blsIndex < blockBLs.length) {
					endParaPos = blockBLs[blsIndex + 1];
				} else {
					// If it is read to the end of the block, get Next Block;
				}
			}
			String[] result = new String[list.size()];
			list.toArray(result);
			list.clear();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;*/
		
	}

	private void parseParagraph(long pos, List<String> content, Integer[] blockBLs,
			int blIndex) {
		while(true) { //2循环，关于paragraph的循环
			byte[] data = new byte[(int) (endParaPos - beginParaPos)];
			try {
				mEngine.getCurParagraph(beginParaPos, data);
			} catch (IOException e) {
				e.printStackTrace();
				return ;
			}
			String str = new String(data, mCharset);	 // the string of current paragraph;
			long offset = getCharPosOfPara(pos, beginParaPos, endParaPos, str.length());
			List<String> list = getPageLinesPosWithOffset(str, offset, lineCount - content.size());
			content.addAll(list);
			if (content.size() < lineCount) {
				long tem = endParaPos;
				if (blIndex < blockBLs.length) {
					endParaPos = blockBLs[blIndex + 1];
				} else {
					// If it is read to the end of the block, get Next Block;
					break;
				}
				beginParaPos = tem;
			} else {
				break;
			}
		}
	}

	private List<String> getPageLinesPosWithOffset(String content, long offset, int requireLineCount) {
		Paint paint = new Paint();
		paint.setTextSize(textSize);
		//float[] measuredWidth = new float[1];
		int start  = 0;
		int lineBegin;
		int length = content.length();
		List<String> list = new ArrayList<String>(requireLineCount);
		do {
			int bPoint = paint.breakText(content, start, length, true, showWidth, null);
			lineBegin = start;
			String str = content.substring(start, bPoint);
			int breakIndex = str.indexOf(STRCRLB);
			if (breakIndex > 0) {
				start += (breakIndex + 2);
			} else if (((breakIndex = str.indexOf(CHARLB)) > 0) || ((breakIndex = str.indexOf(CHARCR)) > 0)) {
				start += (breakIndex + 1);
			} else {
				start += (bPoint + 1);
			}
			
			/*int breakIndex = str.indexOf("\r\n", start);
			if (breakIndex <= 0 || breakIndex >= bPoint) {
				breakIndex = str.indexOf('\n', start);
			} 
			if (breakIndex <= 0 || breakIndex >= bPoint) {
				breakIndex = str.indexOf('\r', start);
			} 
			
			if (breakIndex <= 0 || breakIndex >= bPoint) {
				list.add(str.substring(start, bPoint -1));
				start += bPoint;
			} else {
				//If the breakIndex is between the start and bpoint, We need to break it.
				list.add(str.substring(start, breakIndex));
				start += (breakIndex + 1);
			}*/
			
			if (offset <= start) {
				if (offset == start) {
					// Start to fill line index into lineIndexes[];
					curPageCharPos = lineBegin;
				}
				list.add(content.substring(lineBegin, start));
			}
			
			if (list.size() >= requireLineCount) {
				break;
			}
			
		} while (start < length);
		
		return list;
	}
	
	private List<String> getPageLinesPosWithout(String content) {
		Paint paint = new Paint();
		//float[] measuredWidth = new float[1];
		int start  = 0;
		int lineBegin;
		int length = content.length();
		List<String> list = new ArrayList<String>(lineCount);
		do {
			int bPoint = paint.breakText(content, start, length, true, showWidth, null);
			lineBegin = start;
			String str = content.substring(start, bPoint);
			int breakIndex = str.indexOf(STRCRLB);
			if (breakIndex > 0) {
				start += (breakIndex + 2);
			} else if (((breakIndex = str.indexOf(CHARLB)) > 0) || ((breakIndex = str.indexOf(CHARCR)) > 0)) {
				start += (breakIndex + 1);
			} else {
				start += (bPoint + 1);
			}
			
			list.add(content.substring(lineBegin, start));
			
			if (list.size() >= lineCount) {
				break;
			}
			
		} while (start < length);
		
		return list;
	}
	
	private long getCharPosOfPara(long bytePos, long beginBytePos, long endBytePos, int strLength) {
		if (bytePos > endBytePos) {
			return -1;
		}
		return (bytePos - beginBytePos) * strLength / (endBytePos - beginBytePos);
	}
	
	private void getLineCount(int height, float textSizePix) {
		lineCount = (int) (height / textSizePix);
	}
}
