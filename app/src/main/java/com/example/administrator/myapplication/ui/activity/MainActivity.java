package com.example.administrator.myapplication.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.administrator.myapplication.adapter.SectionsPagerAdapter;
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


    @BindView(R.id.main_vp_container)
    ViewPager mViewPager;
    @BindView(R.id.main_drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.main_nav)
    NavigationView navigationView;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_tabLayout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        this.getExternalFilesDir(null);

        initView();
        initData();

    }

    public void initView(){
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    public void initData(){

        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < Constant.BOOKURLS.size(); i ++){
            fragments.add(BookItemFragment.newInstance(Constant.BOOKURLS.get(i)));
        }

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.setFragments(fragments);

        mViewPager.setAdapter(mSectionsPagerAdapter);
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
                Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
