package org.cc98.mycc98.utility;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by pipi6 on 2018/1/26.
 */

public class AppInfo {

    public static final String TAG=AppInfo.class.getSimpleName();
    private static final String LICENSE_PATH ="hash/license";
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

    public static boolean isDebugMd5Sign(Context context,String sig) {

        try {
            InputStream stream=context.getAssets().open(LICENSE_PATH);//release key hash
            BufferedReader reader=new BufferedReader(new InputStreamReader(stream));
            String savedMd5=reader.readLine().trim();
            reader.close();
            stream.close();
            Log.d(TAG,savedMd5+" "+sig);
            return !savedMd5.equalsIgnoreCase(sig);



        }catch (Exception e){
            LogUtil.e(e,"assets");
            return true;
        }
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
