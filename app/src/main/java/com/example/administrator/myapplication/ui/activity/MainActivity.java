package com.example.administrator.myapplication.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.administrator.myapplication.Adapter.FragmentAdapter;
import com.example.administrator.myapplication.ui.fragment.BookDetailFragment;
import com.example.administrator.myapplication.ui.fragment.ItemFragment;
import com.example.administrator.myapplication.ui.fragment.BlankFragment;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Novel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {


    @BindView(R.id.vp_Container)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getExternalFilesDir(null);
        ButterKnife.bind(this);

        List<Fragment> mFragments = new ArrayList<>();

        mFragments.add(ItemFragment.newInstance(1));
        mFragments.add(BlankFragment.newInstance());

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.setmFragment(mFragments);

        mViewPager.setAdapter(adapter);
    }

    @Override
    public void onListFragmentInteraction(Novel novel) {
        Intent intent = new Intent(MainActivity.this,BookDetailActivity.class);
        intent.putExtra(BookDetailFragment.ARG_ITEM_ID,novel.getUrl());
        startActivity(intent);
    }
}
