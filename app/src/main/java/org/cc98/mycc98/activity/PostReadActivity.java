package org.cc98.mycc98.activity;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import android.webkit.WebViewClient;

import com.google.gson.Gson;

import org.cc98.mycc98.MainApplication;
import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseWebViewActivity;
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

    private TopicInfo topicInfosave;
    private int currentPageBlock;

    private ObservableWebView webView;
    private FloatingActionButton fab;
    private ActionBar actionBar;

    private CC98APIInterface cc98APIInterface;
    private Resources resources;
    private List<PostContent> postContentSave;
    private List<BasicUserInfo>    userInfos;
    private Gson gsonHandler = new Gson();


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
        postContentSave = new ArrayList<>();
        cc98APIInterface = MainApplication.getApiInterface();


        String url=getString(R.string.postview_remote_template)+topicId;
        actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        webView=findViewById(R.id.activity_post_read_webview);
        fab=findViewById(R.id.activity_post_read_reply_btn);
        fab.setOnClickListener(this);

        webView.setOnScrollChangedCallback(new ScrollChangeLisner());

        configWebSettings(webView.getSettings());

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new JavascriptApi(), BRIDGE_TOKEN);
        webView.loadUrl(url);
        requestsNeedsData(0);

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
            case android.R.id.home:
                //WARNING:android.R.id   =.=
                finish();
                break;
            case R.id.menu_postread_share:
                break;
            case R.id.menu_postread_setting:
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        logi("reply topic:"+topicId);
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

    @Deprecated
    protected void requestsNeedsData(final int start) {
        final int size=10;

        Observable<TopicInfo> getTopicinfo = cc98APIInterface.getTopicInfo(topicId);
        Observable<ArrayList<PostContent>> getTopicPosts = cc98APIInterface
                .getTopicPost(topicId, start, size);

        Observable<TopicAndPosts> level1Requests = Observable.zip(getTopicinfo, getTopicPosts,
                new Func2<TopicInfo, ArrayList<PostContent>, TopicAndPosts>() {
                    @Override
                    public TopicAndPosts call(TopicInfo topicInfo, ArrayList<PostContent> postContents) {
                        return new TopicAndPosts(topicInfo, postContents);
                    }
                });
        level1Requests.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TopicAndPosts>() {
                    @Override
                    public void onCompleted() {
                        currentPageBlock=start/10;
                    }

                    @Override
                    public void onError(Throwable e) {
                        loge(e, "Zipped Requests Error");

                    }

                    @Override
                    public void onNext(TopicAndPosts topicAndPosts) {
                        if (topicAndPosts.isTopicValid())

                        {
                            topicInfosave = topicAndPosts.getTopicInfo();
                            PostReadActivity.this.setTitle(topicInfosave.getTitle());
                        }
                        if (topicAndPosts.isPostsValid())
                            postContentSave = topicAndPosts.getPostContents();


                    }
                });


    }


    @Deprecated
    public static class TopicAndPosts {
        TopicInfo topicInfo;
        List<PostContent> postContents;

        public TopicAndPosts(TopicInfo topicInfo, List<PostContent> postContents) {
            this.topicInfo = topicInfo;
            this.postContents = postContents;
        }

        public TopicInfo getTopicInfo() {
            return topicInfo;
        }

        public List<PostContent> getPostContents() {
            return postContents;
        }

        public boolean isTopicValid() {
            return topicInfo != null;
        }

        public boolean isPostsValid() {
            return postContents.size() > 0;
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
            EditActivity.startActivity(PostReadActivity.this,topicId,username+" "+contentRefer);
        }

        @JavascriptInterface
        public void userMessageSend(int userId){
            mkToast("userId"+userId);
        }
        @JavascriptInterface
        public void userProfileView(int userId){
            if (userId>0){
                UserProfileActivity.startActivity(PostReadActivity.this, userId);
            }
        }


        @JavascriptInterface
        public String getUserToken(){
            return MainApplication.getCc98APIManager().getHttpHeaderToken();
        }






    }
}
