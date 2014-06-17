/*
 * Copyright (C) 2008 Romain Guy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mouselee.bluereader.view;

import com.mouselee.bluereader.R;

import android.widget.GridView;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class ShelvesView extends GridView {
    private Bitmap mShelfBackground;
    private int mWidth;
	private int mHeight;
	private int mPaddingLeft;
	private int mPaddingTop;
	private int mPaddingRight;
	private int mPaddingBottom;
	
    private Rect backgroundDst;
    private Paint mPaint;

    public ShelvesView(Context context) {
        super(context);
    }

    public ShelvesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        load(context, attrs, 0);
    }

    public ShelvesView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        load(context, attrs, defStyle);
    }

    private void load(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ShelvesView, defStyle, 0);

        final Resources resources = getResources();
        final int background = a.getResourceId(R.styleable.ShelvesView_shelfBackground, 0);
        final Bitmap shelfBackground = BitmapFactory.decodeResource(resources, background);
        if (shelfBackground != null) {
            mShelfBackground = shelfBackground;
        }
        backgroundDst = new Rect();
   		mPaint = new Paint();
        a.recycle();
    }
    
    
    /* (non-Javadoc)
   	 * @see android.widget.AbsListView#onLayout(boolean, int, int, int, int)
   	 */
   	@Override
   	protected void onLayout(boolean changed, int l, int t, int r, int b) {
   		super.onLayout(changed, l, t, r, b);
   		mWidth = getWidth();
   		mHeight = getHeight();
   		mPaddingLeft = getPaddingLeft();
   		mPaddingTop = getPaddingTop();
   		mPaddingRight = getPaddingRight();
   		mPaddingBottom = getPaddingBottom();
   		
   	}

    @Override
    protected void dispatchDraw(Canvas canvas) {
        final int count = getChildCount();
        final int colCount = getNumColumns();
        int lineCount = (count + getNumColumns() - 1)/getNumColumns();
        int top = count > 0 ? getChildAt(0).getTop() : 0;
        for (int i = 0; i < lineCount; i++) {
        	int bottom  = count > 0 ? getChildAt(i * colCount).getBottom() : 0;
        	drawShelf(canvas, top, bottom);
        	top = bottom + 1;
        }

        super.dispatchDraw(canvas);
    }
    
    private void drawShelf(Canvas canvas, int top, int bottom) {
    	backgroundDst.left = 0;
	   	backgroundDst.top = top;
	   	backgroundDst.right = mWidth;
	   	backgroundDst.bottom = bottom;
    	canvas.drawBitmap(mShelfBackground, null, backgroundDst, mPaint);  
    }

}
