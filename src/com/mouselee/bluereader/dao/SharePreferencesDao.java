/**
 * 
 */
package com.mouselee.bluereader.dao;


import com.mouselee.bluereader.act.FrameActivity;
import com.mouselee.bluereader.shell.ShellFragment;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 
 * Controller of {@link SharedPreferences} in this app
 * @author kang
 *
 */
public class SharePreferencesDao {
	public static final String SHARE_NAME = "blue_reader";
	public static final String STATE_TAG = "shell_state";
	
	private static SharePreferencesDao instance;
	
	private SharedPreferences sp;
	
	private SharePreferencesDao(Context context) {
		sp = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
	}
	
	public static synchronized SharePreferencesDao getInstance(Context context) {
		if (instance == null) {
			instance = new SharePreferencesDao(context);
		}
		return instance;
	}
	
	
	public String getLastShellState() {
		return sp.getString(STATE_TAG, FrameActivity.STATE_FRAG_SHELL);
	}
}
