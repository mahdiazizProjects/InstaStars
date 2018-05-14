package com.robin.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;

import com.afkhabar.afnewsbeta.zoom.PhotoView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.robin.instastars.R;
import com.robin.instastars.fragments.ImageFragment;

public class ImageSlideAdapter extends PagerAdapter {
	ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	FragmentActivity activity;
	ArrayList<String> images;
	ImageFragment homeFragment;
	ViewPager.LayoutParams relativeparamfornullimage;
	ViewPager.LayoutParams relativeparamfordisplayedimage;
	ImageDisplayListener displayer;
	public ImageSlideAdapter(FragmentActivity activity, ArrayList<String>images,
			ImageFragment homeFragment) {
		this.activity = activity;
		this.homeFragment = homeFragment;
		this.images = images;
		options = new DisplayImageOptions.Builder()
				.showImageOnFail(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.logo_ourcompany).showImageOnLoading(R.drawable.logo_ourcompany).showImageOnFail(R.drawable.logo_ourcompany).cacheInMemory(true)
				.cacheOnDisk(true).build();
		displayer=new ImageDisplayListener();
	}

	@Override
	public int getCount() {
		return images.size();
	}

	@Override
	public View instantiateItem(ViewGroup container, final int position) {
		final PhotoView phView = new PhotoView(activity);
		// Attach a PhotoViewAttacher, which takes care of all of the zooming functionality.
		try{
			String imgurl=images.get(position);
			imageLoader.displayImage(imgurl
					, phView,options,displayer);
			container.addView(phView, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			return phView;
		}
		catch(Exception e)
		{
			return phView;
		}
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
	public static class ImageDisplayListener extends
	SimpleImageLoadingListener {

		public static final List<Bitmap> displayedImages = Collections
				.synchronizedList(new LinkedList<Bitmap>());
		public static final List<String> displayedImagesUrl = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				boolean firstDisplay = !displayedImagesUrl.contains(imageUri);
				if (firstDisplay) {
					displayedImages.add(loadedImage);
					displayedImagesUrl.add(imageUri);
				}
			}
		}
		@Override
		public void onLoadingStarted(String imageUri, View view) {
			super.onLoadingStarted(imageUri, view);
		}
		
	}

}