package org.cc98.mycc98.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.cc98.mycc98.R;
import org.cc98.mycc98.fragment.PostFragment.OnListFragmentInteractionListener;
import org.cc98.mycc98.fragment.dummy.DummyContent.DummyItem;

import java.util.List;
import java.util.Random;


public class PostItemRecyclerViewAdapter
        extends RecyclerView.Adapter<PostItemRecyclerViewAdapter.ViewHolder>
        implements View.OnClickListener {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public PostItemRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerlist_item_apost, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
        holder.mUserName.setText("Pip5");
        holder.mBoardName.setText("似水流年·暑假&军训2017");
        holder.mTimetoRun.setText(String.valueOf(new Random().nextInt()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=holder.getAdapterPosition();
                Toast.makeText(v.getContext(),String.valueOf(pos),Toast.LENGTH_SHORT).show();
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mUserName, mTimetoRun, mBoardName;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.posttitle);
            mUserName = (TextView) view.findViewById(R.id.recyclerlist_item_username);
            mBoardName = (TextView) view.findViewById(R.id.boardname);
            mTimetoRun = (TextView) view.findViewById(R.id.recyclerlist_item_timetorun);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
