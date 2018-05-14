package com.robin.instastars.fragments;


import java.util.ArrayList;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.robin.adapters.Home_Adapter;
import com.robin.instastars.Actor_Detail;
import com.robin.instastars.AppContext;
import com.robin.instastars.R;
import com.robin.instastars.classes.Actor_Class;
import com.robin.xmlparser.ActorDOMParser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainPage_Fragment extends Fragment {
	private Home_Adapter adapter;
	private ArrayList<Actor_Class>Actors_temp_search;
	public boolean search_is_active;
//	private StaggeredGridView grid_main;
	private ListView list;
	@SuppressLint("NewApi")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		search_is_active=false;
//		View rootview=inflater.inflate(R.layout.fragment_grid,container,false);
		View rootview=inflater.inflate(R.layout.main_page_list, container,false);
		if(!ImageLoader.getInstance().isInited())
			AppContext.initImageLoader();
//		grid_main=(StaggeredGridView)rootview.findViewById(R.id.grid_main);
		list=(ListView)rootview.findViewById(R.id.list_main);
		adapter=new Home_Adapter(getActivity());
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(search_is_active)
				{
					Bundle bu = new Bundle();
					bu.putString(ActorDOMParser.Bio_TAG, Actors_temp_search.get(position).getBiog());
					bu.putString(ActorDOMParser.FName_TAG, Actors_temp_search.get(position).getFName());
					bu.putString(ActorDOMParser.LName_TAG, Actors_temp_search.get(position).getLName());
					bu.putString(ActorDOMParser.UrlHot_TAG, Actors_temp_search.get(position).getImghot());
					bu.putString(ActorDOMParser.Urlmain_TAG, Actors_temp_search.get(position).getImgmain());
					bu.putString(ActorDOMParser.Prof_TAG, Actors_temp_search.get(position).getImgprofile());
					bu.putString(ActorDOMParser.Insta_TAG, Actors_temp_search.get(position).getInsta());
					Intent myIntent = new Intent(getActivity(),Actor_Detail.class);
					myIntent.putExtras(bu);
					startActivity(myIntent);
				}
				else
				{
					FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
					List_Fragment fragment = new List_Fragment();
					fragment.position=position;
					FragmentTransaction ft=fragmentManager.beginTransaction();
					ft.replace(R.id.fragment_container, fragment);
					ft.setCustomAnimations(R.anim.slide_left_to_right, R.anim.slide_right_to_left,R.anim.slide_right_to_left,R.anim.slide_left_to_right);
					ft.addToBackStack("pagetwo");
					ft.commit();
				}

			}
		});

		return rootview;
	}
	public ListView getgridview()
	{
		return list;
	}

}
