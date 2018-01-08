package org.cc98.mycc98.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import win.pipi.api.data.GroupBoardInfo;
import win.pipi.api.data.RootBoardInfo;
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
    protected ImageView mImageview;

    protected CC98APIInterface iface;
    private Observer<List<GroupBoardInfo>> topBoardObserver;

    public BoardMapFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iface = MainApplication.getApiInterface();
        topBoardObserver = new Observer<List<GroupBoardInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mswipeRefreshLayout.setRefreshing(false);
                mkToast(e.toString());
            }

            @Override
            public void onNext(List<GroupBoardInfo> boardInfos) {

                mExpandlist.removeAllViews();
                for (GroupBoardInfo i : boardInfos) {
                    configureTopItem(i);
                }

                mswipeRefreshLayout.setRefreshing(false);

            }
        };


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boardexpanse_list, container, false);
        mswipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_swipeLayout);
        mExpandlist = (ExpandingList) view.findViewById(R.id.fragment_boardview_expanding_list_main);
        //mImageview=view.findViewById(R.id.fragment_boardview_expanding_list_cover);

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

        Observable<ArrayList<GroupBoardInfo>> call = iface.getBoardAll();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topBoardObserver);

    }


    protected void configureTopItem(GroupBoardInfo boardInfo) {
        //Let's create an item with R.layout.expanding_layout
        ExpandingItem expandingItem = mExpandlist.createNewItem(R.layout.expandinglist_topitem_layout);

        //If item creation is successful, let's configure it
        if (expandingItem != null) {
            expandingItem.setIndicatorColorRes(R.color.card_grey);
            expandingItem.setIndicatorIconRes(R.drawable.ic_menu_gallery);
            //It is possible to get any view inside the inflated layout. Let's set the text in the item
            TextView topboardName=(TextView) expandingItem.findViewById(R.id.expandinglist_item_top_boardname);
            topboardName.setText(boardInfo.getName());

            //We can create items in batch.

            List<GroupBoardInfo.BoardsBean> subboards = boardInfo.getBoards();

            int count=subboards.size();
            expandingItem.createSubItems(count);
            for (int i = 0; i < count; i++) {
                configureSubItem(expandingItem.getSubItemView(i), subboards.get(i));
            }


        }
    }

    private void configureSubItem(View view, final GroupBoardInfo.BoardsBean boardInfo) {
        TextView subboardName=(TextView) view.findViewById(R.id.sub_title);
        subboardName.setText(boardInfo.getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ABoardViewActivity.startActivity(getContext(),boardInfo.getId(),boardInfo.getName());
            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}
