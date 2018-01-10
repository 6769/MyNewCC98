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
import org.cc98.mycc98.activity.base.BaseWebViewActivity;

import java.util.Locale;

public class UserProfileActivity extends BaseWebViewActivity {

    public static final String USER_ID = "userid";
    public static final String UTF_8 = "utf-8";

    private static final String JS_CALL = "nativejava";
    private static final String useridurl = "http://www.cc98.org/user/id/%d";


    protected int userId;

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
        mLinearLayout = findViewById(R.id.activity_webview_location);


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
