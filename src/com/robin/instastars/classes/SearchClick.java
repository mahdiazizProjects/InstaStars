package com.robin.instastars.classes;

import com.robin.instastars.AppContext;
import com.robin.instastars.MainActivity;
import com.robin.instastars.fragments.List_Fragment;
import com.robin.instastars.fragments.MainPage_Fragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

public class SearchClick implements View.OnClickListener
{

	private final AutoCompleteTextView layout_search_text;
	private final LinearLayout action_bar;
	private final MainActivity activity;
	public SearchClick(AutoCompleteTextView layout_search_text,LinearLayout action_bar,MainActivity activity)
	{
		this.layout_search_text=layout_search_text;
		this.action_bar=action_bar;
		this.activity=activity;
	}
	@Override
	public void onClick(View v) {

		if(layout_search_text.getVisibility()==View.INVISIBLE)
		{
			Animation anim=new Animation();
			if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.HONEYCOMB_MR1)
			{
				anim.fadeinTobottom(layout_search_text, true,action_bar.getHeight()+AppContext.getdensity(5));
				int fragment_index=-1;
				if(activity.getSupportFragmentManager().getBackStackEntryCount()>0)
				{
					fragment_index=activity.getSupportFragmentManager().getBackStackEntryCount()-1;
				}
				else
				{
					fragment_index=0;
				}
				Fragment f=activity.getSupportFragmentManager().getFragments().get(fragment_index);
				View grid_view=null;
				if(f instanceof List_Fragment)
				{
					List_Fragment lf=(List_Fragment) f;
					grid_view=lf.getgridview();
				}
				else if(f instanceof MainPage_Fragment)
				{
					MainPage_Fragment lf=(MainPage_Fragment) f;
					grid_view=lf.getgridview();
				}
				anim.fadeinTobottom(grid_view, true, action_bar.getHeight()+AppContext.getdensity(5));
				
			}
			else
				action_bar.setVisibility(View.GONE);
			layout_search_text.setVisibility(View.VISIBLE);
		}

	}

}
