package org.cc98.mycc98.fragment.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.cc98.mycc98.MainApplication;
import org.cc98.mycc98.R;
import org.cc98.mycc98.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import win.pipi.api.network.CC98APIInterface;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnPostFragmentInteractionListener}
 * interface.
 *
 * When try to apply more feature,see: http://www.easydone.cn/2015/10/26/
 */
public abstract class BaseSwipeRefreshFragment<T> extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener {


    protected static final String BOARDID = "boardid";
    protected static final String POSTYTYPEKEY="type";

    protected int mBoardId;

    protected OnPostFragmentInteractionListener mListener;

    protected List<T> mLists=new ArrayList<T>();
    protected RecyclerView.Adapter adapter;

    protected SwipeRefreshLayout mswipeRefreshLayout;
    protected RecyclerView mrecyclerView;
    protected CC98APIInterface iface;
    protected Observer<ArrayList<T>> refreshObserver;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BaseSwipeRefreshFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bundle args= getArguments();

        iface= MainApplication.getApiInterface();
        refreshObserver =new Observer<ArrayList<T>>() {
            @Override
            public void onCompleted() {
                mswipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                mswipeRefreshLayout.setRefreshing(false);
                mkToast(e.toString());
            }

            @Override
            public void onNext(ArrayList<T> ts) {
                adapter.notifyDataSetChanged();
                mswipeRefreshLayout.setRefreshing(false);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_postbase, container, false);
        mswipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_swipeLayout);
        mrecyclerView = (RecyclerView) view.findViewById(R.id.fragment_recyclerview);

        mswipeRefreshLayout.setColorSchemeColors(Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.RED);
        mswipeRefreshLayout.setOnRefreshListener(this);

        initUI();
        mrecyclerView.setAdapter(adapter);
        if(mLists.size()==0){
            onRefresh();
        }

        return view;
    }


    protected abstract void initUI();




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


}
