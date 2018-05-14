package com.robin.adapters;
import java.util.ArrayList;
import java.util.List;

import makeramen.RoundedImageView;

import com.instastars.widgets.AspectImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.robin.instastars.R;
import com.robin.instastars.classes.Category;
import com.robin.instastars.classes.TypefaceSingelton;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Home_Adapter extends BaseAdapter {

	private List<Category>Categories;
	Context context;
	LayoutInflater mInflater;
	DisplayImageOptions options;
	static class HoldView
	{
		ImageView image;
		TextView title;
	}
	private void Fill()
	{
		Category c=new Category();
		c.setImg(R.drawable.star1);
		String[]cat=context.getResources().getStringArray(R.array.categories);
		c.setTitle(cat[0]);
		Categories.add(c);
		c=new Category();
		c.setImg(R.drawable.star2);
		cat=context.getResources().getStringArray(R.array.categories);
		c.setTitle(cat[1]);
		Categories.add(c);
		c=new Category();
		c.setImg(R.drawable.star3);
		cat=context.getResources().getStringArray(R.array.categories);
		c.setTitle(cat[2]);
		Categories.add(c);
	}
	public Home_Adapter(Context context)
	{
		this.context=context;
		mInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Categories=new ArrayList<Category>();
		Fill();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Categories.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return Categories.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	final Typeface Fontheader=  TypefaceSingelton.getFontNumber();
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HoldView item=null;
		if(convertView==null)
		{
			convertView = mInflater.inflate(R.layout.list_item_main, parent, false);
			item=new HoldView();
//			item.image=(RoundedImageView) convertView.findViewById(R.id.image_aks);
//			item.title=(TextView) convertView.findViewById(R.id.group_text);
			item.image=(AspectImageView) convertView.findViewById(R.id.image_aks);
			item.title=(TextView) convertView.findViewById(R.id.group_text);
			convertView.setTag(item);
		}
		else
			item=(HoldView) convertView.getTag();
		item.image.setImageResource(Categories.get(position).getImg());
		item.title.setText(Categories.get(position).getTitle());
		item.title.setTypeface(Fontheader);
		return convertView;
	}

}