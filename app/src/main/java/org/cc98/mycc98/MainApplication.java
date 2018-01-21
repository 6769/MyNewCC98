package org.cc98.mycc98;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;

import org.cc98.mycc98.activity.LoginActivity;
import org.cc98.mycc98.config.ApplicationConfig;

import win.pipi.api.authorization.LoginCC98;
import win.pipi.api.network.CC98APIInterface;
import win.pipi.api.network.CC98APIManager;


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


        ApplicationConfig.init(this);
        context=getApplicationContext();

        cc98APIManager=new CC98APIManager();
        apiInterface= cc98APIManager.createApiClient();
        loginCC98instance=cc98APIManager.getLoginCC98();
        LoginActivity.loadUserTokenPersist(getContext(),loginCC98instance);




        initLogger();
    }


    private void initLogger(){
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                //.methodCount(3)         // (Optional) How many method scroll_bar_bg_line to show. Default 2
                //.methodOffset(5)        // (Optional) Hides internal method calls up to offset. Default 5
                //.logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("My98")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        FormatStrategy diskformatStrategy = CsvFormatStrategy.newBuilder()
                .tag("MyCC98")
                .build();

        Logger.addLogAdapter(new DiskLogAdapter(diskformatStrategy));
    }
}
