package com.robin.instastars.classes;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

@SuppressLint("NewApi")
public class Animation {
	private final int ANIMATION_DURATION=500;
	public void fadeOutToTop(View v, boolean animated,int height) {
		v.animate().
		translationYBy(-height).
		alpha(0).setDuration(animated ? ANIMATION_DURATION : 0).
		setInterpolator(new DecelerateInterpolator()).
		start();
	}
	public void TranslateToTop(View v, boolean animated,int height) {
		v.animate().
		translationYBy(-height).
		setDuration(animated ? ANIMATION_DURATION : 0).
		setInterpolator(new DecelerateInterpolator()).
		start();
	}
	public void fadeinTobottom(View v, boolean animated,int height) {
		if(android.os.Build.VERSION.SDK_INT>12)
		{
			v.animate().
			translationYBy(height).
			alpha(100).setDuration(animated ? ANIMATION_DURATION : 0).
			setInterpolator(new DecelerateInterpolator()).
			start();
		}

	}
}
