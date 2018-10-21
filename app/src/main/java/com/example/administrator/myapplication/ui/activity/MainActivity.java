package com.example.administrator.myapplication.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.myapplication.Adapter.FragmentAdapter;
import com.example.administrator.myapplication.ui.fragment.BookDetailFragment;
import com.example.administrator.myapplication.ui.fragment.ItemFragment;
import com.example.administrator.myapplication.ui.fragment.BlankFragment;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.DummyContent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getExternalFilesDir(null);

        List<Fragment> mFragments = new ArrayList<>();

        mFragments.add(ItemFragment.newInstance(1));
        mFragments.add(BlankFragment.newInstance());

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.setmFragment(mFragments);

        ViewPager mViewPager = findViewById(R.id.vp_Container);
        mViewPager.setAdapter(adapter);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Intent intent = new Intent(MainActivity.this,BookDetailActivity.class);
        intent.putExtra(BookDetailFragment.ARG_ITEM_ID,item.id);
        startActivity(intent);

    }

}
