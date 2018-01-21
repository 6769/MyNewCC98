package org.cc98.mycc98.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import org.cc98.mycc98.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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


    public static File getDCIMNewImageFile(Context context){
        File PHOTO_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(context.getString(R.string.editor_photo_name_template), Locale.CHINA);
        return new File(PHOTO_DIR, dateFormat.format(date));
    }

    @Deprecated
    public static ByteArrayOutputStream  compressPhotoStorageSize(Bitmap orignal, int maxSize){
        //maxsize: byte

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int step=5;
        int options = 95;
        do {
            baos.reset();
            orignal.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options-=step;
        }while (baos.toByteArray().length  > maxSize);
        return baos;

    }
}
