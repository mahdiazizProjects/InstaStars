package com.robin.instastars.fragments;


import java.util.ArrayList;

import com.etsy.android.grid.StaggeredGridView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.robin.adapters.Main_Adapter;
import com.robin.instastars.Actor_Detail;
import com.robin.instastars.AppContext;
import com.robin.instastars.R;
import com.robin.instastars.classes.Actor_Class;
import com.robin.instastars.classes.Search;
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

@SuppressLint("NewApi")
public class Search_Fragment extends Fragment {

	public ArrayList<Actor_Class>Actors;
	private ArrayList<Actor_Class>Actors_temp_search;
	public boolean search_is_active;
	private Main_Adapter adapter;
	private StaggeredGridView grid_main;
	public int position;
	public String entrance;
	public String keyword;
	@SuppressLint("NewApi")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		search_is_active=false;
		View rootview=inflater.inflate(R.layout.fragment_grid,container,false);
		if(!ImageLoader.getInstance().isInited())
			AppContext.initImageLoader();
		fill(keyword);
		grid_main=(StaggeredGridView)rootview.findViewById(R.id.grid_main);
		adapter=new Main_Adapter(getActivity(), Actors);
		grid_main.setAdapter(adapter);
		grid_main.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Bundle bu = new Bundle();

				if(search_is_active)
				{
					bu.putString(ActorDOMParser.Bio_TAG, Actors_temp_search.get(position).getBiog());
					bu.putString(ActorDOMParser.FName_TAG, Actors_temp_search.get(position).getFName());
					bu.putString(ActorDOMParser.LName_TAG, Actors_temp_search.get(position).getLName());
					bu.putString(ActorDOMParser.UrlHot_TAG, Actors_temp_search.get(position).getImghot());
					bu.putString(ActorDOMParser.Urlmain_TAG, Actors_temp_search.get(position).getImgmain());
					bu.putString(ActorDOMParser.Prof_TAG, Actors_temp_search.get(position).getImgprofile());
					bu.putString(ActorDOMParser.Insta_TAG, Actors_temp_search.get(position).getInsta());
				}
				else
				{
					bu.putString(ActorDOMParser.Bio_TAG, Actors.get(position).getBiog());
					bu.putString(ActorDOMParser.FName_TAG, Actors.get(position).getFName());
					bu.putString(ActorDOMParser.LName_TAG, Actors.get(position).getLName());
					bu.putString(ActorDOMParser.UrlHot_TAG, Actors.get(position).getImghot());
					bu.putString(ActorDOMParser.Urlmain_TAG, Actors.get(position).getImgmain());
					bu.putString(ActorDOMParser.Prof_TAG, Actors.get(position).getImgprofile());
					bu.putString(ActorDOMParser.Insta_TAG, Actors.get(position).getInsta());
				}
				Intent myIntent = new Intent(getActivity(),Actor_Detail.class);
				myIntent.putExtras(bu);
				startActivity(myIntent);
			}
		});

		return rootview;
	}
	private void fill(String keyword)
	{
		Search search=new Search();
		search.setKeyword(keyword);
		search.setActors(ActorDOMParser.parseXML(getActivity(), 0));
		Actors=new ArrayList<Actor_Class>();
		Actors=search.getindexofsearchall(Actors);
		search.setActors(ActorDOMParser.parseXML(getActivity(), 1));
		Actors=search.getindexofsearchall(Actors);
		search.setActors(ActorDOMParser.parseXML(getActivity(), 2));
		Actors=search.getindexofsearchall(Actors);
	}
	public StaggeredGridView getgridview()
	{
		return grid_main;
	}
	public void SetgridView(ArrayList<Actor_Class>actors)
	{
		Actors_temp_search=new ArrayList<Actor_Class>(actors);
		adapter=new Main_Adapter(getActivity(), actors);
		grid_main.setAdapter(adapter);
		search_is_active=true;
	}
}
