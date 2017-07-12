package org.cc98.mycc98.activity.base;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.orhanobut.logger.Logger;

import org.cc98.mycc98.ActivityCollector;

/**
 * Created by pip on 2017/7/12.
 */

public class BaseSwipeBackActivity extends SwipeBackActivity {
    public static String TAG="BaseSwipeBackActivity";

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

        Logger.t(this.getClass().getSimpleName()).d("onCreate");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.t(this.getClass().getSimpleName()).d("onStart");
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
        ActivityCollector.removeActivity(this);
        Logger.t(this.getClass().getSimpleName()).d("onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.t(this.getClass().getSimpleName()).d("onRestart");
    }
}
