package org.cc98.mycc98.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.ABoardViewActivity;
import org.cc98.mycc98.activity.PostReadActivity;
import org.cc98.mycc98.activity.UserProfileActivity;
import org.cc98.mycc98.adapter.HotTopicItemRecyclerViewAdapter;
import org.cc98.mycc98.config.ForumConfig;
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
        adapter = new HotTopicItemRecyclerViewAdapter(mLists, this);
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
                        for (HotTopicInfo i : hotTopicInfos) {
                            if (i.getReplyCount() == 0)
                                continue;
                            String name = i.getAuthorName();
                            if (name == null || name.isEmpty())
                                i.setAuthorName(getString(R.string.username_nobody));

                            mLists.add(i);
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(refreshObserver);

    }


    @Override
    public void onListFragmentInteraction(int i, int type) {
        switch (type) {
            case 0://title
                int id = mLists.get(i).getId();
                PostReadActivity.startActivity(getContext(), id);
                break;
            case 1://boardname
                String bname = mLists.get(i).getBoardName();

                int bid = ForumConfig.getBoardIdViaName(bname);
                if (bid > 0)
                    ABoardViewActivity.startActivity(this.getContext(), bid, bname);
                break;
            case 2://user
                String username = mLists.get(i).getAuthorName();
                UserProfileActivity.startActivity(getContext(), 0, username);
                break;
        }

    }

}
