package org.cc98.mycc98.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseSwipeBackActivity;

public class UserProfileActivity extends BaseSwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
    }
}
