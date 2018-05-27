package org.cc98.mycc98.utility;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;

import org.cc98.mycc98.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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

public class ImageUtil {
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


    private static List<EmotionGroup> emotionGroupListCache;

    public static List<EmotionGroup> loadEmotionsFromAssets(Context context, String assetsFolder) {
        if (emotionGroupListCache != null) {
            return emotionGroupListCache;
        }

        //TODO: use more reasonable data structure to contain emotions;
        List<EmotionGroup> emotionGroups = new ArrayList<>();
        AssetManager manager = context.getAssets();
        try {
            for (String agroupfolder : manager.list(assetsFolder)) {
                if (agroupfolder.length() > 2) {//very simple and stupid way to exclude the wired emotions
                    continue;
                }
                File afolder = new File(assetsFolder, agroupfolder);
                List<OneEmotion> oneEmotionList = new ArrayList<>();
                for (String idimg : manager.list(afolder.getPath())) {
                    File img = new File(afolder.getPath(), idimg);
                    String fullpath = StringProcess.ASSETS_FOLDER + img.toString();
                    String textPrep = getEmotionLabel(agroupfolder, idimg);
                    OneEmotion oe = new OneEmotion(fullpath, textPrep);
                    oneEmotionList.add(oe);
                }
                emotionGroups.add(new EmotionGroup(agroupfolder, oneEmotionList));

            }

        } catch (IOException e) {
            e.printStackTrace();
            emotionGroups.clear();
        }
        emotionGroupListCache = emotionGroups;
        return emotionGroups;


    }


    public static String getEmotionLabel(String folder, String filename) {
        String namePart = StringProcess.getNamePart(filename);
        String formation = StringProcess.EMPTY;
        switch (folder) {
            case "ac":
                formation = "[ac%s]";
                break;
            case "tb":
            case "em":
                formation = "[%s]";
                break;
            default:
                break;
        }
        String textPrep = String.format(formation, namePart);
        return textPrep;
    }


    public static File getDCIMNewImageFile(Context context) {
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        String[] appnames=context.getPackageName().split(".");
        String foldername=appnames[appnames.length-1];
        File folder=new File(directory,foldername);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(context.getString(R.string.editor_photo_name_template), Locale.CHINA);
        return new File(folder, dateFormat.format(date));
    }

    @Deprecated
    public static ByteArrayOutputStream compressPhotoStorageSize(Bitmap orignal, int maxSize) {
        //maxsize: byte

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int step = 5;
        int options = 95;
        do {
            baos.reset();
            orignal.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= step;
        } while (baos.toByteArray().length > maxSize);
        return baos;

    }

    /**
     * 保存图片
     *
     * @param context
     * @param bitmap
     * @param fileToSave
     * @return
     */
    public static boolean saveBitmapToFile(Context context, @NonNull Bitmap bitmap, File fileToSave) {
        return saveBitmapToFile(context,bitmap,fileToSave,90);
    }


    public static boolean saveBitmapToFile(Context context, @NonNull Bitmap bitmap, File fileToSave, int quality){
        Bitmap.CompressFormat format= Bitmap.CompressFormat.PNG;
        if (fileToSave.exists() && !fileToSave.delete())
            return false;
        try {
            FileOutputStream stream = new FileOutputStream(fileToSave);
            bitmap.compress(format, quality, stream);
            stream.flush();
            stream.close();
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fileToSave)));
            return true;

        } catch (Exception e) {
            LogUtil.e(e,"saveBitmapToFile failed");
            return false;
        }
    }

    public static Bitmap appendBitmapToTail(Bitmap major,Bitmap tail){
        int h1=major.getHeight();
        int w1=major.getWidth();
        int h2=tail.getHeight();
        int w2=tail.getWidth();
        Bitmap bitmap=tail;
        if(w2>w1){
            bitmap=scaleBitmap(tail,w1*1.0f/w2);
            w2=w1;
        }

        return ScreenCapture.mergeBitmap(h1+h2,w1,major,0,0,bitmap,h1,w1-w2);
    }

    /**
     * 按比例缩放图片
     *
     * @param origin 原图
     * @param ratio  比例
     * @return 新的bitmap
     */
    public static Bitmap scaleBitmap(Bitmap origin, float ratio) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }
}
