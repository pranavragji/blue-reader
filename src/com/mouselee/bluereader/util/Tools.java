/**
 * 
 */
package com.mouselee.bluereader.util;

import java.io.File;

/**
 * 
 * @author Aaron Lee
 * @Date 11:05:39 PM Aug 22, 2013
 */
public class Tools {
	/**
	 * 
	 * @Author Aaron Lee
	 * @Date 11:07:24 PM Aug 22, 2013
	 * @param filename Can be full path string or only filename
	 * @return The extension without '.',if not result,it return empty string ""
	 * but not null.
	 */
	public static String getExtension(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');

			if ((i > 0) && (i < (filename.length() - 1))) {
				return filename.substring(i + 1);
			}
		}
		return "";
	}

	public static String trimExtension(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');
			if ((i > -1) && (i < (filename.length()))) {
				return filename.substring(0, i);
			}
		}
		return filename;
	}

	/**
	 * Get file's name removed extension
	 * @Author Aaron Lee
	 * @Date 11:07:24 PM Aug 22, 2013
	 * @param filename Can be full path string or only filename
	 * @return The extension without '.',if not result,it return empty string ""
	 * but not null.
	 */
	public static String getFileNameWithoutExten(String path) {
		int beginIndex = path.lastIndexOf(File.separator) + 1;
		if (beginIndex < 0) {
			beginIndex = 0;
		}
		int endIndex = path.lastIndexOf('.');
		if (endIndex < beginIndex) {
			endIndex = path.length();
		}
		return path.substring(beginIndex, endIndex);
	}

}
