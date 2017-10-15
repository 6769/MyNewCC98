package org.cc98.mycc98.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.cc98.mycc98.MainApplication;
import org.cc98.mycc98.R;
import org.cc98.mycc98.adapter.PostItemRecyclerViewAdapter;
import org.cc98.mycc98.fragment.base.BaseFragment;
import org.cc98.mycc98.fragment.dummy.DummyContent;
import org.cc98.mycc98.fragment.dummy.DummyContent.DummyItem;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import win.pipi.api.data.HotTopicInfo;
import win.pipi.api.network.CC98APIInterface;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PostFragment extends BaseFragment {


    private static final String BOARDID = "boardid";
    private static final String POSTYTYPEKEY="type";

    private int mBoardId;
    private PostType mpostType;
    private OnListFragmentInteractionListener mListener;
    private SpecificCallFactory mCall;

    SwipeRefreshLayout mswipeRefreshLayout;
    RecyclerView mrecyclerView;
    CC98APIInterface iface;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PostFragment() {
    }



    @SuppressWarnings("unused")
    public static PostFragment newInstance(PostType type,int boardId) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();

        args.putSerializable(POSTYTYPEKEY,type);
        args.putInt(BOARDID, boardId);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args= getArguments();

        if (args != null) {
            mBoardId = args.getInt(BOARDID);
            mpostType=(PostType) args.getSerializable(POSTYTYPEKEY);
            iface=MainApplication.getApiInterface();
            switch (mpostType){
                case HOT:
                    mCall=new SpecificCallFactory() {
                        @Override
                        public Observable<?> getCall(int f,int t) {
                            return iface.getTopicHot();
                        }
                    };

                    break;
                case BOARD:
                    mCall=new SpecificCallFactory() {
                        @Override
                        public Observable<?> getCall(int f,int t) {
                            return iface.getTopicBoard(mBoardId,f,t);
                        }
                    };
                    break;
                case NEW:
                    mCall=new SpecificCallFactory() {
                        @Override
                        public Observable<?> getCall(int from, int to) {
                            return iface.getTopicNew(from,to);
                        }
                    };
                    break;
                default:
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        mswipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_swipeLayout);
        mrecyclerView = (RecyclerView) view.findViewById(R.id.fragment_recyclerview);

        mswipeRefreshLayout.setColorSchemeColors(Color.BLUE,
                Color.GREEN,
                //Color.YELLOW,
                Color.RED);

        mswipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Toast.makeText(getContext(), "refresh", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(3000);

                            Observable<ArrayList<HotTopicInfo>> call= MainApplication.getApiInterface().getTopicHot();

                            call.subscribe(new Subscriber<ArrayList<HotTopicInfo>>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(final ArrayList<HotTopicInfo> hotTopicInfos) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mkToastL(mpostType.toString());
                                            mswipeRefreshLayout.setRefreshing(false);
                                        }
                                    });

                                }
                            });



                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }).start();

            }
        });

        PostItemRecyclerViewAdapter adapter = new PostItemRecyclerViewAdapter(
                DummyContent.ITEMS, mListener);
        mrecyclerView.setAdapter(adapter);


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }

    public enum PostType{
        HOT,NEW,BOARD
    }
}
