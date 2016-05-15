package com.example.contentproviderdemo;

import android.R.integer;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class MyProvider extends ContentProvider{
	
	public static final int PERSON_DIR=0;
	public static final int PERSON_ITEM=1;
	
	public static final String AUTHORITY="com.example.contentproviderdemo.provider";
	public static final String TABLE="person";
	public static final String DATABASE="person.db";
	public static final String DIR="person";
	public static final String ITEM="person/#";
	
	private static UriMatcher uriMatcher;
	private MySqliteHelper helper;
	
	static{
		uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, DIR, PERSON_DIR);
		uriMatcher.addURI(AUTHORITY, ITEM, PERSON_ITEM);
	}

	
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		helper=new MySqliteHelper(getContext(), DATABASE, null, 2);
		return true;
	}

	

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		switch (uriMatcher.match(uri)) {
		case PERSON_DIR:
			return "vnd.android.cursor.dir/com.example.contentproviderdemo.provider.person";

        case PERSON_ITEM:
			
        	return "vnd.android.cursor.item/com.example.contentproviderdemo.provider.person";
			
		default:
			break;
		}
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		SQLiteDatabase db=helper.getWritableDatabase();
		Uri uriReturn = null;
		switch (uriMatcher.match(uri)) {
		case PERSON_DIR:

		case PERSON_ITEM:
			Log.e("provider", "start insert");
			long id=db.insert(TABLE, null, values);
			uriReturn=Uri.parse("content://"+AUTHORITY+"/person/"+id);
			break;
			
		default:
			break;
		}
		return uriReturn;
	}

	

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String orderBy) {
		// TODO Auto-generated method stub
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor cursor = null;
		switch (uriMatcher.match(uri)) {
		case PERSON_DIR:
			cursor=db.query(TABLE, projection, selection, selectionArgs, null, null, orderBy);
			break;

		case PERSON_ITEM:
			String id=uri.getPathSegments().get(1);
			cursor=db.query(TABLE, projection, "id=?", new String[]{id}, null, null, orderBy);
			break;
			
		default:
			break;
		}
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArg) {
		// TODO Auto-generated method stub
		SQLiteDatabase db=helper.getWritableDatabase();
		int row=0;
		switch (uriMatcher.match(uri)) {
		case PERSON_DIR:
			row=db.update(TABLE, values, selection, selectionArg);
			break;

		case PERSON_ITEM:
			String id=uri.getPathSegments().get(1);
			row=db.update(TABLE, values, "id=?", new String[]{id});
			break;
			
		default:
			break;
		}
		return row;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArg) {
		// TODO Auto-generated method stub
		SQLiteDatabase db=helper.getWritableDatabase();
		int row=0;
		switch (uriMatcher.match(uri)) {
		case PERSON_DIR:
			row=db.delete(TABLE, selection, selectionArg);
			break;

		case PERSON_ITEM:
			String id=uri.getPathSegments().get(1);
			row=db.delete(TABLE, "id=?", new String[]{id});
			break;
			
		default:
			break;
		}
		return row;
	}
}
