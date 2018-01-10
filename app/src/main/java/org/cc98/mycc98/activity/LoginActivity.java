package org.cc98.mycc98.activity;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.orhanobut.logger.Logger;

import org.cc98.mycc98.MainApplication;
import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.ActivityCollector;
import org.cc98.mycc98.activity.base.BaseSwipeBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import win.pipi.api.authorization.LoginCC98;
import win.pipi.api.authorization.beans.AccessTokenPayload;

/**
 * Created by pip on 2017/7/12.
 */

public class LoginActivity extends BaseSwipeBackActivity {

    @BindView(R.id.act_login_tx_username)
    EditText edt_username;
    @BindView(R.id.act_login_tx_password)
    EditText edt_password;

    @BindView(R.id.act_login_btn_signin)
    Button signin;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityCollector.justKeepLast();


    }

    String usrname;
    String pass;
    LoginCC98 loginCC98;

    @OnClick(R.id.act_login_btn_signin)
    protected void conductLogin(View view) {
        usrname = edt_username.getText().toString().trim();
        pass = edt_password.getText().toString();
        if (usrname.isEmpty() || pass.isEmpty()) {
            mkToast("Empty passcode");
            return;
        }

        //send out to check login info;


        loginCC98 = MainApplication.getLoginCC98instance();
        loginCC98.setPassword(pass);
        loginCC98.setUsername(usrname);

        Observable<AccessTokenPayload> call=loginCC98.loginRx();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AccessTokenPayload>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e,"Login error");
                        mkToast("Login Failed");
                    }

                    @Override
                    public void onNext(AccessTokenPayload accessTokenPayload) {
                        mkToast("Login success");
                        loginCC98.setAccessTokenPayload(accessTokenPayload);
                        saveUserTokenPersist(loginCC98);
                        MainActivity.startActivity(LoginActivity.this);
                        finish();
                    }
                });

    }

    protected void saveUserTokenPersist(LoginCC98 ploginCC98){
        SharedPreferences sharedPreferences=  getSharedPreferences(getString(R.string.pref_userlogin_storage), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString( getString(R.string.pref_username), ploginCC98.getUsername());
        editor.putString( getString(R.string.pref_password), ploginCC98.getPassword());
        editor.apply();
    }
    public static void loadUserTokenPersist(Context context,LoginCC98 ploginCC98){
        SharedPreferences sharedPreferences= context.getSharedPreferences(
                context.getString(R.string.pref_userlogin_storage),
                Context.MODE_PRIVATE);
        ploginCC98.setUsername(sharedPreferences.getString(context.getString(R.string.pref_username),""));
        ploginCC98.setPassword(sharedPreferences.getString(context.getString(R.string.pref_password),""));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
