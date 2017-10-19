package org.cc98.mycc98.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.cc98.mycc98.R;
import org.cc98.mycc98.adapter.HotTopicItemRecyclerViewAdapter;
import org.cc98.mycc98.fragment.base.BaseSwipeRefreshFragment;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import win.pipi.api.data.HotTopicInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotTopicsFragment extends BaseSwipeRefreshFragment<HotTopicInfo>
        implements BaseSwipeRefreshFragment.OnPostFragmentInteractionListener {

    //private List<HotTopicInfo> mLists =new ArrayList<>();

    public HotTopicsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initUI() {
        adapter=new HotTopicItemRecyclerViewAdapter(mLists,this);
        mrecyclerView.setAdapter(adapter);
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
                        mLists.clear();
                        for(HotTopicInfo i:hotTopicInfos){
                            if (i.getReplyCount()==0)
                                continue;
                            String name=i.getAuthorName();
                            if(name==null ||name.isEmpty())
                                i.setAuthorName(getString(R.string.username_nobody));

                            mLists.add(i);
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(refreshObserver);

    }



    @Override
    public void onListFragmentInteraction(int i) {
        mkToast(mLists.get(i).getTitle());

    }

}
