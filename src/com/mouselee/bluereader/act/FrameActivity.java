/**
 * 
 */
package com.mouselee.bluereader.act;

import com.mouselee.bluereader.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author kang
 *
 */
public class FrameActivity extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shell_main);
		
	}
	
	private void setShowFragment(String tag) {
		
	}
}
