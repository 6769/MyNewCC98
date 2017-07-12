package org.cc98.mycc98.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;

import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.AppCompatPreferenceActivity;

public class SettingActivity extends AppCompatPreferenceActivity {
    public static final String TAG="SettingsActivity";
    public static final String ERROR="Error";


    public static void startActivity(Context context){
        context.startActivity(
                new Intent(context,SettingActivity.class)
        );
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        //use activity is much easier for simple usages than fragments;
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            //default indicator is a return mark;
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //WARNING:android.R.id   =.=
                finish();
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}
