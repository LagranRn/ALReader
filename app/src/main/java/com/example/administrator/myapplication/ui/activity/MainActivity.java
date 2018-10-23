package com.example.administrator.myapplication.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.administrator.myapplication.bean.Constant;
import com.example.administrator.myapplication.ui.fragment.BookDetailFragment;
import com.example.administrator.myapplication.ui.fragment.BookItemFragment;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Novel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BookItemFragment.OnListFragmentInteractionListener,NavigationView.OnNavigationItemSelectedListener{


    @BindView(R.id.vp_Container)
    ViewPager mViewPager;
    @BindView(R.id.drawer_main)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_main)
    NavigationView navigationView;
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getExternalFilesDir(null);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(BookItemFragment.newInstance(Constant.GIRL_URL));
        fragments.add(BookItemFragment.newInstance(Constant.CHINA_URL));
        fragments.add(BookItemFragment.newInstance(Constant.CITY_URL));
        fragments.add(BookItemFragment.newInstance(Constant.FANTACY_URL));
        fragments.add(BookItemFragment.newInstance(Constant.HISTORY_URL));
        fragments.add(BookItemFragment.newInstance(Constant.ONLINEGAME_URL));
        fragments.add(BookItemFragment.newInstance(Constant.SCIENCE_URL));
        mSectionsPagerAdapter.setFragments(fragments);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.vp_Container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs1);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    @Override
    public void onListFragmentInteraction(Novel novel) {
        Intent intent = new Intent(MainActivity.this,BookDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("novel",novel);
        intent.putExtra(BookDetailFragment.ARG_ITEM_ID,bundle);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.menu_information:
                Toast.makeText(this, "个人信息", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(Gravity.START);
                break;
            case R.id.menu_password:
                Toast.makeText(this, "密码", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(Gravity.START);
                break;
                default:
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                Toast.makeText(this, "nonon", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments = new ArrayList<>();
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setFragments(List<Fragment> fragments) {
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
//            return PlaceholderFragment.newInstance(position + 1);
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return fragments.size();
        }
    }
}
