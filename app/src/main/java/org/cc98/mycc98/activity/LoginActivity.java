package org.cc98.mycc98.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseActivity;
import org.cc98.mycc98.activity.base.BaseSwipeBackActivity;

/**
 * Created by pip on 2017/7/12.
 */

public class LoginActivity extends BaseSwipeBackActivity {



    public static void startActivity(Context context){
        Intent intent= new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
