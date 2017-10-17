package org.cc98.mycc98.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.cc98.mycc98.R;
import org.cc98.mycc98.fragment.PostFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import win.pipi.api.data.TopicInfo;

/**
 * Created by pipi6 on 2017/10/17.
 */

public class NormalTopicRecyclerViewAdapter extends RecyclerView.Adapter<NormalTopicRecyclerViewAdapter.ViewHolder> {


    private final List<? extends TopicInfo> mValues;
    private final PostFragment.OnPostFragmentInteractionListener mListener;


    public NormalTopicRecyclerViewAdapter(List<? extends TopicInfo> mValues,
                                          PostFragment.OnPostFragmentInteractionListener mListener) {
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        TopicInfo topicInfo = mValues.get(position);

        holder.topicTitle.setText(topicInfo.getTitle());
        holder.topicCreatetime.setText(topicInfo.getCreateTime());
        holder.topicUsername.setText(topicInfo.getAuthorName());
        //holder.topicBoardname.setText(topicInfo.getBoardId()+"");
        holder.topicHitcnt.setText(topicInfo.getReplyCount()+"/"+topicInfo.getHitCount());


        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(pos);
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

        View mview;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mview=view;
        }
    }
}
