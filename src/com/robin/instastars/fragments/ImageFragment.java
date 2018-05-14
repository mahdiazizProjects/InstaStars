package com.robin.instastars.fragments;

import java.util.ArrayList;
import com.instastars.widgets.CirclePageIndicator;
import com.instastars.widgets.PageIndicator;
import com.robin.adapters.ImageSlideAdapter;
import com.robin.instastars.R;
import com.robin.instastars.classes.TypefaceSingelton;
import com.robin.instastars.classes.working_image;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ImageFragment extends Fragment {

	public static final String ARG_ITEM_ID = "home_fragment";

//	private static final long ANIM_VIEWPAGER_DELAY = 50000;
//	private static final long ANIM_VIEWPAGER_DELAY_USER_VIEW = 100000;
	public ArrayList<String>images;
	// UI References
	private ViewPager mViewPager;
	TextView imgNameTxt;
	PageIndicator mIndicator;
	private TextView Img_no;
	private ImageView close_ico;
	private RelativeLayout layout_linear_aks;
	private RelativeLayout gal_bottom_layout;
	private ImageButton save_button;
	private ImageButton share_button;
	AlertDialog alertDialog;

	boolean stopSliding = false;
	String message;
	private working_image save_share=null;
	private Runnable animateViewPager;
	private Handler handler;
	public static int current_item;
	FragmentActivity activity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		
	}
	public void onAttach(Activity activity) {
		save_share=(working_image)activity;
		this.activity=(FragmentActivity) activity;
		super.onAttach(activity);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_image_slideshow, container, false);
		findViewById(view);
		making_the_background_transparent();
		current_item=0;
		mViewPager.setAdapter(new ImageSlideAdapter(activity, images,
				ImageFragment.this));

		mIndicator.setViewPager(mViewPager,0);
		mViewPager.setOnPageChangeListener(new PageChangeListener());
//		mViewPager.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				v.getParent().requestDisallowInterceptTouchEvent(true);
//				switch (event.getAction()) {
//
//				case MotionEvent.ACTION_CANCEL:
//					break;
//
//				case MotionEvent.ACTION_UP:
//					// calls when touch release on ViewPager
//					if (images != null && images.size() != 0) {
//						stopSliding = false;
//						runnable(images.size());
//						handler.postDelayed(animateViewPager,
//								ANIM_VIEWPAGER_DELAY_USER_VIEW);
//					}
//					break;
//
//				case MotionEvent.ACTION_MOVE:
//					// calls when ViewPager touch
//					if (handler != null && stopSliding == false) {
//						stopSliding = true;
//						handler.removeCallbacks(animateViewPager);
//					}
//					break;
//				}
//				return false;
//			}
//		});
		save_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				save_share.Save_Image();

			}
		});
		share_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				save_share.Share_Image();

			}
		});
		close_ico.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				activity.finish();

			}
		});
		return view;
	}

	private void findViewById(View view) {
		mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
		mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
		save_button=(ImageButton) view.findViewById(R.id.img_save);
		share_button=(ImageButton) view.findViewById(R.id.img_share);
		Img_no=(TextView) view.findViewById(R.id.img_no);
		Img_no.setTypeface(TypefaceSingelton.getFontNumber());
		close_ico=(ImageView)view.findViewById(R.id.close_icon);
		layout_linear_aks=(RelativeLayout) view.findViewById(R.id.layout_linear_aks);
		gal_bottom_layout=(RelativeLayout) view.findViewById(R.id.gal_bottom_layout);
		Img_no.setText(getString(R.string.gallery_image)+ String.valueOf(1)+"/"+String.valueOf(images.size()));

	}

//	public void runnable(final int size) {
//		handler = new Handler();
//		animateViewPager = new Runnable() {
//			public void run() {
//				if (!stopSliding) {
//					if (mViewPager.getCurrentItem() == size - 1) {
//						mViewPager.setCurrentItem(0);
//					} else {
//						mViewPager.setCurrentItem(
//								mViewPager.getCurrentItem() + 1, true);
//					}
//					handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
//				}
//			}
//		};
//	}


//	@Override
//	public void onResume() {
//		mViewPager.setAdapter(new ImageSlideAdapter(activity, images,
//				ImageFragment.this));
//
//		mIndicator.setViewPager(mViewPager);
////		runnable(images.size());
//		//Re-run callback
////		handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
//		super.onResume();
//	}


	@Override
	public void onPause() {
		if (handler != null) {
			//Remove callback
			handler.removeCallbacks(animateViewPager);
		}
		super.onPause();
	}


	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	private class PageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int state) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			current_item=arg0;
			mIndicator.setCurrentItem(arg0);
			Img_no.setText(getString(R.string.gallery_image)+ String.valueOf(current_item+1)+"/"+String.valueOf(images.size()));
		}
	}

	@SuppressLint("NewApi")
	private void making_the_background_transparent()
	{
		if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			AlphaAnimation alpha = new AlphaAnimation(1f, 0.5f);
			alpha.setDuration(0);
			alpha.setFillAfter(true);
			layout_linear_aks.startAnimation(alpha);
			gal_bottom_layout.startAnimation(alpha);
		}
		else
		{
			layout_linear_aks.setAlpha(0.5f);
			gal_bottom_layout.setAlpha(0.5f);
		}
	}


}
