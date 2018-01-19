package org.cc98.mycc98.utility;

import android.content.Context;
import android.content.Intent;

/**
 * Created by pipi6 on 2018/1/19.
 */

public class ShareContent {

    public static void shareText(Context context, String text, String shareTitle) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");
        context.startActivity(Intent.createChooser(intent, shareTitle));
    }
}
