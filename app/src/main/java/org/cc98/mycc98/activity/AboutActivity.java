package org.cc98.mycc98.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseSwipeBackActivity;
import org.cc98.mycc98.utility.AppInfo;
import org.cc98.mycc98.utility.ShareContent;

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
                .setImage(R.mipmap.ic_launcher98)
                .setDescription(getString(R.string.about_mycc98))
                .addGroup("Version")
                .addItem(getVersionRow())
                .addGroup("Connect with us")
                .addEmail(getString(R.string.application_contact_email_addr))
                .addWebsite(getString(R.string.application_github_website_url))
                .addGitHub(getString(R.string.application_contact_github_user))
                .addItem(getShareAppRow())
                .create();

        setContentView(aboutPage);

    }

    private Element getShareAppRow() {
        Element row = new Element();
        String title=getString(R.string.application_share_to_download_title);
        String downloadUrl=getString(R.string.application_share_to_download_url);

        String messageTemplate=getString(R.string.application_share_to_download_template);
        String text=String.format(messageTemplate,downloadUrl,getString(R.string.app_name));
        row.setTitle(title).setIconDrawable(R.drawable.ic_share_black_24dp);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareContent.shareText(AboutActivity.this, text, title);
            }
        });
        return row;
    }

    private Element getVersionRow() {
        Element version = new Element();
        version.setTitle(AppInfo.getPackageVersionName(this));
        return version;
    }


}
