package org.cc98.mycc98.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by pip on 2017/7/13.
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<String> mTitle;
    private List<Fragment> mFragments;

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MainFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> title) {
        super(fm);
        mTitle = title;
        mFragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitle == null)
            return String.valueOf(position);
        else
            return mTitle.get(position);
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.size() : 4;
    }
}
