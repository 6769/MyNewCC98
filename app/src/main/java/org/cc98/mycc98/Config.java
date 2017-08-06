package org.cc98.mycc98;

/**
 * Created by pipi6 on 2017/8/7.
 */

public class Config {

    private static String userToken = "";

    public static void setUserToken(String m) {
        userToken = m;
    }

    public static String getUserToken() {
        return userToken;
    }
}
