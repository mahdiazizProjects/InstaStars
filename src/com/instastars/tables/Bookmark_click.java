package com.instastars.tables;

import com.robin.instastars.AppContext;
import com.robin.instastars.R;
import com.robin.instastars.classes.Actor_Class;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Bookmark_click implements View.OnClickListener{
	private String key;
	private Actor_Class actor;
	public Bookmark_click(String key,Actor_Class actor)
	{
		this.key=key;
		this.actor=actor;
	}
	@Override
	public void onClick(View v) {


		ImageView im=(ImageView) v;
		if(!BookmarkTbl.find(key.hashCode()))
		{
			BookmarkTbl tbl=new BookmarkTbl();
			tbl.id=key.hashCode();
			tbl.Bio_TAG=actor.getBiog();
			tbl.BPlace_TAG=actor.getBPlace();
			tbl.BTime_TAG=actor.getBTime();
			tbl.FName_TAG=actor.getFName();
			tbl.Insta_TAG=actor.getInsta();
			tbl.LName_TAG=actor.getLName();
			tbl.Prof_TAG=actor.getImgprofile();
			tbl.UrlHot_TAG=actor.getImghot();
			tbl.Urlmain_TAG=actor.getImgmain();
			tbl.Insert();
			im.setColorFilter(AppContext.getAppContext().getResources().getColor(R.color.red),
					android.graphics.PorterDuff.Mode.SRC_ATOP);

		}
		else
		{
			BookmarkTbl.Delete(key.hashCode());
			im.setColorFilter(AppContext.getAppContext().getResources().getColor(R.color.pinterest_margin_gray),
					android.graphics.PorterDuff.Mode.SRC_ATOP);
		}
	}
}
