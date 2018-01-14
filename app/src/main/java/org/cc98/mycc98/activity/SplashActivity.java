package org.cc98.mycc98.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;

import org.cc98.mycc98.MainApplication;
import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseActivity;
import org.cc98.mycc98.config.UserConfig;


import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import win.pipi.api.data.UserInfo;
import win.pipi.api.network.CC98APIInterface;

public class SplashActivity extends BaseActivity {

    private CC98APIInterface iface;
    private Gson gson=new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        iface= MainApplication.getApiInterface();
        Observable<UserInfo> me=iface.getMe();
        me.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new GetMeLogin());
    }

    protected class GetMeLogin implements Observer<UserInfo>{

        @Override
        public void onCompleted() {
            MainActivity.startActivity(SplashActivity.this);
            finish();

        }

        @Override
        public void onError(Throwable e) {
            LoginActivity.startActivity(SplashActivity.this);
            loge(e,"userLogin check failed,NewLogin");
            finish();
        }

        @Override
        public void onNext(UserInfo userInfo) {
            //some serialized object;
            UserConfig.setUserInfo(userInfo);

        }
    }



}
