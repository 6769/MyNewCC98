package org.cc98.mycc98.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;

import org.cc98.mycc98.MainApplication;
import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.ABoardViewActivity;
import org.cc98.mycc98.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import win.pipi.api.data.BoardInfo;
import win.pipi.api.data.TopicInfo;
import win.pipi.api.network.CC98APIInterface;

/**
 * Created by pipi6 on 2017/10/19.
 */

public class BoardMapFragment extends BaseFragment
        implements
        SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener{

    protected List<BoardInfo> mLists = new ArrayList<>();
    protected SwipeRefreshLayout mswipeRefreshLayout;
    protected ExpandingList mExpandlist;
    protected CC98APIInterface iface;
    private Observer<List<BoardInfo>> topBoardObserver,subBoardObserver;
    private Action1<List<BoardInfo>> mRxAction1;

    public BoardMapFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iface = MainApplication.getApiInterface();
        topBoardObserver=new Observer<List<BoardInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mswipeRefreshLayout.setRefreshing(false);
                mkToast(e.toString());
            }

            @Override
            public void onNext(List<BoardInfo> boardInfos) {
                mExpandlist.removeAllViews();
                for(BoardInfo i:boardInfos){
                    configureTopItem(i);
                }
                mswipeRefreshLayout.setRefreshing(false);
            }
        };
        mRxAction1=new Action1<List<BoardInfo>>() {
            @Override
            public void call(List<BoardInfo> boardInfos) {
                mLists=boardInfos;
                Collections.sort(mLists, new Comparator<BoardInfo>() {
                    @Override
                    public int compare(BoardInfo o1, BoardInfo o2) {
                        int cnt1=o1.getTodayPostCount();
                        int cnt2=o2.getTodayPostCount();
                        if (cnt1>cnt2)     return -1;
                        else if (cnt1==cnt2) return 0;
                        else return 1;
                    }
                });
            }
        };

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boardexpanse_list, container, false);
        mswipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_swipeLayout);
        mExpandlist = (ExpandingList) view.findViewById(R.id.fragment_boardview_expanding_list_main);

        mswipeRefreshLayout.setColorSchemeColors(Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.RED);
        mswipeRefreshLayout.setOnRefreshListener(this);


        if (mLists.size() == 0) {
            onRefresh();
        }

        return view;
    }

    @Override
    public void onRefresh() {
        Observable<ArrayList<BoardInfo>> call=iface.getBoardRoot();

        call.subscribeOn(Schedulers.io())
                .doOnNext(mRxAction1)
                .flatMap(new Func1<ArrayList<BoardInfo>, Observable<ArrayList<BoardInfo>>>() {
                    @Override
                    public Observable<ArrayList<BoardInfo>> call(ArrayList<BoardInfo> boardInfos) {
                        //build new requests
                        

                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                ;//.subscribe(topBoardObserver);

    }

    protected void OnRefreshSubBoards(int topBoardId, final ExpandingItem topitem){
        Observable<ArrayList<BoardInfo>> call=iface.getBoardSubs(topBoardId);
        call.subscribeOn(Schedulers.io())
                .doOnNext(mRxAction1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<BoardInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<BoardInfo> boardInfos) {
                        int size=boardInfos.size();
                        for (int i = 0; i <size ; i++) {
                            View view= topitem.getSubItemView(i);
                            configureSubItem(view,boardInfos.get(i));
                        }
                    }
                });

    }

    protected void configureTopItem(BoardInfo boardInfo) {
        //Let's create an item with R.layout.expanding_layout
        ExpandingItem expandingItem = mExpandlist.createNewItem(R.layout.expandinglist_topitem_layout);

        //If item creation is successful, let's configure it
        if (expandingItem != null) {
            expandingItem.setIndicatorColorRes(R.color.white);
            expandingItem.setIndicatorIconRes(R.drawable.ic_menu_gallery);
            //It is possible to get any view inside the inflated layout. Let's set the text in the item
            TextView topboardName=(TextView) expandingItem.findViewById(R.id.expandinglist_item_top_boardname);
            topboardName.setText(boardInfo.getName());

            //We can create items in batch.

            expandingItem.createSubItems(boardInfo.getChildBoardCount());
            OnRefreshSubBoards(boardInfo.getRootId(),expandingItem);
        }
    }
    private void configureSubItem(View view, final BoardInfo boardInfo) {
        TextView subboardName=(TextView) view.findViewById(R.id.sub_title);
        subboardName.setText(boardInfo.getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ABoardViewActivity.startActivity(getContext(),boardInfo.getId());
            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}
