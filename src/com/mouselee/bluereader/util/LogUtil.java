package com.mouselee.bluereader.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



import com.mouselee.bluereader.BuildConfig;

import android.provider.SyncStateContract.Constants;
import android.util.Log;

public final class LogUtil
{


	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@SuppressWarnings("rawtypes")
	private static ThreadLocal threadLocal = new ThreadLocal()
	{
		protected synchronized Object initialValue()
		{
			return new SimpleDateFormat(DATE_FORMAT, Locale.US);
		}
	};

	public static DateFormat getDateFormat()
	{
		return (DateFormat) threadLocal.get();
	}

	/**
	 * 有些手机日志被锁，就在手机中打印日志
	 * 
	 * @param message
	 */
	public static void f(String message, String filePath)
	{
		// 如果日志被锁
		final File file = new File(filePath);
		file.getParentFile().mkdir();
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			writer.write(getDateFormat().format(new Date()));
			writer.write(":");
			writer.write(message);
			writer.write("\r\n");
			writer.flush();
			writer.close();
		}
		catch (IOException e)
		{
		}
	}
}
