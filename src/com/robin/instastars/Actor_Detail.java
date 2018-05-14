package com.robin.instastars;


import java.util.ArrayList;

import com.instastars.widgets.CustomScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.robin.instastars.classes.TypefaceSingelton;
import com.robin.xmlparser.ActorDOMParser;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class Actor_Detail extends ActionBarActivity {
	private ImageView imageprofile;
	private ImageView img_top;
	private TextView textbio;
	private TextView textlink;
	private TextView fnamelname;
	private ImageLoader imageloader;
	private DisplayImageOptions options;
	private DisplayImageOptions options2;
	private TextView link_x;
	private ImageView action_bar;
	private CustomScrollView scroll_actor_detail;
	final int sdk = android.os.Build.VERSION.SDK_INT;
	private TextView title;
	private TextView titleactionbar;
	ArrayList<String>images;
	FragmentManager fm;
	@Override
	protected void onStart() {
		super.onStart();
	}
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppContext.context=this;
		if(!ImageLoader.getInstance().isInited())
			AppContext.initImageLoader();
		imageloader=ImageLoader.getInstance();
		setContentView(R.layout.actor_detail);
		images=new ArrayList<>();
		images.add(getIntent().getExtras().getString(ActorDOMParser.Urlmain_TAG));
		images.add(getIntent().getExtras().getString(ActorDOMParser.Prof_TAG));
		images.add(getIntent().getExtras().getString(ActorDOMParser.UrlHot_TAG));
		options = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
				.cacheInMemory(true).cacheOnDisk(true).build();
		options2 = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).showImageOnLoading(R.drawable.logo_ourcompany)
				.cacheInMemory(true).cacheOnDisk(true).build();

		FindViewById();
		imageloader.displayImage(images.get(2),img_top,options2);
		img_top.setOnClickListener(new ViewGalleryClick());
		final String imageurlprofile=getIntent().getExtras().getString(ActorDOMParser.Prof_TAG);
		imageloader.displayImage(imageurlprofile, imageprofile,options);
		textbio.setText("بیوگرافی: "+getIntent().getExtras().getString(ActorDOMParser.Bio_TAG));
		textbio.setTypeface(TypefaceSingelton.getFontAdobArabic());
		textbio.setLineSpacing(2.5f, 1.2f);
		title.setTypeface(TypefaceSingelton.getFontNumber());
		fnamelname.setTypeface(TypefaceSingelton.getFontNumber(),Typeface.BOLD);
		textlink.setText(getIntent().getExtras().getString(ActorDOMParser.FName_TAG)+" "+getIntent().getExtras().getString(ActorDOMParser.LName_TAG));
		textlink.setTypeface(TypefaceSingelton.getFontNumber());
		textlink.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				open_instagram(getApplicationContext(), getIntent().getExtras().getString(ActorDOMParser.Insta_TAG).trim());
			}
		});
		fnamelname.setText(getIntent().getExtras().getString(ActorDOMParser.FName_TAG)+" "+getIntent().getExtras().getString(ActorDOMParser.LName_TAG));
		link_x.setTypeface(TypefaceSingelton.getFontNumber(),Typeface.BOLD);
		link_x.setText(getResources().getString(R.string.ins_add));
		if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			AlphaAnimation alpha = new AlphaAnimation(1f, 0.0f);
			alpha.setDuration(0);
			alpha.setFillAfter(true);
			action_bar.startAnimation(alpha);
		}
		else
			action_bar.setAlpha(0.0f);
		scroll_actor_detail.setOnScrollChangedListener(new CustomScrollView.OnScrollChangedListener() {

			@Override
			public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
				final int headerHeight = getdensity(320) - action_bar.getHeight();
				final float ratio = (float)Math.min(Math.max(t, 0), headerHeight) / headerHeight;
				//final int newAlpha = (int) (ratio * 255);
				if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
					AlphaAnimation alpha = new AlphaAnimation(1f, ratio);
					alpha.setDuration(0);
					alpha.setFillAfter(true);
					action_bar.startAnimation(alpha);
				}
				else
				action_bar.setAlpha(ratio);


			}
		});
		ImageButton backbtn=(ImageButton) findViewById(R.id.ac_back);
		backbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
				
			}
		});
	}
	private void FindViewById()
	{
		imageprofile=(ImageView) findViewById(R.id.profile_aks);
		textbio=(TextView) findViewById(R.id.biog);
		textlink=(TextView) findViewById(R.id.link);
		fnamelname=(TextView) findViewById(R.id.user_name);
		link_x=(TextView) findViewById(R.id.link_x);
		action_bar=(ImageView) findViewById(R.id.ac_background);
		scroll_actor_detail=(CustomScrollView) findViewById(R.id.scroll_actor_detail);
		img_top=(ImageView) findViewById(R.id.image_top);
		title=(TextView) findViewById(R.id.title);
		titleactionbar=(TextView) findViewById(R.id.titleac);
		titleactionbar.setTypeface(TypefaceSingelton.getFontNumber());
	}
	private void open_instagram(Context context,String Insta_Adress)
	{

		if (Insta_Adress.endsWith("/")) {
			Insta_Adress = Insta_Adress.substring(0, Insta_Adress.length() - 1);
		}
		String username = Insta_Adress.substring(Insta_Adress.lastIndexOf("/") + 1);
		Uri uri = Uri.parse("http://instagram.com/_u/" + username);
		Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
		likeIng.setPackage("com.instagram.android");

		try {
			startActivity(likeIng);
		} catch (ActivityNotFoundException e) {
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse(Insta_Adress)));
		}

	}

	private int getdensity(int dpinput)
	{
		return dpinput*(int)getResources().getDisplayMetrics().density;
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
	private class ViewGalleryClick implements View.OnClickListener
	{

		@Override
		public void onClick(View v) {

			Intent in=new Intent(Actor_Detail.this,Gallery_Images.class);
			Bundle b=new Bundle();
			b.putStringArrayList("images", images);
			in.putExtras(b);
			startActivity(in);
			
		}
		
	}
}