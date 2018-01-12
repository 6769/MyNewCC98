package org.cc98.mycc98.fragment;

import android.os.Bundle;

import org.cc98.mycc98.adapter.NormalTopicRecyclerViewAdapter;

import java.util.ArrayList;

import rx.Observable;
import win.pipi.api.data.TopicInfo;

/**
 * Created by pipi6 on 2018/1/12.
 */

public class SearchViewTopicsFragment extends BoardViewPostFragment {

    private static final String SEARCHWORDS_KEY ="keywords";
    private String mKeywords;
    public static SearchViewTopicsFragment newInstance(String keys,int boardId){
        SearchViewTopicsFragment fragment=new SearchViewTopicsFragment();
        Bundle bundle=new Bundle();
        bundle.putString(SEARCHWORDS_KEY,keys);
        bundle.putInt(BOARD_KEY,boardId);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        mKeywords=bundle.getString(SEARCHWORDS_KEY);
    }

    @Override
    protected void initUI() {
        floatingActionButton.hide();
        adapter=new NormalTopicRecyclerViewAdapter(mList,this);

    }

    @Override
    protected Observable<ArrayList<TopicInfo>> genNewCall(int bid, int from, int to) {
        Observable<ArrayList<TopicInfo>> call;
        if (bid==0)
        call=iface.searchTopicGlobal(mKeywords,from,to-from+1);
        else
            call=iface.searchTopicUnderBoard(bid,mKeywords,from,to-from+1);
        return call;
    }
}
