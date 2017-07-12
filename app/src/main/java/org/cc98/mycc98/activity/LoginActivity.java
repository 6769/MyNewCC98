package org.cc98.mycc98.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseActivity;

/**
 * Created by pip on 2017/7/12.
 */

public class LoginActivity extends BaseActivity {



    public static void startActivity(Context context){
        Intent intent= new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
