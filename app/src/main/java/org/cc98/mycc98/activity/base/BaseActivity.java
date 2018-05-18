package org.cc98.mycc98.activity.base;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.orhanobut.logger.Logger;

import org.cc98.mycc98.config.ApplicationConfig;

/**
 * Created by pip on 2017/7/11.
 */

public class BaseActivity extends AppCompatActivity {
    public static String TAG="BaseActivity";

    public static void startActivity(Context context){
        throw new UnsupportedClassVersionError("Not Implemented Static Method");
    }

    protected void mkToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    protected void mkToastL(String msg){
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

        if(ApplicationConfig.getStatEnable()){
            StatService.onResume(this);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.t(this.getClass().getSimpleName()).d("onPause");
        if(ApplicationConfig.getStatEnable()){
            StatService.onPause(this);
        }

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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(this.getClass().getSimpleName(), "onConfigurationChanged: "+newConfig.toString());
    }

    protected void error(Throwable e, String msg){
        Logger.t(this.getClass().getSimpleName()).e(e,msg);
    }

    protected void info(String msg){
        Logger.t(this.getClass().getSimpleName()).i(msg);
    }
}
