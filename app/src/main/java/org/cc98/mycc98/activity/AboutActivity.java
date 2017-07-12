package org.cc98.mycc98.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.liuguangqiang.swipeback.SwipeBackLayout;

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
        //setContentView(R.layout.activity_about);
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.dummy_image)
                .setDescription(getString(R.string.about_mycc98))
                .addItem(new Element().setTitle("Version 6.2"))
                .addGroup("Connect with us")
                .addEmail("elmehdi.sakout@gmail.com")
                .addWebsite("http://medyo.github.io/")
                .addFacebook("the.medy")
                .addTwitter("medyo80")
                .addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
                .addPlayStore("com.ideashower.readitlater.pro")
                .addInstagram("medyo80")
                .addGitHub("medyo")
                //.addItem(getCopyRightsElement())
                .create();

        setContentView(aboutPage);
        //setDragEdge(SwipeBackLayout.DragEdge.LEFT);
    }
}
