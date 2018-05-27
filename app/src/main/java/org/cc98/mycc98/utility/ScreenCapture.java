package org.cc98.mycc98.utility;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.io.File;
import java.io.FileOutputStream;

public class ScreenCapture {
    private static Bitmap getViewBitmapWithoutBottom(View v) {
        if (null == v) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();

        v.measure(View.MeasureSpec.makeMeasureSpec(v.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(v.getHeight(), View.MeasureSpec.EXACTLY));
        v.layout((int) v.getX(), (int) v.getY(), (int) v.getX() + v.getMeasuredWidth(), (int) v.getY() + v.getMeasuredHeight());

        Bitmap bp = Bitmap.createBitmap(v.getDrawingCache(), 0, 0, v.getMeasuredWidth(), v.getMeasuredHeight() - v.getPaddingBottom());
        v.setDrawingCacheEnabled(false);
        v.destroyDrawingCache();
        return bp;
    }

    public static Bitmap getViewBitmap(View v) {
        if (null == v) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        v.measure(View.MeasureSpec.makeMeasureSpec(v.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(v.getHeight(), View.MeasureSpec.EXACTLY));
        v.layout((int) v.getX(), (int) v.getY(), (int) v.getX() + v.getMeasuredWidth(), (int) v.getY() + v.getMeasuredHeight());

        Bitmap b = Bitmap.createBitmap(v.getDrawingCache(), 0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        v.setDrawingCacheEnabled(false);
        v.destroyDrawingCache();
        return b;
    }

    /**
     * 获取 WebView 视图截图
     * @param context
     * @param view
     * @return
     */
    protected static int LONGCAPTURE_LIMITS=11;

    public static boolean isSaftHeightSize(WebView view){
        int visualH=view.getHeight();
        int realH=(int)(view.getContentHeight()*view.getScale());
        return realH/visualH <=LONGCAPTURE_LIMITS;
    }

    public static Bitmap getWebViewBitmap(Context context, WebView view) {
        if (null == view) return null;
        view.scrollTo(0, 0);
        view.buildDrawingCache(true);
        view.setDrawingCacheEnabled(true);
        view.setVerticalScrollBarEnabled(false);
        Bitmap bitmap = getViewBitmapWithoutBottom(view);
        // 可见高度
        int vh = view.getHeight();
        // 容器内容实际高度
        int th = (int)(view.getContentHeight()*view.getScale());
        Bitmap temp;
        int count=0;
        if (th > vh) {
            int w = getScreenWidth(context);
            int absVh = vh - view.getPaddingTop() - view.getPaddingBottom();
            do {
                count++;
                int restHeight = th - vh;
                if (restHeight <= absVh) {
                    view.scrollBy(0, restHeight);
                    vh += restHeight;
                    temp = getViewBitmap(view);
                } else {
                    view.scrollBy(0, absVh);
                    vh += absVh;
                    temp = getViewBitmapWithoutBottom(view);
                }
                bitmap = mergeBitmap(vh, w, temp, 0, view.getScrollY(), bitmap, 0, 0);
            } while (vh < th );
        }
        // 回滚到顶部
        view.scrollTo(0, 0);
        view.setVerticalScrollBarEnabled(true);
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        return bitmap;
    }

    /**
     * 拼接图片
     * @param newImageH
     * @param newImageW
     * @param background
     * @param backX
     * @param backY
     * @param foreground
     * @param foreX
     * @param foreY
     * @return
     */
    public static Bitmap mergeBitmap(int newImageH, int newImageW, Bitmap background, float backX, float backY, Bitmap foreground, float foreX, float foreY) {
        if (null == background || null == foreground) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(newImageW, newImageH, Bitmap.Config.RGB_565);
        Canvas cv = new Canvas(bitmap);
        cv.drawBitmap(background, backX, backY, null);
        cv.drawBitmap(foreground, foreX, foreY, null);
        cv.save(Canvas.ALL_SAVE_FLAG);
        cv.restore();
        return bitmap;
    }

    /**
     * get the width of screen
     */
    public static int getScreenWidth(Context ctx) {
        int w = 0;
        Point p = new Point();
        ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(p);
        w = p.x;
        return w;
    }

    /**
     * 截取scrollview的屏幕
     **/
    public static Bitmap getScrollViewBitmap(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        // 创建对应大小的bitmap

        bitmap = Bitmap.createBitmap(getScreenWidth(scrollView.getContext()), h,
                Bitmap.Config.ARGB_4444);
        final Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.parseColor("#f2f7fa"));
        scrollView.draw(canvas);
        return bitmap;
    }

    /**
     * 生成某个LinearLayout的图片
     *
     * @author gengqiquan
     * @date 2017/3/20 上午10:34
     */
    public static Bitmap getLinearLayoutBitmap(LinearLayout linearLayout) {
        int h = 0;
        // 获取LinearLayout实际高度
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            linearLayout.getChildAt(i).measure(0, 0);
            h += linearLayout.getChildAt(i).getMeasuredHeight();
        }
        linearLayout.measure(0, 0);
        // 创建对应大小的bitmap
        Bitmap bitmap = Bitmap.createBitmap(linearLayout.getMeasuredWidth(), h,
                Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        linearLayout.draw(canvas);
        return bitmap;
    }



}
