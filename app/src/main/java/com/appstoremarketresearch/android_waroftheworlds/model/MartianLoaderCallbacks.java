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
 * <p>MartianLoaderCallbacks creates the Loader for and
 * receives data from SQLite table "martian"</p>
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
public class MartianLoaderCallbacks
    implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;
    private CursorAdapter cursorAdapter;
    private CursorHandler cursorHandler;

    public MartianLoaderCallbacks(Context context) {
        this.context = context;
    }

    @Override
    public Loader<Cursor> onCreateLoader(
        int id,
        Bundle args) {

        Uri uri = AppContentProvider.CONTENT_URI_MARTIAN;
        return new CursorLoader(this.context, uri, null, null, null, null);
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
            
            if (cursorAdapter.getCursor() != null) {
                cursorAdapter.getCursor().close();
            }
            
            cursorAdapter.swapCursor(cursor);
        }
        else if (cursorHandler != null) {
            cursorHandler.handleCursor(cursor);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        if (cursorAdapter != null) {
            
            if (cursorAdapter.getCursor() != null) {
                cursorAdapter.getCursor().close();
            }
            
            cursorAdapter.swapCursor(null);
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
