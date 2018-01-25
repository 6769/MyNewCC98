package org.cc98.mycc98.utility;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Created by pipi6 on 2018/1/26.
 */

public class LogUtil {
    private static final String TAG="LogUtil";
    public static void i(String msg){
        Logger.t(TAG).i(msg);
    }
    public static void d(String msg){
        Logger.t(TAG).d(msg);
    }
    public static void w(String msg){
        Logger.t(TAG).w(msg);
    }
    public static void e(Throwable e, String msg){
        Logger.t(TAG).e(e,msg);
    }


    public static void initLogger(){
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread i or not. Default true
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
