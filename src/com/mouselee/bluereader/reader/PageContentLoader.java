package com.mouselee.bluereader.reader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.mouselee.bluereader.bo.content.BookCore;

public class PageContentLoader extends AsyncTaskLoader<String[]> {

	private BookCore mBC;

	public PageContentLoader(Context context, BookCore core) {
		super(context);
		mBC = core;
	}

	@Override
	public String[] loadInBackground() {
		
		
		mBC.refreshFontSetting();
		return mBC.parseCurrentPage(102);
//		return null;
	}

	@Override
	protected void onForceLoad() {
		// TODO Auto-generated method stub
		super.onForceLoad();
	}


	@Override
	public void onCanceled(String[] data) {
		// TODO Auto-generated method stub
		super.onCanceled(data);
	}

	@Override
	protected String[] onLoadInBackground() {
		// TODO Auto-generated method stub
		return super.onLoadInBackground();
	}
	
}