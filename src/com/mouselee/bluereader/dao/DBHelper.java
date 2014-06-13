package com.mouselee.bluereader.dao;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.os.Environment;


public class DBHelper extends SQLiteOpenHelper {

	private static String DATABASE_NAME = "reader.db";
	private static DBHelper helper;
	private static final int VERSION_CODE = 2;
	
	static{
		if (com.mouselee.bluereader.BuildConfig.DEBUG) {
			File dbFile = new File(Environment.getExternalStorageDirectory(), DATABASE_NAME);
			if (!dbFile.exists())  {
				try {
					dbFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			DATABASE_NAME = dbFile.getPath();
		}
	}
	
	private DBHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION_CODE);
	}

	public static DBHelper getHelper(Context context) {
		if (helper == null) {
			helper = new DBHelper(context);
		}
		return helper;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(BookTableConfig.getCreateTableBookSQL());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (newVersion > oldVersion) {
			db.execSQL("drop table if exists "+BookTableConfig.TABLE_NAME);
			onCreate(db);
		}
	}

}
