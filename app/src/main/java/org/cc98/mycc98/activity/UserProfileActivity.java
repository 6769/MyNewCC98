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


import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseActivity;
import org.cc98.mycc98.activity.base.BaseWebViewActivity;

import java.util.Locale;

public class UserProfileActivity extends BaseWebViewActivity {

    public static final String USER_ID = "userid";
    public static final String USER_NAME="username";
    public static final String UTF_8 = "utf-8";

    private static final String JS_CALL = "nativejava";



    protected int userId;
    protected String userName;

    public static void startActivity(Context context) {
        startActivity(context, 0);
    }
    public static void startActivity(Context context, int userId){
        startActivity(context,userId,null);
    }

    public static void startActivity(Context context, int userId, String username) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(USER_ID, userId);
        bundle.putString(USER_NAME,username);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userId = bundle.getInt(USER_ID);
        userName=bundle.getString(USER_NAME);


        setContentView(R.layout.activity_user_profile);
        mLinearLayout = findViewById(R.id.activity_webview_location);
        String url;
        if (userName==null){
            url= String.format(Locale.ENGLISH, getString(R.string.user_profile_id_url), userId);
        }else {
            url=String.format(getString(R.string.user_profile_name_url),userName);
        }

        initWebView(url);
        webView.addJavascriptInterface(new JavaScriptApi(),JS_CALL);

    }


    public class JavaScriptApi {

        @JavascriptInterface
        public void showToast(String toast) {
            mkToast(toast);
        }

        @JavascriptInterface
        public int getUserId() {
            return userId;
        }
    }


    }
