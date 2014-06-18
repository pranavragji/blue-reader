/**
 * 
 */
package com.mouselee.bluereader.shell;

import java.io.File;

import com.mouselee.bluereader.R;
import com.mouselee.bluereader.dao.BookTableConfig;
import com.mouselee.bluereader.drawable.CrossFadeDrawable;
import com.mouselee.bluereader.drawable.FastBitmapDrawable;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author aaronli
 *
 */
public class ShelfAdapter extends CursorAdapter {

	private Activity mActivity;
	private LayoutInflater inflater;
	
	private int adapterViewHeight;
	private int itemHeight;

	private Bitmap mDefaultCoverBitmap;
    private FastBitmapDrawable mDefaultCover;
    
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
		final TextView view = (TextView) inflater.inflate(R.layout.shelf_book, parent, false);

        final CrossFadeDrawable transition = new CrossFadeDrawable(mDefaultCoverBitmap, null);
        transition.setCallback(view);
        transition.setCrossFadeEnabled(true);

        return view;
	}

	/* (non-Javadoc)
	 * @see android.widget.CursorAdapter#bindView(android.view.View, android.content.Context, android.database.Cursor)
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView textView = (TextView) view;
		final long bookId = cursor.getLong(cursor.getColumnIndexOrThrow(BookTableConfig.ID));
        textView.setText(cursor.getString(cursor.getColumnIndexOrThrow(BookTableConfig.COL_BOOKNAME)));
        final String path = cursor.getString(cursor.getColumnIndexOrThrow(BookTableConfig.COL_BOOKPATH));
        view.setTag(R.id.book_id, bookId);
        view.setTag(R.id.book_path, path);
        final String coverPath = cursor.getString(cursor.getColumnIndexOrThrow(BookTableConfig.COL_IMGPATH));
        boolean showingCustomCover = false;
        if (coverPath != null && new File(coverPath).exists()) {
        	Bitmap b = BitmapFactory.decodeFile(coverPath);
        	if (b != null) {
        		textView.setBackground(new BitmapDrawable(b));
        		showingCustomCover = true;
        	}
        }
        if (!showingCustomCover) {
        	textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null,
                     mDefaultCover);
        }
	}
	
	private void init(Context context, Cursor c){
		mActivity = (Activity) context;
		inflater = LayoutInflater.from(context);
		mDefaultCoverBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.unknown_cover);
        mDefaultCover = new FastBitmapDrawable(mDefaultCoverBitmap);
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
