package com.example.administrator.myapplication.Adapter;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PageAdapter extends FragmentPagerAdapter {
    List<Fragment> pages = new ArrayList<>();

    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setPages(List<Fragment> pages) {
        this.pages = pages;
    }

    @Override
    public Fragment getItem(int i) {
        return pages.get(i);
    }

    @Override
    public int getCount() {
        return pages.size();
    }
}
