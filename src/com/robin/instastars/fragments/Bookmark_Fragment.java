package com.robin.instastars.fragments;


import java.util.ArrayList;

import com.etsy.android.grid.StaggeredGridView;
import com.instastars.tables.BookmarkTbl;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.robin.adapters.Main_Adapter;
import com.robin.instastars.Actor_Detail;
import com.robin.instastars.AppContext;
import com.robin.instastars.R;
import com.robin.instastars.classes.Actor_Class;
import com.robin.xmlparser.ActorDOMParser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Bookmark_Fragment extends Fragment {

	public ArrayList<Actor_Class>Actors;
	private Main_Adapter adapter;
	private StaggeredGridView grid_main;
	public int position;
	public String keyword;
	@SuppressLint("NewApi")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootview=inflater.inflate(R.layout.fragment_grid,container,false);
		if(!ImageLoader.getInstance().isInited())
			AppContext.initImageLoader();
		fill();
		if(Actors.size()<1)
		{
			Toast.makeText(getActivity(), "شما در حال حاضر بوک مارکی ندارید.", Toast.LENGTH_SHORT).show();
		}
		else
		{
			grid_main=(StaggeredGridView)rootview.findViewById(R.id.grid_main);
			adapter=new Main_Adapter(getActivity(), Actors);
			grid_main.setAdapter(adapter);
			grid_main.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position,
						long arg3) {
					Bundle bu = new Bundle();
					bu.putString(ActorDOMParser.Bio_TAG, Actors.get(position).getBiog());
					bu.putString(ActorDOMParser.FName_TAG, Actors.get(position).getFName());
					bu.putString(ActorDOMParser.LName_TAG, Actors.get(position).getLName());
					bu.putString(ActorDOMParser.UrlHot_TAG, Actors.get(position).getImghot());
					bu.putString(ActorDOMParser.Urlmain_TAG, Actors.get(position).getImgmain());
					bu.putString(ActorDOMParser.Prof_TAG, Actors.get(position).getImgprofile());
					bu.putString(ActorDOMParser.Insta_TAG, Actors.get(position).getInsta());

					Intent myIntent = new Intent(getActivity(),Actor_Detail.class);
					myIntent.putExtras(bu);
					startActivity(myIntent);
				}
			});
		}
		return rootview;
	}
	private void fill()
	{
		BookmarkTbl tbl=new BookmarkTbl();
		Actors=tbl.GetBookmark();

	}
}
