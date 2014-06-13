/**
 * 
 */
package com.mouselee.bluereader.shell;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.support.v4.app.Fragment;

/**
 * @author aaronli
 *
 */
@SuppressLint("NewApi")
public abstract class CursorFragment extends Fragment {

	protected Cursor valuesCursor;
	
	public void updateCursor(Cursor cursor) {
		valuesCursor = cursor;
	}
	
	public abstract void refreshDate();
	
}
