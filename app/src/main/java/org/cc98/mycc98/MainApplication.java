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


/**
 * Created by pip on 2017/7/11.
 */

public class MainApplication extends Application {
    private static Context context;

    public static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context=getApplicationContext();

        //registerActivityLifecycleCallbacks(ActivityLifecycleHelper.build());
        initLogger();
    }


    private void initLogger(){
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                //.methodCount(3)         // (Optional) How many method line to show. Default 2
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
