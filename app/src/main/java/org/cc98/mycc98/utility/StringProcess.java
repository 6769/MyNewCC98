package org.cc98.mycc98.utility;

import org.apache.commons.io.FilenameUtils;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pipi6 on 2018/1/20.
 */

public class StringProcess {
    public static final String ASSETS_FOLDER="file:///android_asset/";
    public static final String EMPTY="";
    public static final String FILESUFIX_REG="[.][^.]+$";

    private static final String URL_REG="(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
    public static boolean isValidUrl(String s){
        String _s=s.toLowerCase();
        Pattern pattern=Pattern.compile(URL_REG);
        Matcher matcher=pattern.matcher(_s);
        return matcher.matches();
    }

    public static String getNamePart(final String file){
        return FilenameUtils.getBaseName(file);
    }
}
