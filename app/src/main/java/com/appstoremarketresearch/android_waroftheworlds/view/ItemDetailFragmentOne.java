package com.appstoremarketresearch.android_waroftheworlds.view;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appstoremarketresearch.android_waroftheworlds.R;
import com.appstoremarketresearch.android_waroftheworlds.model.MartianLoaderCallbacks;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ItemDetailFragmentOne.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ItemDetailFragmentOne#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemDetailFragmentOne extends ListFragment {

    private SimpleCursorAdapter mAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ItemDetailFragmentOne() {
        // Required empty public constructor
    }

    /**
     * initializeContentLoader
     */
    private void initializeContentLoader() {

        String[] columns = new String[] {
            "_id", "name", "observation" };

        int[] viewIds = new int[] {
            R.id.text1, R.id.text2, R.id.text3 };

        mAdapter = new SimpleCursorAdapter(getActivity(),
            R.layout.fragment_one_list_item, null, columns, viewIds, 0);

        setListAdapter(mAdapter);

        MartianLoaderCallbacks callbacks = new MartianLoaderCallbacks(getActivity());
        callbacks.setCursorAdapter(mAdapter);

        int loaderId = callbacks.getClass().getSimpleName().hashCode();
        getLoaderManager().initLoader(loaderId, null, callbacks);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemDetailFragmentOne.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemDetailFragmentOne newInstance(String param1, String param2) {
        ItemDetailFragmentOne fragment = new ItemDetailFragmentOne();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        initializeContentLoader();

        //testCursorHandler();
    }

    // Testing using the MartianLoader.CursorHandler interface
    private void testCursorHandler() {
        MartianLoaderCallbacks callbacks = new MartianLoaderCallbacks(getActivity());

        callbacks.setCursorHandler(new MartianLoaderCallbacks.CursorHandler() {
            @Override
            public void handleCursor(Cursor cursor) {

                if (cursor != null) {
                    cursor.moveToFirst();

                    while (!cursor.isAfterLast()) {
                        // TODO: read from cursor
                        cursor.moveToNext();
                    }
                }
            }
        });

        int callbacksId = callbacks.getClass().getSimpleName().hashCode();
        getLoaderManager().initLoader(callbacksId, null, callbacks);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.item_detail_list_view, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
