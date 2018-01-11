package org.cc98.mycc98.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

import com.google.gson.Gson;

import org.cc98.mycc98.MainApplication;
import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseWebViewActivity;

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

public class PostReadActivity extends BaseWebViewActivity {

    public static final String TOPIC_ID = "TOPIC_ID";
    private static final String BRIDGE_TOKEN = "nativeface";
    private int topicId;

    private TopicInfo topicInfosave;
    private int currentPageBlock;

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

        mLinearLayout = findViewById(R.id.activity_post_read_webviewcontainer);

        String url=getString(R.string.postview_remote_template)+topicId;
        webView=findViewById(R.id.activity_post_read_webview);

        //initWebView(getString(R.string.postview_local_template));
        configWebSettings(webView.getSettings());

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new JavascriptApi(), BRIDGE_TOKEN);
        webView.loadUrl(url);

    }

    protected void onRefresh(){


    }

    protected void requestsNeedsData(final int start,final int size) {


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
                            topicInfosave = topicAndPosts.getTopicInfo();
                        if (topicAndPosts.isPostsValid())
                            postContentSave = topicAndPosts.getPostContents();
                        webView.reload();

                    }
                });


    }


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
        public String getTopicInfo() {
            return gsonHandler.toJson(topicInfosave);

        }

        @JavascriptInterface
        public String getUserInfos(){

            return gsonHandler.toJson(userInfos);

        }


        @JavascriptInterface
        public String getUserToken(){
            return MainApplication.getCc98APIManager().getHttpHeaderToken();
        }

        @JavascriptInterface
        public String getPostsInfo(int block) {
            try{
                Response<TopicInfo> response0=cc98APIInterface.getTopicInfoCall(topicId).execute();
                topicInfosave= response0.body();

                Response<ArrayList<PostContent>> response=cc98APIInterface.getTopicPostCall(topicId,block*10,10).execute();
                postContentSave=response.body();

                /*Map<String,Integer> idsmap=new TreeMap<>();
                for(PostContent i:postContentSave){
                    idsmap.put("id",i.getUserId());
                    // key is covered...
                }
                Response<ArrayList<BasicUserInfo>> response1=cc98APIInterface.getBasicUserInfos(idsmap).execute();
                userInfos=response1.body();*/




                return gsonHandler.toJson(postContentSave);
            }catch (IOException e){
                return null;
            }


        }

    }
}
