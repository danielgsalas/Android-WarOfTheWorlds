package com.appstoremarketresearch.android_waroftheworlds.model;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.appstoremarketresearch.android_waroftheworlds.R;

/**
 * AppDatabaseHelper helps create, access and open the application database.
 *
 * @author <a href="http://www.appstoremarketresearch.com">
 * http://www.appstoremarketresearch.com</a>
 */
public class AppDatabaseHelper extends SQLiteOpenHelper {

	private Context context;

	public static final String DB_NAME = "com.appstoremarketresearch.android_waroftheworlds.db";
	public static final int DB_VERSION = 1;

	public AppDatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate (SQLiteDatabase db) {
		Resources res = context.getResources();
		db.execSQL(res.getString(R.string.sql_create_table_martian));
		db.execSQL(res.getString(R.string.sql_create_table_martian_tripod));
	}

	@Override
	public void onUpgrade (
		SQLiteDatabase db,
		int oldVersion,
		int newVersion) {

		switch(oldVersion) {
		case 1:
			// TBD
			break;

		case 2:
		default:
			// TBD
			break;
		}
	}
}
