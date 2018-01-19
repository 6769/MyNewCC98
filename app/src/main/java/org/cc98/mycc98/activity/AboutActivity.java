package org.cc98.mycc98.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;


import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.aboutlibraries.ui.LibsFragment;

import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseSwipeBackActivity;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends BaseSwipeBackActivity {

    public static void startActivity(Context context){
        Intent intent= new Intent(context,AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.dummy_image)
                .setDescription(getString(R.string.about_mycc98))
                .addGroup("Version")
                .addItem(getVersionRow())
                .addGroup("Connect with us")
                .addEmail(getString(R.string.application_contact_email_addr))
                .addWebsite(getString(R.string.application_github_website_url))
                .addGitHub(getString(R.string.application_contact_github_user))
                //.addItem(getReadMore())
                .create();

        setContentView(aboutPage);

    }

    private Element getReadMore() {
        Element openlib = new Element();
        openlib.setTitle("More").setIconDrawable(R.drawable.ic_view_quilt_black_36dp);
        openlib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        return openlib;
    }

    private Element getVersionRow() {
        Element version = new Element();
        version.setTitle(GetPackageInfomation.getPackageVersionName(this));
        return version;
    }


    public static class GetPackageInfomation {
        public static String name;
        public static final String ERRORNAME = "VersionName Error";

        public static String getPackageVersionName(Context context) {

            PackageManager pm = context.getPackageManager();

            try {
                PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
                //getPackageName()是你当前类的包名，0代表是获取版本信息
                name = pi.versionName;
                //int code = pi.versionCode;

            } catch (PackageManager.NameNotFoundException e) {
                //Logg.e(TAG,ERRORNAME,e);
                name = ERRORNAME;
            }
            return name;

        }
    }

}
