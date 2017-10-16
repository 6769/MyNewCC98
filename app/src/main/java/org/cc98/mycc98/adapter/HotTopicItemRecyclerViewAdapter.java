package org.cc98.mycc98.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.cc98.mycc98.R;
import org.cc98.mycc98.fragment.PostFragment.OnPostFragmentInteractionListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import win.pipi.api.data.HotTopicInfo;
import win.pipi.api.data.TopicInfoInterface;


public class HotTopicItemRecyclerViewAdapter
        extends RecyclerView.Adapter<HotTopicItemRecyclerViewAdapter.ViewHolder>
        implements View.OnClickListener {



    private final List<? extends HotTopicInfo> mValues;
    private final OnPostFragmentInteractionListener mListener;

    public HotTopicItemRecyclerViewAdapter(List<? extends HotTopicInfo> items, OnPostFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hottopic_recyclerview_item_atopic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        HotTopicInfo aTopicInfo = mValues.get(position);
        //holder.mItem = aTopicInfo;
        holder.hottopicRankid.setText(String.valueOf(position+1));

        String username=aTopicInfo.getAuthorName();
        holder.hottopicUsername.setText(username);
        holder.hottopicCreatetime.setText(aTopicInfo.getCreateTime());
        holder.hottopicTitle.setText(aTopicInfo.getTitle());
        holder.hottopicBoardname.setText(aTopicInfo.getBoardName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.hottopic_rankid)
        TextView hottopicRankid;
        @BindView(R.id.hottopic_title)
        TextView hottopicTitle;
        @BindView(R.id.hottopic_boardname)
        TextView hottopicBoardname;
        @BindView(R.id.hottopic_username)
        TextView hottopicUsername;
        @BindView(R.id.hottopic_createtime)
        TextView hottopicCreatetime;


        View mView;
        //TopicInfoInterface mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);

        }

        @Override
        public String toString() {
            return getClass().getName();
        }
    }


}
