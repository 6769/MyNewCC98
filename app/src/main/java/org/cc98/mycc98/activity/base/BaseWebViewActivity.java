package org.cc98.mycc98.activity.base;

import android.annotation.TargetApi;
import android.net.Uri;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;


import com.baidu.mobstat.StatService;
import com.just.agentweb.AgentWeb;
import com.orhanobut.logger.Logger;

import org.cc98.mycc98.MainApplication;
import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.UserProfileActivity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.Response;
import win.pipi.api.network.CC98APIInterface;
import win.pipi.api.network.CC98APIManager;

/**
 * Created by pipi6 on 2018/1/9.
 */

public class BaseWebViewActivity extends BaseSwipeBackActivity {
    public static final String UTF_8 = "utf-8";
    public static final String MEMI_TYPE="application/json";
    protected String urlToLoad;

    protected String callBridge;
    protected AgentWeb agentWeb;
    protected WebView webView;
    protected LinearLayout mLinearLayout;
    private OkHttpClient client=new OkHttpClient();

    protected void initWebView(String url) {
        urlToLoad = url;

        if (mLinearLayout == null) {
            Logger.w("Must Initialze Webview container");
            return;
        }

        agentWeb = AgentWeb.with(this)//传入Activity or Fragment
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))
                //传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
                .useDefaultIndicator()// 使用默认进度条

                .defaultProgressBarColor() // 使用默认进度条颜色
                .setReceivedTitleCallback(null) //设置 Web 页面的 title 回调
                //.setWebViewClient(new ApiGrantedWebViewClient())
                .createAgentWeb()  //
                .ready()
                .go(urlToLoad);
        webView = agentWeb.getWebCreator().get();



        configWebSettings(agentWeb.getAgentWebSettings().getWebSettings());

    }

    public class ApiGrantedWebViewClient extends WebViewClient {
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, final WebResourceRequest request) {
            Uri uri=request.getUrl();
            String url=uri.getHost();
            final String API_HOST=getString(R.string.api_cc98_host_part);
            if (url.contains(API_HOST)){
                //logi("hook token "+uri.toString());
                CC98APIManager apiManager=MainApplication.getCc98APIManager();
                try {



                    Request.Builder requestBuilder = new Request.Builder().url(uri.toString());
                    Request request1=requestBuilder
                            .addHeader(apiManager.AUTH_PARA_HEADER,apiManager.getHttpHeaderToken())
                            .removeHeader("referer")
                            .build();
                    Call call=client.newCall(request1);
                    Response response=call.execute();
                    String datahook=response.body().string();
                    InputStream responseInputStream = new ByteArrayInputStream(datahook.getBytes());


                    return new WebResourceResponse(MEMI_TYPE,UTF_8 , responseInputStream);
                }  catch (IOException e) {
                    //return null to tell WebView we failed to fetch it WebView should try again.
                    return null;
                }  catch (Exception e){
                    return null;
                }
            }
            else return null;
            //fuck call here,super finally get a null;
        }
    }


    protected WebSettings configWebSettings(WebSettings webSettings) {
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

        webSettings.setCacheMode(android.webkit.WebSettings.LOAD_DEFAULT);

        return webSettings;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView!=null && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onPause() {
        StatService.onPause(this);
        if (agentWeb!=null)
        agentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        StatService.onResume(this);
        if (agentWeb!=null)
        agentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (agentWeb!=null)
        agentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }
}
