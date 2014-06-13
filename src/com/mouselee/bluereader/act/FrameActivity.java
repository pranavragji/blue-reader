/**
 * 
 */
package com.mouselee.bluereader.act;

import java.util.HashMap;
import java.util.Map;

import com.mouselee.bluereader.R;
import com.mouselee.bluereader.bo.ScanFileController;
import com.mouselee.bluereader.dao.BooksDao;
import com.mouselee.bluereader.dao.SharePreferencesDao;
import com.mouselee.bluereader.shell.CursorFragment;
import com.mouselee.bluereader.shell.ListFragment;
import com.mouselee.bluereader.shell.ShellFragment;
import com.mouselee.bluereader.view.PopUpDialogFragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

/**
 * @author kang
 *
 */
public class FrameActivity extends FragmentActivity {
	
	public static final String STATE_FRAG_SHELL = "shell";
	public static final String STATE_FRAG_LIST = "list";
	
	private String mCurrentTag;
	private CursorFragment mCurrentFragment;
	private Map<String, CursorFragment> mFragmentMap;
	private BooksDao dao;
	
	
	//all views
	private DialogFragment loadingDialog;
	private Button btnBookStore;
	private Button btnReflesh;
	private Button btnAbout;
	private Button btnBooklist;
	private Button btnBookshelf;

	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shell_main);
		initViews();
		dao = BooksDao.getInstance(this);
		mFragmentMap = new HashMap<String, CursorFragment>();
		setShowFragment(SharePreferencesDao.getInstance(this).getLastShellState());
//		refreshBookList();
	}



	
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}
	
	public void onButtonClick(View view) {
		switch (view.getId()) {
		case R.id.btnBookStore:

			break;
		case R.id.btnReflesh:
			new RefreshBooks().execute(Environment.getExternalStorageDirectory().getPath());
			break;
		case R.id.btnAbout:

			break;
		case R.id.btnBooklist:
			setShowFragment(STATE_FRAG_LIST);
			break;
		case R.id.btnBookshelf:
			setShowFragment(STATE_FRAG_SHELL);
			break;

		default:
			break;
		}
	}
	
	private void setShowFragment(String tag) {
		if (!TextUtils.equals(mCurrentTag, tag)) {
			mCurrentTag = tag;
			CursorFragment fragment = mFragmentMap.get(tag);
			if (fragment == null) {
				if (STATE_FRAG_SHELL.equals(tag)) {
					fragment  = ShellFragment.newInstance(tag);
				} else if (STATE_FRAG_LIST.equals(tag)) {
					fragment  = ListFragment.newInstance(tag);
				}
				mFragmentMap.put(tag, fragment);
			}
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.fragment_list_content, fragment);
			ft.commit();
			updateTitleTagButton();
			mCurrentFragment = fragment;
			refreshBookList();
		}
	}

	
	private void initViews() {
		// Find View By Id;
		btnBookStore = (Button)findViewById(R.id.btnBookStore);
		btnReflesh   = (Button)findViewById(R.id.btnReflesh );
		btnAbout     = (Button)findViewById(R.id.btnAbout   );
		btnBooklist  = (Button)findViewById(R.id.btnBooklist);
		btnBookshelf = (Button)findViewById(R.id.btnBookshelf);
	}
	
	private void updateTitleTagButton() {
		if (mCurrentTag.equals(STATE_FRAG_SHELL)) {
			btnBookshelf.setBackgroundResource(R.drawable.shelfonclick);
			btnBooklist.setBackgroundResource(R.drawable.listoutclick);
		} else if (mCurrentTag.equals(STATE_FRAG_LIST)) {
			btnBookshelf.setBackgroundResource(R.drawable.shelfoutclick);
			btnBooklist.setBackgroundResource(R.drawable.listonclick);
		}
	}
	
	private void showLoadingDialog() {
		loadingDialog = PopUpDialogFragment.newInstance(PopUpDialogFragment.LOADING);
		loadingDialog.show(getSupportFragmentManager(), "dialog");
	}
	

	/**
	 * 
	 */
	private void refreshBookList() {
		Cursor bookCursor = dao.queryBookList();
		mCurrentFragment.updateCursor(bookCursor);
	}
	
	
	private class RefreshBooks extends AsyncTask<String, Void, Void> {
		
		private ScanFileController controller = ScanFileController.getInstance(FrameActivity.this);

		@Override
		protected Void doInBackground(String... params) {
			if (params != null && params.length >= 1) {
				for (String s: params) {
					controller.scanDirector(s);
				}
			}
			return null;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			loadingDialog.dismiss();
			refreshBookList();
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onCancelled(java.lang.Object)
		 */
		@Override
		protected void onCancelled(Void result) {
			// TODO Auto-generated method stub
			super.onCancelled(result);
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showLoadingDialog();
		}
		
	}

}
