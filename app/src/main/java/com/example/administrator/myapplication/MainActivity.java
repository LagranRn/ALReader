package com.example.administrator.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.administrator.myapplication.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener,PlusOneFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PlusOneFragment plusOneFragment = new PlusOneFragment();
        ItemFragment itemFragment = new ItemFragment();

        List<Fragment> mFragments = new ArrayList<>();

        mFragments.add(itemFragment);
        mFragments.add(plusOneFragment);

        FragmentAdapter  adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.setmFragment(mFragments);

        ViewPager mViewPager = findViewById(R.id.vp_Container);
        mViewPager.setAdapter(adapter);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Intent intent = new Intent(MainActivity.this,ItemDetailActivity.class);
        intent.putExtra(ItemDetailFragment.ARG_ITEM_ID,item.id);
        startActivity(intent);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
