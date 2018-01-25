package org.cc98.mycc98.fragment.base;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.andview.refreshview.XRefreshView;
import com.orhanobut.logger.Logger;

import org.cc98.mycc98.MainApplication;
import org.cc98.mycc98.R;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
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
    protected FloatingActionButton floatingActionButton;
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
                //mkToast(e.toString());
                Logger.e(e, "e");
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
        View view = inflater.inflate(R.layout.fragment_normaltopicview, container, false);
        mrecyclerView = (RecyclerView) view.findViewById(R.id.fragment_recyclerview_newtopic);
        xRefreshView = (XRefreshView) view.findViewById(R.id.fragment_newpost_xrefresh_container);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fragment_writenewtopic_floatingActionButton);


        xRefreshView.setPinnedTime(800);
        xRefreshView.setMoveForHorizontal(true);
        xRefreshView.setPullLoadEnable(true);
        xRefreshView.setAutoLoadMore(false);
        xRefreshView.enableReleaseToLoadMore(true);
        xRefreshView.enableRecyclerViewPullUp(true);
        xRefreshView.enablePullUpWhenLoadCompleted(true);
        xRefreshView.setXRefreshViewListener(this);



        initUI();

        mrecyclerView.setAdapter(adapter);
        if(mList.size()==0){
            xRefreshView.startRefresh();
            //onRefresh();
        }

        return view;
    }


    protected abstract void initUI();

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY) {

    }

    @Override
    public void onRelease(float direction) {

    }

    @Override
    public void onRefresh(boolean isPullDown) {
        Logger.w("not use this!");
    }


    protected abstract Observable<ArrayList<T>> genNewCall(int bid, int from, int to);


}
