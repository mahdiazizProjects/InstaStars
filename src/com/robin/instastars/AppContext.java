package com.robin.instastars;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Application;
import android.content.Context;

public class AppContext extends Application {
	
	 public static Context context;

	 @Override   
	 public void onCreate(){
		 
	     super.onCreate();
	     AppContext.context = getApplicationContext();
	 }

	    
	 public static Context getAppContext() {
	     return AppContext.context;
	    }


	public static void initImageLoader() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				AppContext.context).threadPriority(Thread.NORM_PRIORITY - 2).
				memoryCache(new LRULimitedMemoryCache(2*1024*1024)).denyCacheImageMultipleSizesInMemory()
				.tasksProcessingOrder(QueueProcessingType.FIFO).build();

		com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);
	}
	public static int getdensity(int dpinput)
	{
		return dpinput*(int)context.getResources().getDisplayMetrics().density;
	}
}
