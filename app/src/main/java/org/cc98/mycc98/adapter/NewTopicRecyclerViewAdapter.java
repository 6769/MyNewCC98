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
 * Created by pipi6 on 2017/10/16.
 */

public class NewTopicRecyclerViewAdapter extends RecyclerView.Adapter<NewTopicRecyclerViewAdapter.ViewHolder>
implements View.OnClickListener{

    private final List<? extends TopicInfo> mValues;
    private final BaseSwipeRefreshFragment.OnPostFragmentInteractionListener mListener;


    public NewTopicRecyclerViewAdapter(List<? extends TopicInfo> m,
                                       BaseSwipeRefreshFragment.OnPostFragmentInteractionListener ml) {
        mValues = m;
        mListener = ml;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.newtopic_recyclerview_item_atopic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        TopicInfo topicInfo=mValues.get(position);
        holder.newtopicTitle.setText(topicInfo.getTitle());
        holder.newtopicUsername.setText(topicInfo.getAuthorName());
        holder.newtopicBoardname.setText(topicInfo.getBoardId()+"");
        holder.newtopicCreatetime.setText(topicInfo.getTime());

        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(pos, 0);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.newtopic_title)
        TextView newtopicTitle;
        @BindView(R.id.newtopic_boardname)
        TextView newtopicBoardname;
        @BindView(R.id.newtopic_username)
        TextView newtopicUsername;
        @BindView(R.id.newtopic_createtime)
        TextView newtopicCreatetime;


        View mview;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mview=view;
        }
    }


}
