package com.robin.instastars;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;
import com.robin.adapters.ImageSlideAdapter;
import com.robin.adapters.ImageSlideAdapter.ImageDisplayListener;
import com.robin.instastars.classes.working_image;
import com.robin.instastars.fragments.ImageFragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;



public class Gallery_Images extends FragmentActivity implements working_image {

	private Fragment contentFragment;
	ImageFragment gallery_image;
	String url;
	String result;
	private ArrayList<String>images;
	@Override
	protected void onStart() {

		super.onStart();
	}
	@Override
	protected void onStop() {

		// TODO Auto-generated method stub
		super.onStop();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			images=getIntent().getExtras().getStringArrayList("images");
			Log.i("TAG", "class");
			setContentView(R.layout.gallery_activity);
			FragmentManager fragmentManager = getSupportFragmentManager();
			if (savedInstanceState != null) {
				if (fragmentManager.findFragmentByTag(ImageFragment.ARG_ITEM_ID) != null) {
					gallery_image = (ImageFragment) fragmentManager
							.findFragmentByTag(ImageFragment.ARG_ITEM_ID);
					contentFragment = gallery_image;
				}
			} else {
				gallery_image = new ImageFragment();
				switchContent(gallery_image, ImageFragment.ARG_ITEM_ID);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		ImageSlideAdapter.ImageDisplayListener.displayedImages.clear();
		ImageSlideAdapter.ImageDisplayListener.displayedImagesUrl.clear();

	}

	@Override
	public void onBackPressed() {
		FragmentManager fm = getSupportFragmentManager();
		if (fm.getBackStackEntryCount() > 0) {
			super.onBackPressed();
		} else if (contentFragment instanceof ImageFragment
				|| fm.getBackStackEntryCount() == 0) {
			finish();
		}
	}
	public void switchContent(ImageFragment fragment, String tag) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		while (fragmentManager.popBackStackImmediate())
			;
		if (fragment != null) {
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			transaction.replace(R.id.content_frame, fragment, tag);
			// Only ProductDetailFragment is added to the back stack.
			if (!(fragment instanceof ImageFragment)) {
				transaction.addToBackStack(tag);
			}
			else
			{
				fragment.images=images;
			}
			transaction.commit();
			contentFragment = fragment;
		}
	}

	@Override
	public void Share_Image() {
		try {

			if(!ImageSlideAdapter.ImageDisplayListener.displayedImages.isEmpty()&&ImageDisplayListener.displayedImages.size()>ImageFragment.current_item)
			{
				int currentpos=ImageFragment.current_item;
				String imgurl= images.get(currentpos);
				currentpos=ImageSlideAdapter.ImageDisplayListener.displayedImagesUrl.indexOf(imgurl);
				String[] temp=imgurl.split("=");
				String id = temp[temp.length-1];
				url = MediaStore.Images.Media.insertImage(getContentResolver(),ImageDisplayListener.displayedImages.get(currentpos), id,getIntent().getExtras().getString("title"));
				if(url==null)
				{


					result=Save_im_cache(ImageDisplayListener.displayedImages.get(currentpos));
					if(result==null)
					{
						Toast.makeText(Gallery_Images.this,Gallery_Images.this.getString(R.string.save_pic_error), Toast.LENGTH_SHORT).show();
					}
					else
					{
						Share(result);
					}
				}
				else
				{
					Share(url);
				}
			}
		}
		catch (Exception e) {
			Toast.makeText(Gallery_Images.this,Gallery_Images.this.getString(R.string.save_pic_error), Toast.LENGTH_SHORT).show();
		}
	}
	@Override
	public void Save_Image() {
		//		return savePhoto();
		try {
			if(!ImageSlideAdapter.ImageDisplayListener.displayedImages.isEmpty()&&ImageDisplayListener.displayedImages.size()>ImageFragment.current_item)
			{

				String imgurl= images.get(ImageFragment.current_item);
				String[] temp=imgurl.split("=");
				String id = temp[temp.length-1];
				url = MediaStore.Images.Media.insertImage(getContentResolver(),ImageDisplayListener.displayedImages.get(ImageFragment.current_item), id,getIntent().getExtras().getString("title"));
				if(url==null)
				{

					String result=Save_im_cache(ImageDisplayListener.displayedImages.get(ImageFragment.current_item));
					if(result==null)
					{
						Toast.makeText(Gallery_Images.this,Gallery_Images.this.getString(R.string.save_pic_error), Toast.LENGTH_SHORT).show();
					}
					else
					{
						Toast.makeText(this,getString(R.string.save_pic_completed).replace(getResources().getString(R.string.pic_gallery), result), Toast.LENGTH_SHORT).show();
					}
				}
				else
				{
					Toast.makeText(this,getString(R.string.save_pic_completed), Toast.LENGTH_SHORT).show();
				}

			}
		}
		catch (Exception e) {
			Log.i("T", e.getMessage());
			Toast.makeText(Gallery_Images.this,Gallery_Images.this.getString(R.string.save_pic_error), Toast.LENGTH_SHORT).show();
		}
	}
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// blutooth return
		if(arg0==1)
		{
			try{

				new Handler().postDelayed(new Runnable() {
					public void run() {
				if(url!=null)
				{
					String filename=getRealPathFromURI(Uri.parse(url));
					File f=new File(filename);
					f.delete();
				}
				else
				{
					File f=new File(result);
					f.delete();
				}
					}
				},100000);
			}
			catch(Exception e)
			{
				Log.i("LASTNEWS", e.getMessage());
			}
		}
		super.onActivityResult(arg0, arg1, arg2);
	}
	public String getRealPathFromURI(Uri contentUri) {

		// can post image
		String [] proj={MediaStore.Images.Media.DATA};
		Cursor cursor = getContentResolver().query(contentUri,
				proj, // Which columns to return
				null,       // WHERE clause; which rows to return (all rows)
				null,       // WHERE clause selection arguments (none)
				null); // Order-by clause (ascending by name)
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();

		return cursor.getString(column_index);
	}
	private void Share(String result)
	{
		Intent shareCaptionIntent = new Intent(Intent.ACTION_SEND);
		shareCaptionIntent.setType("image/png");
		shareCaptionIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.parse(result);
		shareCaptionIntent.putExtra(Intent.EXTRA_STREAM, uri);
		// set caption
		shareCaptionIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.share_caption));
		shareCaptionIntent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.share_caption));
		shareCaptionIntent.putExtra(Intent.EXTRA_TITLE,getString(R.string.share_caption));
		startActivityForResult(Intent.createChooser(shareCaptionIntent,getString(R.string.share_photo)),1);
	}
	public static String Save_im_cache(Bitmap bitmap)
	{
		try
		{
			String Dirname="SavedPic";
			String CacheDir=com.nostra13.universalimageloader.utils.StorageUtils.getIndividualCacheDirectory(AppContext.getAppContext()).getAbsolutePath();
			File f=new File(CacheDir, Dirname);
			if(!f.exists())
				f.mkdir();
			final Random r=new Random();
			String filename=CacheDir+"/"+Dirname+"/"+bitmap.hashCode()+String.valueOf(r.nextInt())+".jpg";
			FileOutputStream fos = new FileOutputStream(filename);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
			fos.flush();
			fos.close();
			return filename;
		}
		catch(Exception e)
		{
			Log.i("T", e.getMessage());
			return null;
		}
	}
}
