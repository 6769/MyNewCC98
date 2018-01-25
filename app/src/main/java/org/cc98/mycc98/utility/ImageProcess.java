package org.cc98.mycc98.utility;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Environment;

import org.cc98.mycc98.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import win.pipi.swiftemotionboard.model.EmotionGroup;
import win.pipi.swiftemotionboard.model.OneEmotion;

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

    public static List<EmotionGroup> loadEmotionsFromAssets(Context context,String assetsFolder){
        List<EmotionGroup> emotionGroups=new ArrayList<>();
        AssetManager manager =context.getAssets();
        try{
            for(String agroupfolder:manager.list(assetsFolder)){
                File afolder=new File(assetsFolder,agroupfolder);
                List<OneEmotion> oneEmotionList=new ArrayList<>();
                for(String idimg:manager.list(afolder.getPath())){
                    File imgf=new File(afolder,idimg);
                    String textPrep=String.format("[%s%s]",agroupfolder.trim(),imgf.getName());
                    OneEmotion oe=new OneEmotion(imgf.toString(),textPrep);
                    oneEmotionList.add(oe);
                }
                emotionGroups.add(new EmotionGroup(agroupfolder,oneEmotionList));

            }
            return emotionGroups;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }



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
