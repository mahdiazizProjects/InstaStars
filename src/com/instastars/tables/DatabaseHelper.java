package com.instastars.tables;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper; 
//import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{

	private static final String DBNAME = "dbInstaStars";
	//private DatabaseHelper dbHelper;
	public SQLiteDatabase db; 
	private final static int dbVersion = 2;


	//private final Context myContext;

	public DatabaseHelper(Context context) {
		super(context, DBNAME, null, dbVersion);
		open();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(BookmarkTbl.CREATE_TABLE);
			/*FileCache fc = new FileCache(AppContext.getAppContext());
			  fc.clear();*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//				  Log.w(DatabaseHelper.class.getName(),
//					        "Upgrading database from version " + oldVersion + " to "
//					            + newVersion + ", which will destroy all 
		db.execSQL("DROP TABLE IF EXISTS " + BookmarkTbl.TABLE);
		onCreate(db);
	}

	public void open() throws SQLException {
		//dbHelper = new DatabaseHelper(myContext);
		db = getWritableDatabase();
		//return this;
	}

	public void close() {
		db.close();
	}





}
