package org.cc98.mycc98.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.just.library.AgentWeb;


import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseActivity;

import java.util.Locale;

public class UserProfileActivity extends BaseActivity {

    public static final String USER_ID = "userid";
    public static final String UTF_8 = "utf-8";

    private static final String JS_CALL = "nativejava";
    /*
    private static final String localHtmlFile = "file:///android_asset/webTemplate/userInfo/userInfo.html";
    private static final String debugAddr = "http://192.168.123.119:8000/";
    */
    private static final String useridurl = "http://www.cc98.org/user/id/%d";


    protected int userId;

    protected AgentWeb agentWeb;
    protected WebView webView;
    protected LinearLayout mLinearLayout;

    public static void startActivity(Context context) {
        startActivity(context, 0);
    }

    public static void startActivity(Context context, int userId) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(USER_ID, userId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userId = bundle.getInt(USER_ID);

        String url = String.format(Locale.ENGLISH, useridurl, userId);
        setContentView(R.layout.activity_user_profile);
        mLinearLayout = (LinearLayout) findViewById(R.id.activity_webview_location);

        agentWeb = AgentWeb.with(this)//传入Activity or Fragment
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))
                //传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
                .useDefaultIndicator()// 使用默认进度条
                .defaultProgressBarColor() // 使用默认进度条颜色
                .setReceivedTitleCallback(null) //设置 Web 页面的 title 回调
                .createAgentWeb()  //
                .ready()
                .go(url);
        webView = agentWeb.getWebCreator().get();
        webView.addJavascriptInterface(new JavascriptIntact(), JS_CALL);


        configWebSettings(agentWeb.getAgentWebSettings().getWebSettings());


    }

    private WebSettings configWebSettings(WebSettings webSettings) {
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.setTextZoom(100);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        webSettings.setDefaultTextEncodingName(UTF_8);//设置编码格式
        webSettings.setDefaultFontSize(22);//设置 WebView 字体的大小，默认大小为 16
        webSettings.setJavaScriptEnabled(true);  //支持js
        webSettings.setMinimumFontSize(14);//设置 WebView 支持的最小字体大小，默认为 8
        webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        return webSettings;
    }

    public class JavascriptIntact {

        @JavascriptInterface
        public void showToast(String toast) {
            mkToast(toast);
        }

        @JavascriptInterface
        public int getUserId() {
            return userId;
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onPause() {
        agentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        agentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        agentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }
}
