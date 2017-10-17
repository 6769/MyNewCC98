package org.cc98.mycc98.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.andview.refreshview.XRefreshView;
import com.orhanobut.logger.Logger;

import org.cc98.mycc98.MainApplication;
import org.cc98.mycc98.R;
import org.cc98.mycc98.adapter.NewTopicRecyclerViewAdapter;
import org.cc98.mycc98.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import win.pipi.api.data.TopicInfo;
import win.pipi.api.network.CC98APIInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewPostFragment extends BaseFragment
        implements
        PostFragment.OnPostFragmentInteractionListener, XRefreshView.XRefreshViewListener {

    public static final String BOARD_KEY = "boardId";


    protected int boardId = 0;

    protected boolean isNetworking=false;
    protected CC98APIInterface iface;
    protected RecyclerView mrecyclerView;
    protected XRefreshView xRefreshView;
    protected RecyclerView.Adapter adapter;
    protected List<TopicInfo> topicInfoList = new ArrayList<>();

    public NewPostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        iface = MainApplication.getApiInterface();
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
        //adapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
        xRefreshView.enableReleaseToLoadMore(true);
        xRefreshView.enableRecyclerViewPullUp(true);
        xRefreshView.enablePullUpWhenLoadCompleted(true);
        xRefreshView.setXRefreshViewListener(this);


        initAdapter();

        //TODO: abstract a baseclass for post view;
        mrecyclerView.setAdapter(adapter);

        return view;
    }


    //re write
    protected void initAdapter() {
        adapter = new NewTopicRecyclerViewAdapter(topicInfoList, this);

    }

    @Override
    public void onLoadMore(boolean isSilence) {

        mkToast("loadmore");
        int currentlen = topicInfoList.size();
        updatePages(currentlen + 1, currentlen + 20, false);


    }

    @Override
    public void onRefresh() {

        updatePages(1, 20, true);
    }

    @Override
    public void onRefresh(boolean isPullDown) {

    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY) {

    }

    @Override
    public void onRelease(float direction) {

    }


    //re write;
    protected Observable<ArrayList<TopicInfo>> genNewCall(int bid, int from, int to) {

        return iface.getTopicNew(from, to);
    }


    protected Subscriber<ArrayList<TopicInfo>> listSubscriber = new Subscriber<ArrayList<TopicInfo>>() {
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
        public void onNext(ArrayList<TopicInfo> normalTopicInfos) {
            adapter.notifyDataSetChanged();
            xRefreshView.stopRefresh();
            xRefreshView.stopLoadMore();
            mkToast(getString(R.string.toast_tips_ok));
        }
    };


    protected void updatePages(int from, int to, final boolean clearold) {
        Observable<ArrayList<TopicInfo>> call = genNewCall(boardId, from, to);
        call.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                //.observeOn(Schedulers.io())
                .doOnNext(new Action1<ArrayList<TopicInfo>>() {
                    @Override
                    public void call(ArrayList<TopicInfo> normalTopicInfos) {
                        if (clearold) {
                            topicInfoList.clear();
                        }

                        for (TopicInfo i : normalTopicInfos) {
                            String name = i.getAuthorName();
                            if (name == null || name.isEmpty())
                                i.setAuthorName(getString(R.string.username_nobody));
                            topicInfoList.add(i);
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(listSubscriber);

    }


    @Override
    public void onListFragmentInteraction(int i) {

    }
}
