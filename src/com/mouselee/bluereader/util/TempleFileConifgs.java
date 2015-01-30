/**
 * 
 */
package com.mouselee.bluereader.util;

import java.io.File;

import android.os.Environment;

/**
 * @author zuokang.li
 *
 */
public class TempleFileConifgs {
	
	public static final String BUFFER_ROOT = Environment.getExternalStorageDirectory().getPath() + "/bluereader";
	
	public static final String TEMP_PATH = BUFFER_ROOT + "/temps";

	public static File getBookTempPath(int id, String name, String path) {
		File folder = new File(TEMP_PATH);
		if(!folder.exists()) {
			folder.mkdirs();
		}
		String tempName;
		if (id <= 0) {
			tempName = name + System.currentTimeMillis() + ".tmp";
		} else {
			tempName = "ID" + id + name + System.currentTimeMillis() + ".tmp";
		}
		return new File(folder, tempName);
	}
}
