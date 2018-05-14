package com.instastars.tables;
import java.util.ArrayList;
import java.util.Date;

import com.robin.instastars.AppContext;
import com.robin.instastars.classes.Actor_Class;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

public class BookmarkTbl {
	public static final String TABLE = "bookmark";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_Bookmark_Date="bookmarktime";
	public static final String COLUMN_FName_TAG = "Name";
	public static final String COLUMN_LName_TAG = "LastName";
	public static final String COLUMN_BTime_TAG = "Birth_Time";
	public static final String COLUMN_BPlace_TAG = "Birth_Place";
	public static final String COLUMN_Bio_TAG = "Biography";
	public static final String COLUMN_Insta_TAG = "Instagram";
	public static final String COLUMN_Urlmain_TAG = "Url_Main";
	public static final String COLUMN_Prof_TAG = "Url_Prof";
	public static final String COLUMN_UrlHot_TAG = "Url_Hot";
	public int id;
	public String FName_TAG;
	public String LName_TAG;
	public String BTime_TAG;
	public String BPlace_TAG;
	public String Bio_TAG;
	public String Insta_TAG;
	public String Images_TAG;
	public String Urlmain_TAG;
	public String Prof_TAG;
	public String UrlHot_TAG;
	public static final String CREATE_TABLE = "create table "
			+ TABLE + "("
			+ COLUMN_ID + " integer primary key , "
			+ COLUMN_Bookmark_Date+" text ,"
			+ COLUMN_Bio_TAG+" text ,"
			+ COLUMN_BPlace_TAG+" text ,"
			+ COLUMN_BTime_TAG+" text ,"
			+ COLUMN_FName_TAG+" text ,"
			+ COLUMN_Insta_TAG+" text ,"
			+ COLUMN_LName_TAG+" text ,"
			+ COLUMN_Prof_TAG+" text ,"
			+ COLUMN_UrlHot_TAG+" text ,"
			+ COLUMN_Urlmain_TAG+" text "
			+ ");";

	public void Insert(){
		DatabaseHelper dbhelper=
				new DatabaseHelper(AppContext.getAppContext());
		ContentValues cv = new ContentValues();
		//		DatabaseHelper dbhelper = new DatabaseHelper();
		cv.put(COLUMN_ID, id);
		final Date date=new Date();
		String datetime=String.valueOf(date.getTime());
		cv.put(COLUMN_Bookmark_Date, datetime);
		cv.put(COLUMN_Bio_TAG, Bio_TAG);
		cv.put(COLUMN_BPlace_TAG, BPlace_TAG);
		cv.put(COLUMN_BTime_TAG, BTime_TAG);
		cv.put(COLUMN_FName_TAG, FName_TAG);
		cv.put(COLUMN_Insta_TAG, Insta_TAG);
		cv.put(COLUMN_LName_TAG, LName_TAG);
		cv.put(COLUMN_Prof_TAG, Prof_TAG);
		cv.put(COLUMN_UrlHot_TAG, UrlHot_TAG);
		cv.put(COLUMN_Urlmain_TAG, Urlmain_TAG);
		try {
			dbhelper.db.insertOrThrow(TABLE, null, cv);

		} catch (SQLException ex) {
			dbhelper.db.delete(TABLE, COLUMN_ID+"=?", new String[] {String.valueOf(id)});

			dbhelper.db.insertOrThrow(TABLE, null, cv);
		}
		finally
		{
			dbhelper.close();
		}
	}


	public boolean Exists (int newsId){
		DatabaseHelper dbhelper=
				new DatabaseHelper(AppContext.getAppContext());
		Cursor cursor = dbhelper.db.query(TABLE
				, new String[] {COLUMN_ID}
		, COLUMN_ID+"=?"
		, new String[] {String.valueOf(newsId)}, null, null, null);
		if (cursor.moveToFirst()){
			while (!cursor.isAfterLast()) {
				cursor.close();
				//				dbhelper.close();
				return true;
			}
		}
		cursor.close();
		dbhelper.close();
		return false;
	}
	public static boolean find(int Id)
	{
		DatabaseHelper dbhelper=
				new DatabaseHelper(AppContext.getAppContext());
		boolean result=false;
		try
		{
			Cursor cursor = dbhelper.db.query(TABLE
					, new String[] {COLUMN_ID,COLUMN_Bookmark_Date
			}
			, COLUMN_ID+"=?"
			, new String[] {String.valueOf(Id)}, null, null, null);

			if (cursor.moveToFirst()){
				result=true;
			}
			cursor.close();
		}
		catch(Exception e)
		{
			return result;
		}
		finally
		{
			dbhelper.close();
		}
		return result;
	}

	public static void Delete(int Id){
		DatabaseHelper dbhelper=
				new DatabaseHelper(AppContext.getAppContext());
		dbhelper.db.delete(TABLE, COLUMN_ID+"=?", new String[] {String.valueOf(Id)});
		dbhelper.close();
	}

	public void DeleteAll(){
		DatabaseHelper dbhelper=
				new DatabaseHelper(AppContext.getAppContext());
		dbhelper.db.delete(TABLE, null, null);
		dbhelper.close();
	}
	public ArrayList<Actor_Class> GetBookmark(){
		ArrayList<Actor_Class> bk = new ArrayList<Actor_Class>();
		DatabaseHelper dbhelper=
				new DatabaseHelper(AppContext.getAppContext());
		//		DatabaseHelper dbhelper = new DatabaseHelper();
		Cursor cursor = dbhelper.db.query(TABLE
				, new String[] {COLUMN_ID,COLUMN_Bio_TAG,COLUMN_Bookmark_Date,
				COLUMN_BPlace_TAG,COLUMN_BTime_TAG,COLUMN_FName_TAG,
				COLUMN_Insta_TAG,COLUMN_LName_TAG,
				COLUMN_Prof_TAG,COLUMN_UrlHot_TAG,COLUMN_Urlmain_TAG}
		, null
		, null, null, null, COLUMN_Bookmark_Date +
				" DESC");
		if (cursor.moveToFirst()){
			while (!cursor.isAfterLast()) {
				// an attempt to remove zero bookmark ids from being shown!
				if(cursor.getInt(0)==0)
				{
					cursor.moveToNext();
					continue;
				}
				Actor_Class tmp = new Actor_Class();
				tmp.setBiog(cursor.getString(1));
				tmp.setBPlace(cursor.getString(3));
				tmp.setBTime(cursor.getString(4));
				tmp.setFName(cursor.getString(5));
				tmp.setInsta(cursor.getString(6));
				tmp.setLName(cursor.getString(7));
				tmp.setImgprofile(cursor.getString(8));
				tmp.setImghot(cursor.getString(9));
				tmp.setImgmain(cursor.getString(10));
				cursor.moveToNext();
				bk.add(tmp);
			}
		}
		cursor.close();
		dbhelper.close();
		return bk;
	}
}