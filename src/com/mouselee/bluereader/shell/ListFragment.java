/**
 * 
 */
package com.mouselee.bluereader.shell;

import com.mouselee.bluereader.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * @author kang
 *
 */
public class ListFragment extends Fragment {
	
	private Activity mActivity;
	private LayoutInflater inflater;
	private ListView lvwMain;
	
	public static ListFragment newInstance(String tag) {
		ListFragment f = new ListFragment();
		Bundle args = new Bundle();
		args.putString("tag", tag);
		f.setArguments(args);
		return f;
	}

	public ListFragment() {
		super();
		mActivity  = getActivity();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		inflater = LayoutInflater.from(mActivity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.shell_list_content, null);
		lvwMain = (ListView) contentView;
		return contentView;
	}
	
}
