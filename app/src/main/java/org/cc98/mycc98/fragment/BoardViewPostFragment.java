package org.cc98.mycc98.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.ABoardViewActivity;
import org.cc98.mycc98.activity.EditActivity;
import org.cc98.mycc98.activity.PostReadActivity;
import org.cc98.mycc98.activity.UserProfileActivity;
import org.cc98.mycc98.adapter.NormalTopicRecyclerViewAdapter;
import org.cc98.mycc98.config.ForumConfig;
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

public class BoardViewPostFragment extends BasePullPushSwipeFragment<TopicInfo>
        implements View.OnClickListener {
    private static final String TAG= "BoardViewPostFragment" ;
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
    protected void initUI() {
        adapter = new NormalTopicRecyclerViewAdapter(mList, this);
        floatingActionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: newTopic to"+boardId);
        EditActivity.startActivity(this.getContext(), boardId);
    }

    @Override
    protected Observable<ArrayList<TopicInfo>> genNewCall(int bid, int from, int to) {
        return iface.getTopicBoard(bid, from, to - from);
    }


    @Override
    public void onRefresh() {
        int currentlen = 0;
        updatePages(currentlen , currentlen + 20, true);
    }

    @Override
    public void onLoadMore(boolean isSilence) {
        int currentlen = mList.size();
        updatePages(currentlen , currentlen + 20, false);

    }


    @Override
    public void onListFragmentInteraction(int i, int type) {
        switch (type){
            case 0:
                int topicid=mList.get(i).getId();
                PostReadActivity.startActivity(getContext(),topicid);
                // mkToast(mList.get(i).getTitle());
                break;
            case 1:
                int bid=mList.get(i).getBoardId();

                //mkToast(+"");
                ABoardViewActivity.startActivity(this.getContext(),bid, ForumConfig.getBoardNameViaId(bid));
                break;
            case 2:
                int userid=mList.get(i).getUserId();
                String username=mList.get(i).getUserName();
                if (!username.equals(getString(R.string.username_nobody))){
                    UserProfileActivity.startActivity(this.getContext(), userid);
                }
                break;
        }

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
                                i.setUserName(getString(R.string.username_nobody));
                            mList.add(i);
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(listSubscriber);

    }
}
