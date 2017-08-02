package com.mouselee.bluereader.act;


import com.mouselee.bluereader.R;
import com.mouselee.bluereader.bo.content.BookCore;
import com.mouselee.bluereader.reader.PageContentLoader;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.TextView;

public class ReaderActivity extends Activity implements LoaderManager.LoaderCallbacks<String[]>  {
	
	public static final String KEY_BOOKPATH = "bookPath";
	private TextView textContent;
	
	private BookCore mBC;
	private float fontSizeSp;
	
	private Handler readerHander = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reader);
		textContent = (TextView) findViewById(R.id.textContent);
		fontSizeSp = 16.f;
		Intent intent = getIntent();
		final String path = intent.getStringExtra(KEY_BOOKPATH);
		if (TextUtils.isEmpty(path)) {
			finish();
			return;
		}
		readerHander.post(new Runnable() {
			
			@Override
			public void run() {
				mBC = new BookCore(path);
				mBC.setTextSize(fontSizeSp);
				mBC.setShowSize(textContent.getWidth(), textContent.getHeight());
				mBC.setCharast("GBK");
				LoaderManager lm = getLoaderManager();
				lm.initLoader(1, null, ReaderActivity.this).forceLoad();
				
			}
		});
	}
	

	@Override
	public Loader<String[]> onCreateLoader(int id, Bundle args) {
		textContent.setText(null);
		return new PageContentLoader(ReaderActivity.this, mBC);
	}




	@Override
	public void onLoadFinished(Loader<String[]> loader, String[] data) {
		for (String str : data) {
			textContent.setText(str + "\n");
		}
		
	}




	@Override
	public void onLoaderReset(Loader<String[]> loader) {
		// TODO Auto-generated method stub
		
	}

}
