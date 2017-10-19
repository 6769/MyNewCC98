package org.cc98.mycc98.fragment;

import android.os.Bundle;

import org.cc98.mycc98.R;
import org.cc98.mycc98.adapter.NormalTopicRecyclerViewAdapter;
import org.cc98.mycc98.fragment.base.BasePullPushSwipeFragment;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import win.pipi.api.data.TopicInfo;

/**
 * Created by pipi6 on 2017/10/17.
 */

public class BoardViewPostFragment extends BasePullPushSwipeFragment<TopicInfo> {
    public BoardViewPostFragment() {
    }


    public static BoardViewPostFragment newInstance(int bid) {
        BoardViewPostFragment fragment = new BoardViewPostFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BOARD_KEY, bid);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        boardId = bundle.getInt(BOARD_KEY);
    }


    @Override
    protected void initAdapter() {
        adapter = new NormalTopicRecyclerViewAdapter(mList, this);
    }

    @Override
    protected Observable<ArrayList<TopicInfo>> genNewCall(int bid, int from, int to) {
        return iface.getTopicBoard(bid, from, to);
    }

    @Override
    public void onRefresh() {
        int currentlen = 0;
        updatePages(currentlen + 1, currentlen + 20, true);
    }

    @Override
    public void onLoadMore(boolean isSilence) {
        int currentlen = mList.size();
        updatePages(currentlen + 1, currentlen + 20, false);

    }

    @Override
    public void onRelease(float direction) {

    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY) {

    }

    @Override
    public void onListFragmentInteraction(int i) {

    }

    protected void updatePages(int from, int to, final boolean clearold) {
        Observable<ArrayList<TopicInfo>> call = genNewCall(boardId, from, to);
        call.subscribeOn(Schedulers.io())

                .observeOn(Schedulers.io())
                .doOnNext(new Action1<List<TopicInfo>>() {
                    @Override
                    public void call(List<TopicInfo> normalTopicInfos) {
                        if (clearold) {
                            mList.clear();
                        }

                        for (TopicInfo i : normalTopicInfos) {
                            String name = i.getAuthorName();
                            if (name == null || name.isEmpty())
                                i.setAuthorName(getString(R.string.username_nobody));
                            mList.add(i);
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(listSubscriber);

    }
}
