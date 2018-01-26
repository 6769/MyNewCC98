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
import org.cc98.mycc98.utility.AppInfo;

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
        version.setTitle(AppInfo.getPackageVersionName(this));
        return version;
    }


}
