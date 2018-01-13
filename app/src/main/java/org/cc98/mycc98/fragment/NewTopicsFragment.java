package org.cc98.mycc98.fragment;

import android.os.Bundle;

import org.cc98.mycc98.adapter.NewTopicRecyclerViewAdapter;

import java.util.ArrayList;

import rx.Observable;
import win.pipi.api.data.TopicInfo;

/**
 * Created by pipi6 on 2017/10/19.
 */

public class NewTopicsFragment extends BoardViewPostFragment {

    public static NewTopicsFragment newInstance(){

        NewTopicsFragment fragment=new NewTopicsFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(BOARD_KEY,0);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    protected void initUI() {
        adapter=new NewTopicRecyclerViewAdapter(mList,this);
        floatingActionButton.hide();
    }

    @Override
    protected Observable<ArrayList<TopicInfo>> genNewCall(int bid, int from, int to) {
        Observable<ArrayList<TopicInfo>> call = iface.getTopicNew(from, to - from);
        return call;
    }
}
