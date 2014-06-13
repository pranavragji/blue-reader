/**
 * Written by Aaron Lee at 9:58:08 PM Nov 5, 2013
 */
package com.mouselee.bluereader.shell;

import java.net.URI;

import com.mouselee.bluereader.R;
import com.mouselee.bluereader.dao.BookTableConfig;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Aaron Lee
 * @Date 9:58:08 PM Nov 5, 2013
 */
public class ListFrameAdapter extends CursorAdapter {

	private Activity mActivity;
	private LayoutInflater inflater;
	private boolean editMode;
	
	public ListFrameAdapter(Context context, Cursor c) {
		super(context, c, false);
		init(context, c);
	}

	public ListFrameAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		init(context, c);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public ListFrameAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		init(context, c);
	}
	
	private void init(Context context, Cursor c){
		mActivity = (Activity) context;
		inflater = LayoutInflater.from(context);
	}

	/* (non-Javadoc)
	 * @see android.widget.CursorAdapter#newView(android.content.Context, android.database.Cursor, android.view.ViewGroup)
	 */
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = inflater.inflate(R.layout.shell_list_item, null);
		ViewHolder holder = new ViewHolder();
		 holder.ivwBookIcon = (ImageView) view.findViewById(R.id.ivwBookIcon);
		 holder.tvwBookName = (TextView) view.findViewById(R.id.tvwBookName);
		 holder.tvwAuthor   = (TextView) view.findViewById(R.id.tvwAuthor  );
		 holder.ivwDelIcon  = (ImageView) view.findViewById(R.id.ivwDelIcon );
		 view.setTag(holder);
		return view;
	}

	/* (non-Javadoc)
	 * @see android.widget.CursorAdapter#bindView(android.view.View, android.content.Context, android.database.Cursor)
	 */
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();
		try {
			holder.ivwBookIcon.setImageURI(Uri.parse(cursor.getString(cursor.getColumnIndexOrThrow(BookTableConfig.COL_IMGPATH)))) ;
		} catch (Exception e) {
			holder.ivwBookIcon.setImageResource(R.drawable.fileicon_txt);
		}
		holder.tvwBookName.setText(cursor.getString(cursor.getColumnIndexOrThrow(BookTableConfig.COL_BOOKNAME)));
		holder.tvwAuthor.setText(cursor.getString(cursor.getColumnIndexOrThrow(BookTableConfig.COL_AUTHOR)));
		if (editMode) {
			holder.ivwDelIcon.setVisibility(View.VISIBLE);
		} else {
			holder.ivwDelIcon.setVisibility(View.GONE);
		}
	}
	
	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	class ViewHolder {
		ImageView ivwBookIcon;
		TextView tvwBookName;
		TextView tvwAuthor;
		ImageView ivwDelIcon;
	}

}
