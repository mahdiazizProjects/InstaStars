package com.robin.xmlparser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.robin.instastars.R;
import com.robin.instastars.classes.Actor_Class;

import android.content.Context;
import android.util.Log;
public class ActorDOMParser {

	public static final String LOG_TAG = "Actors";
	public static final String Actor_TAG = "Actor";
	public static final String FName_TAG = "Name";
	public static final String LName_TAG = "LastName";
	public static final String BTime_TAG = "Birth_Time";
	public static final String BPlace_TAG = "Birth_Place";
	public static final String Bio_TAG = "Biography";
	public static final String Insta_TAG = "Instagram";
	public static final String Images_TAG = "Images";
	public static final String Urlmain_TAG = "Url_Main";
	public static final String Prof_TAG = "Url_Prof";
	public static final String UrlHot_TAG = "Url_Hot";
	public static ArrayList<Actor_Class> parseXML(Context context,int position) {

		InputStream stream=null;
		if(position==0)
			stream = context.getResources().openRawResource(R.raw.actors);
		else if(position==1)
			stream = context.getResources().openRawResource(R.raw.sport);
		else if(position==2)
			stream = context.getResources().openRawResource(R.raw.music);
		SAXBuilder builder = new SAXBuilder();
		org.jdom2.Element rootNode=null;
		ArrayList<Actor_Class>Actors=new ArrayList<Actor_Class>();
		try {

			Document document = (Document) builder.build(stream);
			rootNode = document.getRootElement();
			List<org.jdom2.Element> Actorsnode = rootNode.getChildren(Actor_TAG);
			for (Element node : Actorsnode) {
				Actor_Class actor = new Actor_Class();
				actor.setFName(node.getChildText(FName_TAG).trim());
				actor.setLName(node.getChildText(LName_TAG).trim());
				actor.setBPlace(node.getChildText(BPlace_TAG).trim());
				actor.setBTime(node.getChildText(BTime_TAG).trim());
				actor.setBiog(node.getChildText(Bio_TAG));
				actor.setInsta(node.getChildText(Insta_TAG));
				Element imagesnode = node.getChild(Images_TAG);
				actor.setImgmain(imagesnode.getChildText(Urlmain_TAG).trim());
				actor.setImghot(imagesnode.getChildText(UrlHot_TAG).trim());
				actor.setImgprofile(imagesnode.getChildText(Prof_TAG).trim());
				Actors.add(actor);
			}
		} catch (IOException e) {
			Log.i(LOG_TAG, e.getMessage());
		} catch (JDOMException e) {
			Log.i(LOG_TAG, e.getMessage());
		}
		return Actors;
	}

}
