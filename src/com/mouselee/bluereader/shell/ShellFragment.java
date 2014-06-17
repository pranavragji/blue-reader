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



	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}



	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onDestroyView()
	 */
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}



	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onStart()
	 */
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	

}
