package org.cc98.mycc98.utility;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by pipi6 on 2018/1/26.
 */

public class AppInfo {

    public static final String DEBUG_SIGN = "c3cfb855fbf9c54cb60b0773d782ff81";
    public static String name;
    public static final String ERRORNAME = "VersionName Error";

    /**
     * 获取app签名md5值
     */
    public static String getMd5SignString(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];

            return new String(Hex.encodeHex(DigestUtils.md5(sign.toByteArray())));
            /*https://stackoverflow.com/questions/9126567/method-not-found-using-digestutils-in-android
            * packages namespace error;
            * */
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.e(e, "getMd5SignString");
            return "";
        }

    }

    public static boolean isDebugMd5Sign(String sig) {
        return DEBUG_SIGN.equalsIgnoreCase(sig);
    }



    public static String getPackageVersionName(Context context) {

        PackageManager pm = context.getPackageManager();

        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            //getPackageName()是你当前类的包名，0代表是获取版本信息
            name = pi.versionName;
            //int code = pi.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            //Logg.e(TAG,ERRORNAME,e);
            name = ERRORNAME;
        }
        return name;

    }
}
