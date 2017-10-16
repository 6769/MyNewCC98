package org.cc98.mycc98.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.cc98.mycc98.R;
import org.cc98.mycc98.fragment.base.BaseFragment;

import win.pipi.api.network.CC98APIInterface;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnPostFragmentInteractionListener}
 * interface.
 */
public class PostFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener {


    protected static final String BOARDID = "boardid";
    protected static final String POSTYTYPEKEY="type";

    protected int mBoardId;
    protected PostType mpostType;
    protected OnPostFragmentInteractionListener mListener;


    protected SwipeRefreshLayout mswipeRefreshLayout;
    protected RecyclerView mrecyclerView;
    protected CC98APIInterface iface;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PostFragment() {
    }



    @SuppressWarnings("unused")
    public static PostFragment newInstance() {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bundle args= getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        mswipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_swipeLayout);
        mrecyclerView = (RecyclerView) view.findViewById(R.id.fragment_recyclerview);

        mswipeRefreshLayout.setColorSchemeColors(Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.RED);

        mswipeRefreshLayout.setOnRefreshListener(this);

        /*HotTopicItemRecyclerViewAdapter adapter = new HotTopicItemRecyclerViewAdapter(
                DummyContent.ITEMS, mListener);
        mrecyclerView.setAdapter(adapter);*/


        return view;
    }

    @Override
    public void onRefresh() {
        throw new UnsupportedOperationException("needed to override!");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPostFragmentInteractionListener {

        void onListFragmentInteraction(int i);
    }

    public enum PostType{
        HOT,NEW,BOARD
    }
}
