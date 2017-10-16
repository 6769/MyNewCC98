package org.cc98.mycc98.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.cc98.mycc98.adapter.NewTopicRecyclerViewAdapter;

import java.util.ArrayList;

import rx.Observable;
import win.pipi.api.data.TopicInfo;

/**
 * Created by pipi6 on 2017/10/17.
 */

public class BoardViewPostFragment extends NewPostFragment {
    public BoardViewPostFragment() {
    }


    public static BoardViewPostFragment newInstance(int bid){
        BoardViewPostFragment fragment=new BoardViewPostFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(BOARD_KEY,bid);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle=getArguments();
        boardId=bundle.getInt(BOARD_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initAdapter() {
        //adapter=new NewTopicRecyclerViewAdapter(topicInfoList,this);
    }

    @Override
    protected Observable<ArrayList<TopicInfo>> genNewCall(int bid, int from, int to) {
        return iface.getTopicBoard(bid, from, to);
    }



    @Override
    public void onLoadMore(boolean isSilence) {

    }



    @Override
    public void onListFragmentInteraction(int i) {

    }
}
