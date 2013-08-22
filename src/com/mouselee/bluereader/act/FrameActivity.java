/**
 * 
 */
package com.mouselee.bluereader.act;

import java.util.HashMap;
import java.util.Map;

import com.mouselee.bluereader.R;
import com.mouselee.bluereader.dao.SharePreferencesDao;
import com.mouselee.bluereader.shell.ListFragment;
import com.mouselee.bluereader.shell.ShellFragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * @author kang
 *
 */
public class FrameActivity extends FragmentActivity {
	
	public static final String STATE_FRAG_SHELL = "shell";
	public static final String STATE_FRAG_LIST = "list";
	
	private String mCurrentTag;
	private Map<String, Fragment> mFragmentMap;

	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shell_main);
		mFragmentMap = new HashMap<String, Fragment>();
		mCurrentTag = SharePreferencesDao.getInstance(this).getLastShellState();
		setShowFragment(mCurrentTag);
	}
	
	private void setShowFragment(String tag) {
		mCurrentTag = tag;
		Fragment fragment = mFragmentMap.get(tag);
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
	}
}
