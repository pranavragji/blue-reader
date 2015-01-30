/**
 * 
 */
package com.mouselee.bluereader.shell;

import com.mouselee.bluereader.R;
import com.mouselee.bluereader.act.ReaderActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * @author kang
 *
 */
@SuppressLint("NewApi")
public class ListFragment extends CursorFragment implements OnItemClickListener {
	
	private Activity mActivity;
	private LayoutInflater inflater;
	private ListView lvwMain;
	private ListFrameAdapter adapter;
	
	public static ListFragment newInstance(String tag) {
		ListFragment f = new ListFragment();
		Bundle args = new Bundle();
		args.putString("tag", tag);
		f.setArguments(args);
		return f;
	}


	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity  = getActivity();
		inflater = LayoutInflater.from(mActivity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.shell_list_content, null);
		lvwMain = (ListView) contentView;
		adapter = new ListFrameAdapter(mActivity, valuesCursor);
		lvwMain.setAdapter(adapter);
		lvwMain.setOnItemClickListener(this);
		return contentView;
	}

	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onStart()
	 */
	@Override
	public void onStart() {
		refreshDate();
		super.onStart();
	}

	@Override
	public void refreshDate() {
		adapter.changeCursor(valuesCursor);
		adapter.notifyDataSetChanged();
		
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(mActivity, ReaderActivity.class);
		startActivity(intent);
		
	}

	
}
