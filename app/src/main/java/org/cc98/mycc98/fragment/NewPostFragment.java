package org.cc98.mycc98.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.andview.refreshview.XRefreshView;

import org.cc98.mycc98.MainApplication;
import org.cc98.mycc98.R;
import org.cc98.mycc98.adapter.NewTopicRecyclerViewAdapter;
import org.cc98.mycc98.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import win.pipi.api.data.HotTopicInfo;
import win.pipi.api.data.TopicInfo;
import win.pipi.api.network.CC98APIInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewPostFragment extends BaseFragment
        implements
        PostFragment.OnPostFragmentInteractionListener,XRefreshView.XRefreshViewListener {

    public static final String BOARD_KEY="boardId";


    protected int boardId=0;
    protected CC98APIInterface iface;
    protected RecyclerView mrecyclerView;
    protected XRefreshView xRefreshView;
    protected NewTopicRecyclerViewAdapter adapter;
    protected List<TopicInfo> topicInfoList=new ArrayList<>();

    public NewPostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        iface= MainApplication.getApiInterface();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newtopicview, container, false);
        mrecyclerView =(RecyclerView)view.findViewById(R.id.fragment_recyclerview_newtopic);
        xRefreshView=(XRefreshView)view.findViewById(R.id.fragment_newpost_xrefresh_container);



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
    protected void initAdapter(){
        adapter=new NewTopicRecyclerViewAdapter(topicInfoList,this);

    }

    @Override
    public void onLoadMore(boolean isSilence) {

        //get more data;
        xRefreshView.stopLoadMore();

    }

    @Override
    public void onRefresh(boolean isPullDown) {
        onRefresh();
    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY) {

    }

    @Override
    public void onRelease(float direction) {

    }


    //re write;
    protected Observable<ArrayList<TopicInfo>> genNewCall(int bid,int from,int to){

        return iface.getTopicNew(from,to);
    }

    @Override
    public void onRefresh() {

        Observable<ArrayList<TopicInfo>> call=genNewCall(boardId,1,20);
        call.subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<ArrayList<TopicInfo>>() {
                    @Override
                    public void call(ArrayList<TopicInfo> hotTopicInfos) {
                        topicInfoList.clear();
                        for(TopicInfo i:hotTopicInfos){
                            String name=i.getAuthorName();
                            if(name==null ||name.isEmpty())
                                i.setAuthorName(getString(R.string.username_nobody));
                            topicInfoList.add(i);
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<TopicInfo>>() {
                    @Override
                    public void onCompleted() {
                        xRefreshView.stopRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        xRefreshView.stopRefresh();
                        mkToast(e.toString());
                    }

                    @Override
                    public void onNext(ArrayList<TopicInfo> hotTopicInfos) {
                        adapter.notifyDataSetChanged();
                        xRefreshView.stopRefresh();
                        //mkToast(getString(R.string.toast_tips_ok));
                    }
                });


    }


    @Override
    public void onListFragmentInteraction(int i) {

    }
}
