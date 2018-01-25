package org.cc98.mycc98.config;

import android.content.Context;


import org.cc98.mycc98.utility.AppInfo;
import org.cc98.mycc98.utility.LogUtil;

/**
 * Created by pipi6 on 2018/1/21.
 */

public class ApplicationConfig {
    private static ApplicationConfig instance;


    public static Boolean getIsDebugMode() {
        return isDebugMode;
    }

    public static void setIsDebugMode(boolean isDebugMode) {
        ApplicationConfig.isDebugMode = isDebugMode;
    }

    private static Boolean isDebugMode;

    public static Boolean getStatEnable() {
        return statEnable;
    }

    private static Boolean statEnable;



    public static void init(Context context){
        String md5sig=AppInfo.getMd5SignString(context);
        LogUtil.i(md5sig);
        isDebugMode=AppInfo.isDebugMd5Sign(md5sig);

        //isDebugMode=true;
        statEnable=!isDebugMode;
    }
}
