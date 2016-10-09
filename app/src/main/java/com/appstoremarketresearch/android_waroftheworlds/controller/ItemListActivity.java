package com.appstoremarketresearch.android_waroftheworlds.controller;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.appstoremarketresearch.android_waroftheworlds.R;
import com.appstoremarketresearch.android_waroftheworlds.model.AppContentProvider;
import com.appstoremarketresearch.android_waroftheworlds.model.AppDatabaseHelper;
import com.appstoremarketresearch.android_waroftheworlds.view.ItemDetailFragmentOne;
import com.appstoremarketresearch.android_waroftheworlds.view.ItemDetailFragmentTwo;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity
    extends AppCompatActivity
    implements
        ItemDetailFragmentOne.OnFragmentInteractionListener,
        ItemDetailFragmentTwo.OnFragmentInteractionListener
{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    public static final String FRAGMENT_INDEX = "fragment_index";

    private static final String LOG_TAG = ItemListActivity.class.getSimpleName();

    /**
     * createFragmentForPosition
     */
    protected static Fragment createFragmentForPosition(int position) {

        Fragment fragment;

        switch (position) {
            case 1:
                fragment = new ItemDetailFragmentTwo();
                break;

            case 0:
            default:
                fragment = new ItemDetailFragmentOne();
                break;
        }

        return fragment;
    }

    /**
     * initializeDatabaseRows
     */
    private void initializeDatabaseRows() {
        ContentResolver resolver = getContentResolver();

        Cursor cursor = resolver.query(AppContentProvider.CONTENT_URI_MARTIAN_TRIPOD,
            null, null, null, null);

        if (cursor == null) {
            Log.e(LOG_TAG, "Failed to query database");
        }
        else if (cursor.getCount() == 0) {

            int rowCount = 6;

            for (int i = 0; i < rowCount; i++) {

                int id = (int)(Math.random() * 1000);

                ContentValues values = new ContentValues();
                //values.put("_id", id); // autoincremented
                values.put("name", "martian-" + id);

                double random = Math.random();

                if (random > 0.8) {
                    values.put("observation", "Watching from tripod");
                }
                else if (random > 0.6) {
                    values.put("observation", "Crawling away from tripod");
                }
                else if (random > 0.4) {
                    values.put("observation", "Crawling toward tripod");
                }
                else if (random > 0.2) {
                    values.put("observation", "Restraining prisoner");
                }
                else {
                    values.put("observation", "Operating heat ray");
                }

                resolver.insert(AppContentProvider.CONTENT_URI_MARTIAN, values);
            }

            for (int i = 0; i < rowCount; i++) {

                ContentValues values = new ContentValues();
                //values.put("_id", i); // autoincremented

                double random = Math.random();

                if (random > 0.6) {
                    values.put("description", "Fighting-machine with heat ray");
                }
                else if (random > 0.3) {
                    values.put("description", "Anti-personnel machine with black smoke");
                }
                else {
                    values.put("description", "Agri-machine with red weed planter");
                }

                resolver.insert(AppContentProvider.CONTENT_URI_MARTIAN_TRIPOD, values);
            }
        }
        else {
            String[] columnNames = cursor.getColumnNames();

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                String databaseValues = "martian_tripod";

                for (int i = 0; i < columnNames.length; i++) {
                    databaseValues += " " + cursor.getString(i);
                }

                Log.d(LOG_TAG, databaseValues);

                cursor.moveToNext();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        deleteDatabase(AppDatabaseHelper.DB_NAME);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // NO OP
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeDatabaseRows();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter());
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private static final int FRAGMENT_COUNT = 2;

        private String[] menuItemText = {
            "List of Observed Martians",
            "List of Martian Tripods"
        };

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            holder.mIdView.setText(menuItemText[position]);

            final int listPosition = position;

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mTwoPane) {

                        Fragment fragment = createFragmentForPosition(listPosition);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();

                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ItemDetailActivity.class);
                        intent.putExtra(FRAGMENT_INDEX, listPosition);
                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return FRAGMENT_COUNT;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
            }
        }
    }
}
