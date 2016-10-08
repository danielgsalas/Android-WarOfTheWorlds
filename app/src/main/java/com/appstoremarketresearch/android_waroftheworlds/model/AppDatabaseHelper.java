package com.appstoremarketresearch.android_waroftheworlds.model;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.appstoremarketresearch.android_waroftheworlds.R;

/**
 * <p>AppDatabaseHelper helps create, access and open the application database.</p>
 *
 * <p>Copyright 2016 <a href="http://www.appstoremarketresearch.com">
 * http://www.appstoremarketresearch.com</a></p>
 *
 * <p>This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.</p>
 *
 * <p>This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.</p>
 *
 * <p>You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <a href="http://www.gnu.org/licenses">
 * http://www.gnu.org/licenses</a>.</p>
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
