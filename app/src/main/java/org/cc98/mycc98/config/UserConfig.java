package org.cc98.mycc98.config;

import win.pipi.api.data.UserInfo;

/**
 * Created by pipi6 on 2018/1/14.
 */

public class UserConfig {
    public static UserInfo getUserInfo() {
        return userInfo;
    }

    public static void setUserInfo(UserInfo userI) {
        userInfo = userI;
    }

    private static UserInfo userInfo;

}
