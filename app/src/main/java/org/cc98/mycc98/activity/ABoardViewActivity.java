package org.cc98.mycc98.activity;

import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseSwipeBackActivity;
import org.cc98.mycc98.fragment.HotPostFragment;
import org.cc98.mycc98.fragment.PostFragment;

public class ABoardViewActivity extends BaseSwipeBackActivity {
    //lsit of boards's subposts


    public static void startActivity(Context context){
        Intent intent = new Intent(context, ABoardViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_board_view);

        Fragment postFragment=new HotPostFragment();
        FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_board_view,postFragment);
        transaction.commit();
    }
    //maybe never used;
}
