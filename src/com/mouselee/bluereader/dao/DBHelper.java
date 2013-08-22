package com.mouselee.bluereader.dao;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;


public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "reader.db";
	private static DBHelper helper;
	
	private DBHelper(Context context, int versionCode ) {
		super(context, DATABASE_NAME, null, versionCode);
	}

	public static DBHelper getHelper(Context context) {
		if (helper == null) {
			/*-	数据库版本跟软件版本统一，无需每次都卸载再安装
			 * 
			 * */
			
			PackageManager pm = context.getPackageManager();  
	        PackageInfo pi = null;
			try {
				pi = pm.getPackageInfo(context.getPackageName(), 0);
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}  
	        int versionCode = pi.versionCode ; 
			helper = new DBHelper(context, versionCode);
		}
		return helper;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(BookTableConfig.getCreateTableBookSQL());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists "+BookTableConfig.TABLE_NAME);
		onCreate(db);
	}

}
