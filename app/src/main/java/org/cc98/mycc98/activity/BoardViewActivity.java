package org.cc98.mycc98.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseSwipeBackActivity;

public class BoardViewActivity extends BaseSwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_view);
    }
}
