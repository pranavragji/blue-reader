/**
 * 
 */
package com.mouselee.bluereader.shell;

import com.mouselee.bluereader.R;
import com.mouselee.bluereader.act.ReaderActivity;
import com.mouselee.bluereader.view.ShelvesView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author kang
 *
 */
public class ShellFragment extends CursorFragment implements OnItemClickListener {
	
	private ShelvesView shelvesView;
	private ShelfAdapter shelfAdapter;
	
	public static ShellFragment newInstance(String tag) {
		ShellFragment f = new ShellFragment();
		Bundle args = new Bundle();
		args.putString("tag", tag);
		f.setArguments(args);
		return f;
	}
	
	

	@Override
	public void refreshDate() {
		shelfAdapter.changeCursor(valuesCursor);
		shelfAdapter.notifyDataSetChanged();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		shelvesView = (ShelvesView) inflater.inflate(R.layout.shelf_shelf_content, container, false);
		shelfAdapter = new ShelfAdapter(getActivity(), valuesCursor); 
		shelvesView.setAdapter(shelfAdapter);
		shelvesView.setOnItemClickListener(this);
		return shelvesView;
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getActivity(), ReaderActivity.class);
		String path = (String) view.getTag(R.id.book_path);
		intent.putExtra(ReaderActivity.KEY_BOOKPATH, path);
		startActivity(intent);
		
	}
	

}
