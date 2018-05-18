package org.cc98.mycc98.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jakewharton.rxbinding.view.RxView;

import org.cc98.mycc98.MainApplication;
import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseWebViewActivity;
import org.cc98.mycc98.config.ApplicationConfig;
import org.cc98.mycc98.utility.ImageUtil;
import org.cc98.mycc98.utility.ScreenCapture;
import org.cc98.mycc98.utility.ShareContent;
import org.cc98.mycc98.webview.ObservableWebView;

import java.io.File;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class PostReadActivity extends BaseWebViewActivity implements View.OnClickListener {

    public static final String TOPIC_ID = "TOPIC_ID";
    public static final String TOPIC_TITLE = "TOPIC_TITLE";
    private static final String BRIDGE_TOKEN = "nativeface";
    private int topicId;


    private ObservableWebView webView;
    private FloatingActionButton fab;
    private ActionBar actionBar;


    private Resources resources;
    private File captureFileToSave;


    public static void startActivity(Context context, int topicID) {
        Intent intent = new Intent(context, PostReadActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(TOPIC_ID, topicID);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_read);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        resources = getResources();
        topicId = bundle.getInt(TOPIC_ID,
                resources.getInteger(R.integer.default_bug_report_topicid));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean localSettingDebug = preferences.getBoolean(getString(R.string.pref_debug_mode_key), false);
        String urlTemplate = ApplicationConfig.getIsDebugMode() && localSettingDebug ? getString(R.string.postview_remote_template) : getString(R.string.postview_local_template);

        String url = urlTemplate + topicId;
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        webView = findViewById(R.id.activity_post_read_webview);
        super.webView = webView;
        //register Goback Handle.


        fab = findViewById(R.id.activity_post_read_reply_btn);
        RxView.clicks(fab).throttleFirst(resources.getInteger(R.integer.window_duration_time), TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        onClick(null);
                    }
                });

        webView.setOnScrollChangedCallback(new ScrollChangeLisner());

        configWebSettings(webView.getSettings());
        webView.setDrawingCacheEnabled(true);
        webView.setWebViewClient(new PostReadWebClient());
        webView.setWebChromeClient(new PostReadWebChromeClient());
        webView.addJavascriptInterface(new JavascriptApi(), BRIDGE_TOKEN);
        webView.loadUrl(url);


    }

    private class PostReadWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            PostReadActivity.this.setTitle(title);
        }
    }

    private class PostReadWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Uri currentUri = Uri.parse(url);
            if (ImageUtil.isPhotoUrl(url)) {//handle images' url;
                PhotoViewActivity.startActivity(PostReadActivity.this, url);
                return true;
            }

            if (currentUri.getHost().equals(getString(R.string.bbs_cc98_host_part))) {
                return false;
            } else {
                NormalWebviewActivity.startActivity(PostReadActivity.this, url);
                return true;
            }

        }

        @Override
        public void onPageFinished(WebView view, String url) {
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_posts_read, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_postread_share:
                String fmt = getString(R.string.cc98topic_urlshare_template);
                ShareContent.shareTextDefaultTitle(this,
                        String.format(fmt, getTitle(), String.valueOf(topicId)));
                break;

            case R.id.menu_postread_long_screen_capture:

                break;

                
            case R.id.menu_postread_short_screen_capture:
                createScreenCapture();
                break;

            case R.id.menu_postread_setting:
                SettingActivity.startActivity(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        info("reply topic: " + topicId);
        EditActivity.startActivity(this, topicId, "");
    }


    /*
    * reference:https://blog.csdn.net/duanyy1990/article/details/72552965
    *
    * the following codes has hazardous for OOM;
    * */

    protected void createScreenCapture() {
        captureFileToSave = ImageUtil.getDCIMNewImageFile(this);
        info("creating: captureFileToSave" + captureFileToSave);

        Observable.just(webView)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<ObservableWebView, Observable<Bitmap>>() {
                    @Override
                    public Observable<Bitmap> call(ObservableWebView observableWebView) {
                        //Bitmap bitmap = ScreenCapture.getWebViewBitmap(PostReadActivity.this, observableWebView);
                        Bitmap bitmap=ScreenCapture.getViewBitmap(observableWebView);
                        return Observable.just(bitmap);
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Func1<Bitmap, Object>() {
                    @Override
                    public Object call(Bitmap bitmap) {
                        ImageUtil.saveBitmapToFile(PostReadActivity.this, bitmap, captureFileToSave);
                        return captureFileToSave;
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        info(o.toString());
                    }
                });


    }


    protected class ScrollChangeLisner implements ObservableWebView.OnScrollChangedCallback {
        //not implemented view action as we wanted.
        @Override
        public void onScroll(int dx, int dy) {
            if (dy > 0) {
                fab.hide();
            }
            if (dy < -5) {
                fab.show();
            }

        }
    }


    protected class JavascriptApi {
        @JavascriptInterface
        public void showToast(String toast) {
            mkToast(toast);
        }

        @JavascriptInterface
        public int getTopicId() {
            return topicId;
        }

        @JavascriptInterface
        public void replyHasRefers(String username, String contentRefer) {
            info(username + contentRefer);
            EditActivity.startActivity(PostReadActivity.this, topicId,
                    String.format(getString(R.string.postread_refercontent_to_edit), username, contentRefer));
        }

        @JavascriptInterface
        public void userMessageSend(int userId) {
            mkToast("userId  " + userId);
        }

        @JavascriptInterface
        public void userProfileView(int userId) {
            UserProfileActivity.startActivity(PostReadActivity.this, userId);
        }

        @JavascriptInterface
        public String getUserToken() {
            return MainApplication.getCc98APIManager().getHttpHeaderToken();
        }

    }
}
