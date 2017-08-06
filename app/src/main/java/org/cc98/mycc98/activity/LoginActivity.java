package org.cc98.mycc98.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseSwipeBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.converter.gson.GsonConverterFactory;

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


    public static void startActivity(Context context){
        Intent intent= new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.act_login_btn_signin)
    protected void conductLogin(View view) {
        String usrname = edt_username.getText().toString().trim();
        String pasname = edt_password.getText().toString();

        //send out to check login info;
        mkToast(usrname + pasname);


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
