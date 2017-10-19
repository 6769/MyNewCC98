package org.cc98.mycc98.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseSwipeBackActivity;
import org.cc98.mycc98.fragment.BoardViewPostFragment;
import org.cc98.mycc98.fragment.HotTopicsFragment;

public class ABoardViewActivity extends BaseSwipeBackActivity {
    //lsit of boards's subposts
    public static final String BOARD_KEY="Board";
    private int boardId;


    public static void startActivity(Context context){
        Intent intent = new Intent(context, ABoardViewActivity.class);
        context.startActivity(intent);
    }
    public static void startActivity(Context context,int boardId){
        Intent intent = new Intent(context, ABoardViewActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt(BOARD_KEY,boardId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        boardId=bundle.getInt(BOARD_KEY,184);

        setContentView(R.layout.activity_board_view);

        Fragment postFragment= BoardViewPostFragment.newInstance(boardId);
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction =  manager.beginTransaction();
        transaction.add(R.id.activity_board_view,postFragment);
        transaction.commit();
    }
    //maybe never used;
}
