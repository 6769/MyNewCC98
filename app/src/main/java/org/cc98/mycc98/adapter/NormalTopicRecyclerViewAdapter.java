package org.cc98.mycc98.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.cc98.mycc98.R;
import org.cc98.mycc98.fragment.base.BaseSwipeRefreshFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import win.pipi.api.data.TopicInfo;

/**
 * Created by pipi6 on 2017/10/17.
 */

public class NormalTopicRecyclerViewAdapter
        extends RecyclerView.Adapter<NormalTopicRecyclerViewAdapter.ViewHolder> {


    private final List<? extends TopicInfo> mValues;
    private final BaseSwipeRefreshFragment.OnPostFragmentInteractionListener mListener;


    public NormalTopicRecyclerViewAdapter(List<? extends TopicInfo> mValues,
                                          BaseSwipeRefreshFragment.OnPostFragmentInteractionListener mListener) {
        this.mValues = mValues;
        this.mListener = mListener;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.normaltopic_recyclerview_item_atopic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        TopicInfo topicInfo = mValues.get(position);

        holder.topicTitle.setText(topicInfo.getTitle());
        holder.topicCreatetime.setText(topicInfo.getTime().substring(0, 16));
        holder.topicUsername.setText(topicInfo.getAuthorName());
        //holder.topicBoardname.setText(topicInfo.getBoardId()+"");
        holder.topicHitcnt.setText(topicInfo.getReplyCount()+"/"+topicInfo.getHitCount());


        holder.topicUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=mListener)
                    mListener.onListFragmentInteraction(position,2);
            }
        });
        holder.topicContainerTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(position, 0);
                }
            }
        });

    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.topic_title)
        TextView topicTitle;
        @BindView(R.id.topic_hitcnt)
        TextView topicHitcnt;
        //@BindView(R.id.topic_boardname)
        //TextView topicBoardname;
        @BindView(R.id.topic_username)
        TextView topicUsername;
        @BindView(R.id.topic_createtime)
        TextView topicCreatetime;
        @BindView(R.id.topic_container_title)
        View topicContainerTitle;

        View mview;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mview=view;
        }
    }
}
