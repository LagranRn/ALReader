package com.example.administrator.ezReader.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.ezReader.R;
import com.example.administrator.ezReader.adapter.SectionsPagerAdapter;
import com.example.administrator.ezReader.bean.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainNetBookFragment extends Fragment {

    @BindView(R.id.main_search_vp)
    ViewPager viewPager;
    @BindView(R.id.main_search_tabLayout)
    TabLayout tabLayout;

    public MainNetBookFragment() {
    }


    public static MainNetBookFragment newInstance() {
        MainNetBookFragment fragment = new MainNetBookFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_search, container, false);
        ButterKnife.bind(this,view);
        initData();
        initView();
        return view;
    }

    public void initView(){
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

    }

    public void initData(){
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < Constant.BOOKURLS.size(); i ++){
            fragments.add(BookItemFragment.newInstance(Constant.BOOKURLS.get(i)));
        }


        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());
        mSectionsPagerAdapter.setFragments(fragments);

        viewPager.setAdapter(mSectionsPagerAdapter);
    }
}
