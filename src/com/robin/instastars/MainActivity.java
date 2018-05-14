package com.robin.instastars;


import java.util.ArrayList;

import com.robin.adapters.NavDrawerItem;
import com.robin.adapters.NavDrawerListAdapter;
import com.robin.instastars.classes.Animation;
import com.robin.instastars.classes.Search;
import com.robin.instastars.classes.SearchClick;
import com.robin.instastars.classes.TypefaceSingelton;
import com.robin.instastars.fragments.About_Us_Fragment;
import com.robin.instastars.fragments.Bookmark_Fragment;
import com.robin.instastars.fragments.List_Fragment;
import com.robin.instastars.fragments.MainPage_Fragment;
import com.robin.instastars.fragments.Search_Fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
public class MainActivity extends ActionBarActivity {

	//sliding drawer initialization
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	////////////////////////
	private ImageButton backbtn;
	private LinearLayout action_bar;
	private ImageButton search_action;
	private TextView title;
	private AutoCompleteTextView searchtext;
	private boolean doubleBackToExitPressedOnce=false;
	FragmentManager fragmentManager = getSupportFragmentManager();
	private final int ANIMATION_DURATION=500;
	private ImageButton ac_bookmark;
	private ImageButton drawer_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppContext.context=this;
		setContentView(R.layout.activity_main);
		FindViewById();

		searchtext.addTextChangedListener(new TextSearcher());
		backbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();

			}
		});
		drawer_initialization();
		if(savedInstanceState==null)
			GotoFragment(0);
		else
		{
			Fragment f=getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getBackStackEntryCount()-1);
			if(f!=null)
			{
				if(f instanceof MainPage_Fragment)
					Change_Fragment(f, "main",false);
				else if(f instanceof List_Fragment)
					Change_Fragment(f, "list",false);
				else if(f instanceof Bookmark_Fragment)
					Change_Fragment(f, "bookmark",true);
				else if(f instanceof About_Us_Fragment)
					Change_Fragment(f, "about_us",false);
				else if(f instanceof Search_Fragment)
					Change_Fragment(f, "search",false);
				else
					GotoFragment(0);
			}
		}
		if(drawer_btn!=null)
			drawer_btn.setOnClickListener(new DrawerClick());
		search_action.setOnClickListener(new SearchClick(searchtext,action_bar,this));
		ac_bookmark.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				GotoFragment(1);

			}
		});

	}

	private void GotoFragment(int id) {
		// update the main content by replacing com.mahdiaziz.fragments


		String name="";
		Fragment fragment = null;
		switch (id) {
		case 0:
			name="main";
			if(fragmentManager.findFragmentByTag(name)!=null)
			{
				fragment=fragmentManager.findFragmentByTag(name);
			}
			else
			{
				//				fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
				fragment = new MainPage_Fragment();
			}

			break;
		case 1:
			name="bookmark";
			if(fragmentManager.findFragmentByTag(name)!=null)
				fragment=fragmentManager.findFragmentByTag(name);
			else
				fragment=new Bookmark_Fragment();
			ac_bookmark.setVisibility(View.INVISIBLE);
			search_action.setVisibility(View.INVISIBLE);
			break;
		case 2:
			Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
					"mailto","Rabinfirm@gmail.com", null));
			i.putExtra(Intent.EXTRA_SUBJECT, "Subject");
			startActivity(Intent.createChooser(i, getString(R.string.sendusemail)));
			break;
		case 3:
			name="about_us";
			if(fragmentManager.findFragmentByTag(name)!=null)
				fragment=fragmentManager.findFragmentByTag(name);
			else
				fragment=new About_Us_Fragment();
			break;
		case 4:
			finish();
			break;
		}
		if(fragment!=null)
		{
			if(!(fragment instanceof Bookmark_Fragment))
			while (fragmentManager.popBackStackImmediate())
				;
			FragmentTransaction ft=fragmentManager.beginTransaction();
			ft.replace(R.id.fragment_container, fragment);
			ft.setCustomAnimations(R.anim.slide_left_to_right, R.anim.slide_right_to_left);
			ft.addToBackStack(name);
			ft.commit();
			mDrawerList.setItemChecked(id, true);
			mDrawerList.setSelection(id);
			setTitle(navMenuTitles[id]);
			mDrawerLayout.closeDrawer(mDrawerList);
		}
		// update selected item and title, then close the drawer

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
		super.onConfigurationChanged(newConfig);
	}
	@Override
	public void onBackPressed() {
		if(searchtext!=null)
		{
			if(searchtext.getVisibility()==View.VISIBLE)
			{
				searchtext.setText("");
				Animation anim=new Animation();
				Fragment f=getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getBackStackEntryCount()-1);
				View grid=null;
				if(f instanceof List_Fragment)
				{
					List_Fragment lf=(List_Fragment) f;
					lf.SetgridView();
					grid=lf.getgridview();
				}
				else if(f instanceof Search_Fragment)
				{
					GotoFragment(0);
				}
				else
				{
					MainPage_Fragment mf=(MainPage_Fragment) f;
					grid=mf.getgridview();

				}
				if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.HONEYCOMB_MR1)
				{
					anim.fadeOutToTop(searchtext,true,action_bar.getHeight()+AppContext.getdensity(5));
					 if(grid!=null)
						 anim.TranslateToTop(grid, true, action_bar.getHeight()+AppContext.getdensity(5));
				}
				else
				{
					action_bar.setVisibility(View.VISIBLE);
				}
				searchtext.postDelayed(new Runnable() {
					@Override
					public void run() {
						searchtext.setVisibility(View.INVISIBLE);
					}
				}, ANIMATION_DURATION-50);
				return;
			}
		}
		FragmentManager fm = getSupportFragmentManager();
		if(fm.getFragments().get(fm.getBackStackEntryCount()-1) instanceof Bookmark_Fragment)
		{
			ac_bookmark.setVisibility(View.VISIBLE);
			search_action.setVisibility(View.VISIBLE);
		}
		boolean flag_while_in=false;
		while (fm.getBackStackEntryCount() > 1) {
			fm.popBackStackImmediate();
			flag_while_in=true;
		}
		if (flag_while_in) {
			return;
		}
		if (doubleBackToExitPressedOnce) {
			super.onBackPressed();
			finish();
			System.exit(0);
			return;
		}

		Fragment f=fm.getFragments().get(fm.getBackStackEntryCount()-1);
		if(f instanceof Bookmark_Fragment)
		{

		}
		else if(!(f instanceof MainPage_Fragment))
			GotoFragment(0);
		this.doubleBackToExitPressedOnce = true;
		Toast.makeText(this, getResources().getString(R.string.back), Toast.LENGTH_SHORT).show();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				doubleBackToExitPressedOnce=false;                       
			}
		}, 2000);
	}
	private void FindViewById()
	{
		action_bar=(LinearLayout)findViewById(R.id.action_bar);
		search_action=(ImageButton)findViewById(R.id.ac_search);
		backbtn=(ImageButton) findViewById(R.id.ac_back);
		searchtext=(AutoCompleteTextView) findViewById(R.id.searchtext);
		searchtext.setTypeface(TypefaceSingelton.getFontNumber());
		ac_bookmark=(ImageButton) findViewById(R.id.ac_bookmark);
		title=(TextView) findViewById(R.id.title);
		title.setTypeface(TypefaceSingelton.getFontNumber(),Typeface.BOLD);
		drawer_btn=(ImageButton) findViewById(R.id.ac_options);
	}
	private class TextSearcher implements TextWatcher
	{

		@Override
		public void afterTextChanged(Editable s) {
			if(s.toString().length()>0)
			{
				Fragment f=fragmentManager.getFragments().get(fragmentManager.getBackStackEntryCount()-1);
				if(f instanceof List_Fragment)
				{
					List_Fragment lf=(List_Fragment) f;
					Search search=new Search();
					search.setKeyword(s.toString());
					search.setActors(lf.Actors);
					lf.SetgridView(search.getindexofsearch(lf.position));
				}
				else if(f instanceof MainPage_Fragment)
				{
					Search_Fragment sf=new Search_Fragment();
					sf.keyword=s.toString();
					FragmentTransaction ft=fragmentManager.beginTransaction();
					ft.replace(R.id.fragment_container, sf);
					ft.setCustomAnimations(R.anim.slide_left_to_right, R.anim.slide_right_to_left);
					ft.addToBackStack("search");
					ft.commit();
				}
				else if(f instanceof Search_Fragment)
				{
					Search_Fragment sf=(Search_Fragment) f;
					Search search=new Search();
					search.setKeyword(s.toString());
					search.setActors(sf.Actors);
					sf.SetgridView(search.getindexofsearch(sf.position));
				}
			}

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub

		}

	}
	private class SlideMenuClickListener implements
	ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if(position==0)
				return;
			// display view for selected nav drawer item
			GotoFragment(Integer.parseInt(String.valueOf(position-1)));
		}
	}
	private class DrawerClick implements View.OnClickListener
	{

		@Override
		public void onClick(View v) {

			if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
				mDrawerLayout.closeDrawer(Gravity.RIGHT);
			} else {
				mDrawerLayout.openDrawer(Gravity.RIGHT);
			}
		}

	}
	private void drawer_initialization()
	{
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		navDrawerItems = new ArrayList<NavDrawerItem>();
		//		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		//		getSupportActionBar().setHomeButtonEnabled(false);
		// adding nav drawer items to array
		// Main
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Contact Us
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// Question
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		//	Exit
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.heartup, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
				) {
			public void onDrawerClosed(View view) {
				title.setText(getResources().getString(R.string.instastars));
				supportInvalidateOptionsMenu();
			}
			public void onDrawerOpened(View drawerView) {
				title.setText(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				supportInvalidateOptionsMenu();
			}
			@Override
			public boolean onOptionsItemSelected(MenuItem item) {
				if (item != null && item.getItemId() == android.R.id.home) {
					if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
						mDrawerLayout.closeDrawer(Gravity.RIGHT);
					} else {
						mDrawerLayout.openDrawer(Gravity.RIGHT);
					}
				}
				return false;
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}
	private void Change_Fragment(Fragment fragment,String name,Boolean isiconinvisible)
	{
		if(isiconinvisible)
		{
			ac_bookmark.setVisibility(View.INVISIBLE);
			search_action.setVisibility(View.INVISIBLE);
		}

		while (fragmentManager.popBackStackImmediate())
			;
		FragmentTransaction ft=fragmentManager.beginTransaction();
		ft.replace(R.id.fragment_container, fragment);
		ft.setCustomAnimations(R.anim.slide_left_to_right, R.anim.slide_right_to_left);
		ft.addToBackStack(name);
		ft.commit();
	}
}
