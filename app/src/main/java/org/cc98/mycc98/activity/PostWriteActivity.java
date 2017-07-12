package org.cc98.mycc98.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseActivity;
import org.cc98.mycc98.activity.base.BaseSwipeBackActivity;

public class PostWriteActivity extends BaseSwipeBackActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, PostWriteActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_write);


    }

    @Override
    protected void onResume() {
        super.onResume();
        //load config here

    }
}
