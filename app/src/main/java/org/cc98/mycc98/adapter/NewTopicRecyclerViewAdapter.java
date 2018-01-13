package org.cc98.mycc98.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.cc98.mycc98.R;
import org.cc98.mycc98.config.ForumConfig;
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        TopicInfo topicInfo=mValues.get(position);
        holder.newtopicTitle.setText(topicInfo.getTitle());
        holder.newtopicUsername.setText(topicInfo.getAuthorName());
        String bname=ForumConfig.getBoardNameViaId(topicInfo.getBoardId());
        if(bname==null){
            bname=topicInfo.getBoardId()+"";
        }
        holder.newtopicBoardname.setText(bname);
        holder.newtopicCreatetime.setText(topicInfo.getTime().substring(0,16));

        holder.newtopicBoardname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=mListener){
                    mListener.onListFragmentInteraction(position,1);
                }
            }
        });

        holder.container_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=mListener){
                    mListener.onListFragmentInteraction(position,0);
                }
            }
        });

        holder.newtopicUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=mListener){
                    mListener.onListFragmentInteraction(position,2);
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

        @BindView(R.id.newtopic_container_title)
        View container_title;

        View mview;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mview=view;
        }
    }


}
