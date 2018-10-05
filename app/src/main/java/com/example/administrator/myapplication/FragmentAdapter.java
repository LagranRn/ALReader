package com.example.administrator.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragment;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setmFragment(List<Fragment> mFragment) {
        this.mFragment = mFragment;
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    @Override
    public Fragment getItem(int i) {
        return mFragment.get(i);
    }

}
