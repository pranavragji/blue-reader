/**
 * 
 */
package com.mouselee.bluereader.shell;

import android.os.Bundle;

/**
 * @author kang
 *
 */
public class ShellFragment extends CursorFragment {
	
	public static ShellFragment newInstance(String tag) {
		ShellFragment f = new ShellFragment();
		Bundle args = new Bundle();
		args.putString("tag", tag);
		f.setArguments(args);
		return f;
	}

	@Override
	public void refreshDate() {
		// TODO Auto-generated method stub
		
	}
	

}
