package org.cc98.mycc98.activity.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.baidu.mobstat.StatService;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.orhanobut.logger.Logger;

/**
 * Created by pip on 2017/7/12.
 */

public class BaseSwipeBackActivity extends BaseActivity {
    public static final String TAG = "BaseSwipeBackActivity";

    public static void startActivity(Context context){
        throw new UnsupportedClassVersionError("Not Implemented Static Method");
    }

    protected void mkToast(String msg,boolean showMoreTime){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }


}
