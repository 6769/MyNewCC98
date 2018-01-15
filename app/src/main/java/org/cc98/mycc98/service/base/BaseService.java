package org.cc98.mycc98.service.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.orhanobut.logger.Logger;

/**
 * Created by pipi6 on 2018/1/15.
 */

public class BaseService extends Service {

    private final String TAG = "BaseService";
    //必须要实现的方法
    @Override
    public IBinder onBind(Intent intent) {
        Logger.t(getClass().getSimpleName()).i("onBind");

        return null;
    }

    //Service被创建时调用
    @Override
    public void onCreate() {
        Logger.t(getClass().getSimpleName()).i("onCreate");
        super.onCreate();
    }

    //Service被启动时调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.t(getClass().getSimpleName()).i("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    //Service被关闭之前回调
    @Override
    public void onDestroy() {
        Logger.t(getClass().getSimpleName()).i("onDestroy");
        super.onDestroy();
    }
}
