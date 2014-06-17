/**
 * 
 */
package com.mouselee.bluereader.shell;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * @author aaronli
 *
 */
public class ShelfAdapter extends CursorAdapter {

	private Activity mActivity;
	private LayoutInflater inflater;
	
	private int adapterViewHeight;
	private int itemHeight;

	/**
	 * @param context
	 * @param c
	 */
	public ShelfAdapter(Context context, Cursor c) {
		super(context, c, FLAG_REGISTER_CONTENT_OBSERVER);
		init(context, c);
	}

	/**
	 * @param context
	 * @param c
	 * @param autoRequery
	 */
	public ShelfAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		init(context, c);
	}

	/**
	 * @param context
	 * @param c
	 * @param flags
	 */
	public ShelfAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		init(context, c);
	}
	
	

	/* (non-Javadoc)
	 * @see android.widget.CursorAdapter#newView(android.content.Context, android.database.Cursor, android.view.ViewGroup)
	 */
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see android.widget.CursorAdapter#bindView(android.view.View, android.content.Context, android.database.Cursor)
	 */
	@Override
	public void bindView(View view, Context context, Cursor cursor) {

	}
	
	private void init(Context context, Cursor c){
		mActivity = (Activity) context;
		inflater = LayoutInflater.from(context);
	}

	/* (non-Javadoc)
	 * @see android.widget.CursorAdapter#getCount()
	 */
	@Override
	public int getCount() {
		int superCount = super.getCount();
		int minRowcount = computeMinRowcount();
		if (superCount < minRowcount) {
			return minRowcount;
		}
		return superCount;
	}

	/**
	 * @return the adapterViewHeight
	 */
	public int getAdapterViewHeight() {
		return adapterViewHeight;
	}

	/**
	 * @param adapterViewHeight the adapterViewHeight to set
	 */
	public void setAdapterViewHeight(int adapterViewHeight) {
		this.adapterViewHeight = adapterViewHeight;
	}

	/**
	 * @return the itemHeight
	 */
	public int getItemHeight() {
		return itemHeight;
	}

	/**
	 * @param itemHeight the itemHeight to set
	 */
	public void setItemHeight(int itemHeight) {
		this.itemHeight = itemHeight;
	}

	private int computeMinRowcount() {
		return (adapterViewHeight + itemHeight - 1) / itemHeight;
	}

}
