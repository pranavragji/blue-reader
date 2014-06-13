/**
 * Written by Aaron Lee at 11:11:17 PM Aug 22, 2013
 */
package com.mouselee.bluereader.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.os.Environment;

/**
 * @author Aaron Lee
 * @Date 11:11:17 PM Aug 22, 2013
 */
public class BookConfig {

	public static final String BASIC_DIRECTOR = Environment
			.getExternalStorageDirectory().getPath()
			+ File.separatorChar
			+ "bluereader";

	public final static String FILE_TYPE_TXT = "txt";

	/**
	 * The static field is all the support format of documents.The key is the
	 * suffix,the value is the format name
	 */
	public static Map<String, String> sFromatSupport;

	static {
		sFromatSupport = new HashMap<String, String>();
		sFromatSupport.put("txt", FILE_TYPE_TXT);
	}

	/**
	 * 
	 * @Author Aaron Lee
	 * @Date 11:26:48 PM Aug 22, 2013
	 * @param file
	 *            This can be filename or file' full path.
	 * @return
	 */
	public static String judgeFileType(String file) {
		String extension = Tools.getExtension(file);
		return sFromatSupport.get(extension.toLowerCase());
	}
}
