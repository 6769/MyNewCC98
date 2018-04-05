package org.cc98.mycc98.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import net.lucode.hackware.magicindicator.MagicIndicator;

import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseSwipeBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
* Receive all four types of messages, represents here.
* */

public class PmActivity extends BaseSwipeBackActivity {


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, PmActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.activity_pm_view_pager_magic_indicator)
    MagicIndicator activityPmViewPagerMagicIndicator;
    @BindView(R.id.activity_pm_view_pager)
    ViewPager activityPmViewPager;
    @BindView(R.id.activity_pm_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pm);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


    }
}
