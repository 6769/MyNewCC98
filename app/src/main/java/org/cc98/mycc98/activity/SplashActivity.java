package org.cc98.mycc98.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.baidu.mobstat.StatService;

import org.cc98.mycc98.MainApplication;
import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseActivity;
import org.cc98.mycc98.config.ApplicationConfig;
import org.cc98.mycc98.config.UserConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import win.pipi.api.data.UserInfo;
import win.pipi.api.network.CC98APIInterface;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.activity_splash_image)
    ImageView imageView;

    private CC98APIInterface iface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        iface = MainApplication.getApiInterface();
        Observable<UserInfo> me = iface.getMe();
        me.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new GetMeLogin());

        /*
        Init Baidu Statistical Services;
        *
        * */
        if(ApplicationConfig.getStatEnable()){
            StatService.start(this);
        }


    }

    protected class GetMeLogin implements Observer<UserInfo> {
        @Override
        public void onCompleted() {
            MainActivity.startActivity(SplashActivity.this);
            finish();
        }

        @Override
        public void onError(Throwable e) {
            LoginActivity.startActivity(SplashActivity.this);
            mkToast("登陆异常/网络异常");
            error(e, "userLogin check failed,NewLogin");
            finish();
        }

        @Override
        public void onNext(UserInfo userInfo) {
            //some serialized object;
            UserConfig.setUserInfo(userInfo);

        }
    }


}
