package org.cc98.mycc98.activity.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.jude.swipbackhelper.SwipeBackHelper;
import com.orhanobut.logger.Logger;

/**
 * Created by pip on 2017/7/12.
 */

public class BaseSwipeBackActivity extends AppCompatActivity {
    public static final String TAG = "BaseSwipeBackActivity";

    public static void startActivity(Context context){
        throw new UnsupportedClassVersionError("Not Implemented Static Method");
    }

    protected void mkToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    protected void mkToast(String msg,boolean showMoreTime){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        SwipeBackHelper.onCreate(this);

        Logger.t(this.getClass().getSimpleName()).d("onCreate");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.t(this.getClass().getSimpleName()).d("onStart");
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.t(this.getClass().getSimpleName()).d( "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.t(this.getClass().getSimpleName()).d("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.t(this.getClass().getSimpleName()).d("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
        ActivityCollector.removeActivity(this);

        Logger.t(this.getClass().getSimpleName()).d("onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.t(this.getClass().getSimpleName()).d("onRestart");
    }


}
