package org.cc98.mycc98.utility;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import org.cc98.mycc98.R;

/**
 * Created by pipi6 on 2018/1/19.
 */

public class ShareContent {

    public static final String INTENT_TEXT_TYPE="text/plain";
    public static final String INTENT_IMAGE_TYPE="image/*";


    public static void shareTextDefaultTitle(Context context,String text){
        shareText(context, text, context.getString(R.string.share_content_text_title));
    }

    public static void shareText(Context context, String text, String shareTitle) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType(INTENT_TEXT_TYPE);
        context.startActivity(Intent.createChooser(intent, shareTitle));
    }
    
    public static void sharePhoto(Context context, Uri uriImage, String shareTitle) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uriImage);
        intent.setType (INTENT_IMAGE_TYPE);
        context.startActivity(Intent.createChooser(intent, shareTitle));
    }
    public static void sharePhoto(Context context, Uri uriImage){
        sharePhoto(context,uriImage,context.getString(R.string.share_content_text_title));

    }
    
}
