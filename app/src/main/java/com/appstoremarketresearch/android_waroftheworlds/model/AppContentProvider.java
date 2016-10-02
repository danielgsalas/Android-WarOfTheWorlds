package com.appstoremarketresearch.android_waroftheworlds.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * AppContentProvider provides access to the application database.
 *
 * @author <a href="http://www.appstoremarketresearch.com">
 * http://www.appstoremarketresearch.com</a>
 */
public class AppContentProvider extends ContentProvider {

	private SQLiteOpenHelper dbHelper;

	public static final String AUTHORITY = 
		AppContentProvider.class.getCanonicalName();

	public static final Uri CONTENT_URI_MARTIAN =
		Uri.parse("content://" + AUTHORITY + "/martian");

	public static final Uri CONTENT_URI_MARTIAN_TRIPOD =
		Uri.parse("content://" + AUTHORITY + "/martian_tripod");

	@Override
	public int delete (
		Uri uri,
		String selection,
		String[] selectionArgs) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String uriText = uri.toString();
		String tablename = uriText.substring(uriText.lastIndexOf("/")+1);

		return db.delete(tablename, selection, selectionArgs);
	}

	@Override
	public String getType (Uri uri) {
		return null;
	}

	@Override
	public Uri insert (
		Uri uri,
		ContentValues values) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String uriText = uri.toString();
		String tablename = uriText.substring(uriText.lastIndexOf("/")+1);

		long id =  db.insert(tablename, null, values);
		getContext().getContentResolver().notifyChange(uri, null);
		return ContentUris.withAppendedId(uri, id);
	}

	@Override
	public boolean onCreate () {
		dbHelper = new AppDatabaseHelper(getContext());
		return true; // assume provider was successfully loaded
	}

	@Override
	public Cursor query (
		Uri uri,
		String[] projection,
		String selection,
		String[] selectionArgs,
		String sortOrder){

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String uriText = uri.toString();
		String tablename = uriText.substring(uriText.lastIndexOf("/")+1);

		String groupBy = null;
		String having = null;

		return db.query(tablename, projection,
			selection, selectionArgs,
			groupBy, having, sortOrder);
	}

	@Override
	public int update (
		Uri uri,
		ContentValues values,
		String selection,
		String[] selectionArgs) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String uriText = uri.toString();
		String tablename = uriText.substring(uriText.lastIndexOf("/")+1);

		return db.update(tablename, values, selection, selectionArgs);
	}
}
