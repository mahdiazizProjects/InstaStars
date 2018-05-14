package com.robin.adapters;
import java.util.List;
import java.util.Random;

import makeramen.RoundedImageView;

import com.instastars.tables.BookmarkTbl;
import com.instastars.tables.Bookmark_click;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.robin.instastars.AppContext;
import com.robin.instastars.R;
import com.robin.instastars.classes.Actor_Class;
import com.robin.instastars.classes.TypefaceSingelton;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class Main_Adapter extends BaseAdapter {

	private List<Actor_Class>Actors;
	Context context;
	LayoutInflater mInflater;
	DisplayImageOptions options;
	ImageLoader imageloader;
	private int[]display;
	FrameLayout.LayoutParams relativeparamfornullimage;
	FrameLayout.LayoutParams relativeparamfordisplayedimage;
	RelativeLayout.LayoutParams relativeparamforprofile;
//	int[]imgkeys;
	static class HoldView
	{
		RoundedImageView image;
		RoundedImageView profile_aks;
		TextView user_name;
		ProgressBar prw;
		ImageView bookmark;
	}
	@SuppressWarnings("deprecation")
	public Main_Adapter(Activity context,List<Actor_Class>Actors)
	{
		this.context=context;
		mInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		options = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
				.cacheInMemory(true).cacheOnDisk(true).build();
		imageloader=ImageLoader.getInstance();
		this.Actors=Actors;
		display=Getmeasure(context);
		int multiplier_img_size= (int)((double)display[1]/4.0);
		relativeparamfornullimage=new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,multiplier_img_size);
		relativeparamfordisplayedimage=new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);

		int multiplier= (int)((double)display[1]/16.0);
		relativeparamforprofile=new RelativeLayout.LayoutParams(multiplier,multiplier);
//		imgkeys=new int[Actors.size()];
		relativeparamforprofile.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		relativeparamforprofile.setMargins(AppContext.getdensity(2), AppContext.getdensity(4), AppContext.getdensity(6), AppContext.getdensity(2));
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Actors.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	final Typeface Fontheader=  TypefaceSingelton.getFontNumber();
	//	@SuppressWarnings("deprecation")
	//	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HoldView item=null;
		if(convertView==null)
		{
			convertView = mInflater.inflate(R.layout.list_item, parent, false);
			item=new HoldView();
			item.image=(RoundedImageView) convertView.findViewById(R.id.image_aks);
			item.profile_aks=(RoundedImageView) convertView.findViewById(R.id.profile_aks);
			item.user_name=(TextView) convertView.findViewById(R.id.user_name);
			item.bookmark=(ImageView) convertView.findViewById(R.id.btnbookmark);
			item.user_name.setTypeface(Fontheader);
			item.prw=(ProgressBar) convertView.findViewById(R.id.pw_spinner);
			convertView.setTag(item);
		}
		else
			item=(HoldView) convertView.getTag();
		item.image.setImageDrawable(null);
		item.profile_aks.setImageDrawable(null);
		item.image.setBackgroundColor(SelectingColor());
		imageloader.displayImage(Actors.get(position).getImgmain(),item.image,options,new ImageDislistener(item.prw,position));
		imageloader.displayImage(Actors.get(position).getImgprofile(),item.profile_aks,options,new ImageDislistenerforprofile());
		item.user_name.setText(Actors.get(position).getFName()+" "+Actors.get(position).getLName());
		if(BookmarkTbl.find((Actors.get(position).getFName()+Actors.get(position).getLName()).hashCode()))
		{	item.bookmark.setColorFilter(context.getResources().getColor(R.color.red),
				android.graphics.PorterDuff.Mode.SRC_ATOP);

		}
		else
		{
			BookmarkTbl.Delete((Actors.get(position).getFName()+Actors.get(position).getLName()).hashCode());
			item.bookmark.setColorFilter(AppContext.getAppContext().getResources().getColor(R.color.pinterest_margin_gray),
					android.graphics.PorterDuff.Mode.SRC_ATOP);
		}
		item.bookmark.setOnClickListener(new Bookmark_click(Actors.get(position).getFName()+Actors.get(position).getLName(), Actors.get(position)));
		return convertView;
	}

	private class ImageDislistener implements ImageLoadingListener
	{
		final ProgressBar progress;
		private int key;
		public ImageDislistener(ProgressBar progress,int key)
		{
			this.progress=progress;
			this.key=key;
		}
		@Override
		public void onLoadingCancelled(String arg0, View arg1) {
			progress.setVisibility(View.GONE);
			arg1.setBackgroundColor(SelectingColor());
			arg1.setLayoutParams(relativeparamfornullimage);
		}

		@SuppressLint("NewApi")
		@SuppressWarnings("deprecation")
		@Override
		public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
			if(arg2!=null)
			{
				progress.setVisibility(View.INVISIBLE);
//				if(imgkeys[key]==0)
//				{
//					imgkeys[key] =arg2.getHeight();
//					relativeparamfordisplayedimage.height=LayoutParams.WRAP_CONTENT;
//				}
//				else
//					relativeparamfordisplayedimage.height=imgkeys[key];

				arg1.setLayoutParams(relativeparamfordisplayedimage);
				if(Build.VERSION.SDK_INT > 16) {
					arg1.setBackground(null);
				} else {
					arg1.setBackgroundDrawable(null);
				}
			}
		}

		@Override
		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {

			progress.setVisibility(View.GONE);
			arg1.setBackgroundColor(SelectingColor());
			arg1.setLayoutParams(relativeparamfornullimage);

		}

		@Override
		public void onLoadingStarted(String arg0, View arg1) {
			progress.setVisibility(View.VISIBLE);
			arg1.setBackgroundColor(SelectingColor());
			arg1.setLayoutParams(relativeparamfornullimage);
		}



	}
	private class ImageDislistenerforprofile implements ImageLoadingListener
	{
		@Override
		public void onLoadingCancelled(String arg0, View arg1) {
			arg1.setBackgroundColor(SelectingColor());
			arg1.setLayoutParams(relativeparamforprofile);
		}

		@SuppressLint("NewApi")
		@SuppressWarnings("deprecation")
		@Override
		public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
			if(arg2!=null)
			{
				arg1.setLayoutParams(relativeparamforprofile);
				if(Build.VERSION.SDK_INT > 16) {
					arg1.setBackground(null);
				} else {
					arg1.setBackgroundDrawable(null);
				}
			}
		}

		@Override
		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {

			arg1.setBackgroundColor(SelectingColor());
			arg1.setLayoutParams(relativeparamforprofile);

		}

		@Override
		public void onLoadingStarted(String arg0, View arg1) {
			arg1.setBackgroundColor(SelectingColor());
			arg1.setLayoutParams(relativeparamforprofile);
		}



	}
	public static int SelectingColor()
	{
		Random r=new Random();
		int randnumber=r.nextInt(8);
		return getcolor(randnumber,AppContext.getAppContext());
	}
	public static int getcolor(int id,Context context)
	{
		int result=0;
		if(id==0)
		{
			result=context.getResources().getColor(R.color.lightblue);
		}
		else if(id==1)
		{
			result=context.getResources().getColor(R.color.lightgreen);
		}
		else if(id==2)
		{
			result=context.getResources().getColor(R.color.lightorange);
		}
		else if(id==3)
		{
			result=context.getResources().getColor(R.color.lightpink);
		}
		else if(id==4)
		{
			result=context.getResources().getColor(R.color.lightred);
		}
		else if(id==5)
		{
			result=context.getResources().getColor(R.color.lightyellow);
		}
		else if(id==6)
		{
			result=context.getResources().getColor(R.color.lightgreen2);
		}
		else
		{
			result=context.getResources().getColor(R.color.lightwhite);
		}
		return result;
	}
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private int[]Getmeasure(Activity activity)
	{
		int measuredWidth = 0;
		int measuredHeight = 0;
		int[]display=new int[2];
		Point size = new Point();
		WindowManager w = activity.getWindowManager();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
		{
			w.getDefaultDisplay().getSize(size);

			measuredWidth = size.x;
			measuredHeight = size.y;
		}
		else
		{
			Display d = w.getDefaultDisplay();
			measuredWidth = d.getWidth();
			measuredHeight = d.getHeight();
		}
		display[0]=measuredWidth;
		display[1]=measuredHeight;
		return display;
	}
}