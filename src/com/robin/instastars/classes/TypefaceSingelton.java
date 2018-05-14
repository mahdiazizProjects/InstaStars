package com.robin.instastars.classes;

import com.robin.instastars.AppContext;

import android.graphics.Typeface;
import android.os.Build;

public class TypefaceSingelton {

	private static TypefaceSingelton instance = new TypefaceSingelton();

	private TypefaceSingelton() {}

	public static TypefaceSingelton getInstance() {
		return instance;
	}


	public static  Typeface getFontNumber() {
		return Typeface.createFromAsset(AppContext.getAppContext().getResources().getAssets(), "fonts/BYekan.ttf");
	}
	public static  Typeface getFontAdobArabic() {
		if(Build.VERSION.SDK_INT<20)
			return Typeface.createFromAsset(AppContext.getAppContext().getResources().getAssets(), "fonts/arabic.ttf");
		else
			return Typeface.createFromAsset(AppContext.getAppContext().getResources().getAssets(), "fonts/nassim.ttf");
	}
}