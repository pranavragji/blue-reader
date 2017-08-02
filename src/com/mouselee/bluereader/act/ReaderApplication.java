/**
 * 
 */
package com.mouselee.bluereader.act;

import android.app.Application;

/**
 * @author zuokang.li
 *
 */
public class ReaderApplication extends Application {
	
	private static Application sApplication;

	@Override
	public void onCreate() {
		
		super.onCreate();
		sApplication = this;
	}

	public static Application getApplication() {
		return sApplication;
	}

	@Override
	protected void finalize() throws Throwable {
		sApplication = null;
		super.finalize();
	}
	
	
}
