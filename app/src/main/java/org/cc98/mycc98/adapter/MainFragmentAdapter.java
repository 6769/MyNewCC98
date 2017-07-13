package org.cc98.mycc98.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.cc98.mycc98.fragment.BoardFragment;
import org.cc98.mycc98.fragment.PostFragment;

import java.util.List;

/**
 * Created by pip on 2017/7/13.
 */

public class MainFragmentAdapter extends FragmentPagerAdapter {

    private List<String> mTitle;
    private List<Fragment> mFragments;

    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public MainFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> title) {
        super(fm);
        mTitle = title;
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        if (null == mFragments)
            return new PostFragment();
        else
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
