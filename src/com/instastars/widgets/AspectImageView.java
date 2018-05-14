package com.instastars.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author Sherif elKhatib
 *
 * ImageView Class that maintains the width of the view and changes height to keep the aspect ratio.
 */
public class AspectImageView extends ImageView {
	public AspectImageView(Context context) {
		super(context);

	}

	public AspectImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AspectImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if(getBackground() == null || getBackground().getIntrinsicHeight()==0 || getBackground().getIntrinsicWidth()==0) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			return;
		}
//		final TypedValue outValue = new TypedValue();
//		getResources().getValue(R.dimen.scale_image_news, outValue, true);
//		value = outValue.getFloat();
		//        Log.i(VIEW_LOG_TAG,String.valueOf(value));
		final int width = MeasureSpec.getSize(widthMeasureSpec);
		final int height = width * getBackground().getIntrinsicHeight() / getBackground().getIntrinsicWidth();
//		width=(int) (width/value);
//		height=(int) (height/value);
		setMeasuredDimension(width, height);
	}


}