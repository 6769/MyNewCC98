package org.cc98.mycc98.utility;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pipi6 on 2018/1/20.
 */

public class StringProcess {

    private static final String URL_REG="(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
    public static boolean isValidUrl(String s){
        String _s=s.toLowerCase();
        Pattern pattern=Pattern.compile(URL_REG);
        Matcher matcher=pattern.matcher(_s);
        return matcher.matches();
    }
}
