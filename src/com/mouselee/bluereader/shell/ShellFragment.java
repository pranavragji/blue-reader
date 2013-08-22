/**
 * 
 */
package com.mouselee.bluereader.shell;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * @author kang
 *
 */
public class ShellFragment extends Fragment {
	
	public static ShellFragment newInstance(String tag) {
		ShellFragment f = new ShellFragment();
		Bundle args = new Bundle();
		args.putString("tag", tag);
		f.setArguments(args);
		return f;
	}
}
