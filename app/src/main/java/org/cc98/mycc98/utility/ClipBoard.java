package org.cc98.mycc98.utility;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by pipi6 on 2018/1/19.
 */

public class ClipBoard {
    public static void copyToClpBoard(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("URL", text);//label used for what?
        clipboard.setPrimaryClip(clip);
    }
}
