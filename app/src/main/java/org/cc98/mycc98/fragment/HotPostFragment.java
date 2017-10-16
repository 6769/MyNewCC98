package org.cc98.mycc98.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.cc98.mycc98.MainApplication;
import org.cc98.mycc98.R;
import org.cc98.mycc98.adapter.HotTopicItemRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import win.pipi.api.data.HotTopicInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotPostFragment extends PostFragment
        implements PostFragment.OnPostFragmentInteractionListener {

    private List<HotTopicInfo> hotTopicInfoList=new ArrayList<>();
    private HotTopicItemRecyclerViewAdapter adapter;

    public HotPostFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=super.onCreateView(inflater,container,savedInstanceState);

        adapter=new HotTopicItemRecyclerViewAdapter(hotTopicInfoList,this);

        mrecyclerView.setAdapter(adapter);


        if (hotTopicInfoList.size()==0)
            onRefresh();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onRefresh() {

        iface.getTopicHot().subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<ArrayList<HotTopicInfo>>() {
                    @Override
                    public void call(ArrayList<HotTopicInfo> hotTopicInfos) {
                        hotTopicInfoList.clear();
                        for(HotTopicInfo i:hotTopicInfos){
                            if (i.getReplyCount()==0)
                                continue;
                            String name=i.getAuthorName();
                            if(name==null ||name.isEmpty())
                                i.setAuthorName(getString(R.string.username_nobody));

                            hotTopicInfoList.add(i);
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<HotTopicInfo>>() {
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
                    public void onNext(ArrayList<HotTopicInfo> hotTopicInfos) {
                        adapter.notifyDataSetChanged();
                        mswipeRefreshLayout.setRefreshing(false);
                        //mkToast(getString(R.string.toast_tips_ok));
                    }
                });

    }



    @Override
    public void onListFragmentInteraction(int i) {
        mkToast(hotTopicInfoList.get(i).getTitle());

    }

}
