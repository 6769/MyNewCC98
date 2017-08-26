package org.cc98.mycc98.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.ABoardViewActivity;

import java.util.Random;

/**
 * Created by pipi6 on 2017/8/7.
 */

public class BoardItemAdapter
        extends RecyclerView.Adapter<BoardItemAdapter.ViewHolder> {


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerlist_item_aboard, parent, false);
        final ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        holder.mUserName.setText("Pip5");
        holder.mBoardName.setText("似水流年·暑假&军训2017");
        holder.mTimetoRun.setText(String.valueOf(new Random().nextInt()));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=holder.getAdapterPosition();
                Toast.makeText(v.getContext(),String.valueOf(pos),Toast.LENGTH_SHORT).show();
                ABoardViewActivity.startActivity(v.getContext());
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mUserName, mTimetoRun, mBoardName;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.recycle_item_aboard_boardsid);
            mUserName = (TextView) view.findViewById(R.id.recyclerlist_item_username);
            mBoardName = (TextView) view.findViewById(R.id.recycle_item_aboard_boardsname);
            mTimetoRun = (TextView) view.findViewById(R.id.recyclerlist_item_timetorun);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mBoardName.getText() + "'";
        }
    }
}
