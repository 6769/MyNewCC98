package org.cc98.mycc98.fragment.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.andview.refreshview.XRefreshView;
import com.orhanobut.logger.Logger;

import org.cc98.mycc98.MainApplication;
import org.cc98.mycc98.R;
import org.cc98.mycc98.adapter.NewTopicRecyclerViewAdapter;
import org.cc98.mycc98.fragment.base.BaseFragment;
import org.cc98.mycc98.fragment.base.BaseSwipeRefreshFragment;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import win.pipi.api.data.TopicInfo;
import win.pipi.api.network.CC98APIInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BasePullPushSwipeFragment<T> extends BaseFragment
        implements
        BaseSwipeRefreshFragment.OnPostFragmentInteractionListener,
        XRefreshView.XRefreshViewListener {

    public static final String BOARD_KEY = "boardId";


    protected int boardId = 0;

    protected boolean isNetworking = false;
    protected CC98APIInterface iface;
    protected RecyclerView mrecyclerView;
    protected XRefreshView xRefreshView;
    protected RecyclerView.Adapter adapter;
    protected Observer<List<T>> listSubscriber;
    protected List<T> mList = new ArrayList<>();

    public BasePullPushSwipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iface = MainApplication.getApiInterface();
        listSubscriber = new Observer<List<T>>() {
            @Override
            public void onCompleted() {
                xRefreshView.stopRefresh();
                xRefreshView.stopLoadMore();
            }

            @Override
            public void onError(Throwable e) {
                xRefreshView.stopRefresh();
                xRefreshView.stopLoadMore();
                mkToast(e.toString());
                Logger.e(e, "error???");
            }

            @Override
            public void onNext(List<T> normalTopicInfos) {
                adapter.notifyDataSetChanged();
                xRefreshView.stopRefresh();
                xRefreshView.stopLoadMore();
                //mkToast(getString(R.string.toast_tips_ok));
            }
        };
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newtopicview, container, false);
        mrecyclerView = (RecyclerView) view.findViewById(R.id.fragment_recyclerview_newtopic);
        xRefreshView = (XRefreshView) view.findViewById(R.id.fragment_newpost_xrefresh_container);


        xRefreshView.setPinnedTime(800);
        xRefreshView.setMoveForHorizontal(true);
        xRefreshView.setPullLoadEnable(true);
        xRefreshView.setAutoLoadMore(false);
        xRefreshView.enableReleaseToLoadMore(true);
        xRefreshView.enableRecyclerViewPullUp(true);
        xRefreshView.enablePullUpWhenLoadCompleted(true);
        xRefreshView.setXRefreshViewListener(this);


        initAdapter();
        mrecyclerView.setAdapter(adapter);
        if(mList.size()==0){
            onRefresh();
        }

        return view;
    }


    protected abstract void initAdapter();

    /*@Override
    public void onLoadMore(boolean isSilence) {

        mkToast("loadmore");



    }

    @Override
    public void onRefresh() {

        updatePages(1, 20, true);
    }*/

    @Override
    public void onRefresh(boolean isPullDown) {
        Logger.w("not use this!");
    }


    protected abstract Observable<ArrayList<T>> genNewCall(int bid, int from, int to);


}
