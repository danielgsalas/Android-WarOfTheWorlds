package com.appstoremarketresearch.android_waroftheworlds.model;

import android.support.v4.app.LoaderManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;

/**
 * <p>MartianTripodLoaderCallbacks creates the Loader for and
 * receives data from SQLite table "martian_tripod"</p>
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
public class MartianTripodLoaderCallbacks
    implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;
    private CursorAdapter cursorAdapter;
    private CursorHandler cursorHandler;

    public MartianTripodLoaderCallbacks(Context context) {
        this.context = context;
    }

    @Override
    public Loader<Cursor> onCreateLoader(
        int id,
        Bundle args) {

        Uri uri = AppContentProvider.CONTENT_URI_MARTIAN_TRIPOD;
        String[] projection = { "_id", "description" };
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = "_id ASC";

        return new CursorLoader(this.context, uri,
            projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public void onLoadFinished(
        Loader<Cursor> loader,
        Cursor cursor) {

        if (cursor == null) {
            String logtag = this.getClass().getSimpleName();
            Log.e(logtag, "onLoadFinished cursor is null");
        }
        else if (cursorAdapter != null) {
            
            Cursor oldCursor = cursorAdapter.swapCursor(cursor);

            if (oldCursor != null) {
                oldCursor.close();
            }
        }
        else if (cursorHandler != null) {
            cursorHandler.handleCursor(cursor);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        if (cursorAdapter != null) {
            
            Cursor oldCursor = cursorAdapter.swapCursor(null);

            if (oldCursor != null) {
                oldCursor.close();
            }
        }
        else if (cursorHandler != null) {
            cursorHandler.handleCursor(null);
        }
    }

    public void setCursorAdapter(CursorAdapter cursorAdapter) {
        this.cursorAdapter = cursorAdapter;
    }

    public void setCursorHandler(CursorHandler cursorHandler) {
        this.cursorHandler = cursorHandler;
    }

    public interface CursorHandler {
        void handleCursor(Cursor cursor);
    }
}
