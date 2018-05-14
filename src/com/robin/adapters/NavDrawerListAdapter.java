package com.robin.adapters;
import java.util.ArrayList;

import javax.crypto.spec.PSource;

import com.robin.instastars.R;
import com.robin.instastars.classes.TypefaceSingelton;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavDrawerListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<NavDrawerItem> navDrawerItems;
	public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems){
		this.context = context;
		this.navDrawerItems = navDrawerItems;
	}

	@Override
	public int getCount() {
		return navDrawerItems.size()+1;
	}

	@Override
	public Object getItem(int position) {		
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			final LayoutInflater mInflater = (LayoutInflater)
					context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			if(position==0)
			{
				convertView = mInflater.inflate(R.layout.first_item_drawer, null);
			}
			else
			{
				convertView = mInflater.inflate(R.layout.drawer_list_item, null);
				ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
				TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
				imgIcon.setImageResource(navDrawerItems.get(position-1).getIcon());        
				txtTitle.setText(navDrawerItems.get(position-1).getTitle());
				txtTitle.setTypeface(TypefaceSingelton.getFontNumber(), Typeface.BOLD);
			}
			return convertView;

	}
}