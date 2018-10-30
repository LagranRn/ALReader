package com.example.administrator.ezReader.ui.fragment;

import android.content.Context;
import android.net.Uri;
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

public class MainSearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.main_search_vp)
    ViewPager viewPager;
    @BindView(R.id.main_search_tabLayout)
    TabLayout tabLayout;

    private OnFragmentInteractionListener mListener;

    public MainSearchFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MainSearchFragment newInstance() {
        MainSearchFragment fragment = new MainSearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_search, container, false);
        ButterKnife.bind(this,view);
        initView();
        initData();
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





    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
