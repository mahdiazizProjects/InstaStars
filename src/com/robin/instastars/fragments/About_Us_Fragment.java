package com.robin.instastars.fragments;
import com.robin.instastars.R;
import com.robin.instastars.classes.TypefaceSingelton;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class About_Us_Fragment extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootview=inflater.inflate(R.layout.about_us,container,false);
		LinearLayout me=(LinearLayout) rootview.findViewById(R.id.me);
		TextView tv=(TextView) me.findViewById(R.id.title);
		tv.setText(getResources().getString(R.string.me));
		tv.setTypeface(TypefaceSingelton.getFontNumber());
		LinearLayout mohammad=(LinearLayout) rootview.findViewById(R.id.mohammad);
		TextView tv2=(TextView) mohammad.findViewById(R.id.title);
		ImageView im=(ImageView) mohammad.findViewById(R.id.list_image);
		im.setImageResource(R.drawable.mo);
		tv2.setText(getResources().getString(R.string.moh));
		tv2.setTypeface(TypefaceSingelton.getFontNumber());
		LinearLayout azar=(LinearLayout) rootview.findViewById(R.id.azar);
		TextView tv3=(TextView) azar.findViewById(R.id.title);
		tv3.setText(getResources().getString(R.string.azar));
		tv3.setTypeface(TypefaceSingelton.getFontNumber());
		
		return rootview;
	}
}
