package com.example.administrator.myapplication.Main;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.myapplication.Adapter.FragmentAdapter;
import com.example.administrator.myapplication.ItemDetail.ItemDetailActivity;
import com.example.administrator.myapplication.ItemDetail.ItemDetailFragment;
import com.example.administrator.myapplication.ItemList.ItemFragment;
import com.example.administrator.myapplication.ItemList.ItemListActivity;
import com.example.administrator.myapplication.MyStar.BlankFragment;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Fragment> mFragments = new ArrayList<>();

        mFragments.add(ItemFragment.newInstance(2));
        mFragments.add(BlankFragment.newInstance());

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.setmFragment(mFragments);

        ViewPager mViewPager = findViewById(R.id.vp_Container);
        mViewPager.setAdapter(adapter);
        startActivity(new Intent(MainActivity.this,ItemListActivity.class));
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Intent intent = new Intent(MainActivity.this,ItemDetailActivity.class);
        intent.putExtra(ItemDetailFragment.ARG_ITEM_ID,item.id);
        startActivity(intent);

    }

}
