package com.robin.instastars;


import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
@SuppressLint("NewApi")
public class SplashActivity extends ActionBarActivity{
	@SuppressLint("InlinedApi")
	protected void onResume()
	{
	    super.onResume();

	    if (Build.VERSION.SDK_INT < 16)
	    {
	        // Hide the status bar
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        // Hide the action bar
	    }
	    else
	    {
	        // Hide the status bar
	    	
	        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
	        // Hide the action bar

	    }
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_layout);
//		TextView tv1=(TextView) findViewById(R.id.slogan);
		AppContext.context=this;
//		tv1.setTypeface(TypefaceSingelton.getFontNumber(), Typeface.BOLD);
//		TextView tv2=(TextView) findViewById(R.id.compname);
//		tv2.setTypeface(TypefaceSingelton.getFontNumber(), Typeface.BOLD);

		/****** Create Thread that will sleep for 2 seconds *************/        
		Thread background = new Thread() {
			public void run() {

				try {
					// Thread will sleep for 1 seconds
					sleep(3000);

					Intent i=new Intent(SplashActivity.this,MainActivity.class);
					startActivity(i);
					finish();

				} catch (Exception e) {

				}
			}
		};

		// start thread
		background.start();
	}
	public void startActivity(Intent intent)
	{
		super.startActivity(intent);
		overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_open_exit);
	}
	public void finish()
	{
		super.finish();
		overridePendingTransition(R.anim.activity_close_enter, R.anim.activity_close_exit);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();

	}
}