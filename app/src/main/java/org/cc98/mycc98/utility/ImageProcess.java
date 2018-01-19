package org.cc98.mycc98.utility;

/**
 * Created by pipi6 on 2018/1/19.
 */

public class ImageProcess {
    private final static String[] photoPrefix = {"png", "jpg", "bmp", "jpeg", "gif", "tiff"};

    public static boolean isPhotoUrl(String url) {
        String sturl = url.toLowerCase();
        for (String afix : photoPrefix) {
            String withdot = "." + afix;
            if (sturl.endsWith(withdot)) {
                return true;
            }
        }
        return false;

    }
}
