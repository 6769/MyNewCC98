package org.cc98.mycc98;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

import org.cc98.mycc98.activity.LoginActivity;
import org.cc98.mycc98.config.ApplicationConfig;

import win.pipi.api.authorization.LoginCC98;
import win.pipi.api.network.CC98APIInterface;
import win.pipi.api.network.CC98APIManager;

import org.cc98.mycc98.utility.LogUtil;


/**
 * Created by pip on 2017/7/11.
 */

public class MainApplication extends Application {
    private static Context context;
    private static CC98APIInterface apiInterface;

    public static LoginCC98 getLoginCC98instance() {
        return loginCC98instance;
    }

    private static LoginCC98 loginCC98instance;

    public static CC98APIManager getCc98APIManager() {
        return cc98APIManager;
    }

    private static CC98APIManager cc98APIManager;

    public static Context getContext(){
        return context;
    }
    public static CC98APIInterface getApiInterface(){
        return apiInterface;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);

        LogUtil.initLogger();
        ApplicationConfig.init(this);
        context=getApplicationContext();

        cc98APIManager=new CC98APIManager();
        apiInterface= cc98APIManager.createApiClient();
        loginCC98instance=cc98APIManager.getLoginCC98();
        LoginActivity.loadUserTokenPersist(getContext(),loginCC98instance);



    }



}
