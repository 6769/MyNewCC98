package org.cc98.mycc98.activity;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;

import org.cc98.mycc98.MainApplication;
import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseWebViewActivity;
import org.cc98.mycc98.utility.ImageProcess;
import org.cc98.mycc98.webview.ObservableWebView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import win.pipi.api.data.BasicUserInfo;
import win.pipi.api.data.PostContent;
import win.pipi.api.data.TopicInfo;
import win.pipi.api.data.UserInfo;
import win.pipi.api.network.CC98APIInterface;

public class PostReadActivity extends BaseWebViewActivity implements View.OnClickListener {

    public static final String TOPIC_ID = "TOPIC_ID";
    public static final String TOPIC_TITLE="TOPIC_TITLE";
    private static final String BRIDGE_TOKEN = "nativeface";
    private int topicId;



    private ObservableWebView webView;
    private FloatingActionButton fab;
    private ActionBar actionBar;


    private Resources resources;



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


        String url = getString(R.string.postview_local_template) + topicId;
        actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        webView=findViewById(R.id.activity_post_read_webview);
        super.webView = webView;
        //register Goback Handle.


        fab=findViewById(R.id.activity_post_read_reply_btn);
        fab.setOnClickListener(this);

        webView.setOnScrollChangedCallback(new ScrollChangeLisner());

        configWebSettings(webView.getSettings());

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
            if (ImageProcess.isPhotoUrl(url)) {//handle images' url;
                PhotoViewActivity.startActivity(PostReadActivity.this,url);
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
            //PostReadActivity.this.setTitle(view.getTitle());//onReceivedTitle has done;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_posts_read,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_postread_share:
                break;
            case R.id.menu_postread_setting:
                SettingActivity.startActivity(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        logi("reply topic: "+topicId);
        EditActivity.startActivity(this,topicId,"");
    }

    protected class ScrollChangeLisner implements ObservableWebView.OnScrollChangedCallback {
        //not implemented view action as we wanted.
        @Override
        public void onScroll(int dx, int dy) {
            if (dy>0){
                fab.hide();
            }
            if (dy<-5){
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
        public int getTopicId(){
            return topicId;
        }

        @JavascriptInterface
        public void replyHasRefers(String username,String contentRefer){
            logi(username+contentRefer);
            EditActivity.startActivity(PostReadActivity.this, topicId,
                    String.format(getString(R.string.postread_refercontent_to_edit), username, contentRefer));
        }

        @JavascriptInterface
        public void userMessageSend(int userId){
            mkToast("userId  "+userId);
        }
        @JavascriptInterface
        public void userProfileView(int userId){
            UserProfileActivity.startActivity(PostReadActivity.this, userId);
        }
        @JavascriptInterface
        public String getUserToken(){
            return MainApplication.getCc98APIManager().getHttpHeaderToken();
        }

    }
}
