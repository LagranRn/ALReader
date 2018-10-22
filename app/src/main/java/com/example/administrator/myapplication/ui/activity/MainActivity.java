package com.example.administrator.myapplication.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.administrator.myapplication.Adapter.FragmentAdapter;
import com.example.administrator.myapplication.ui.fragment.BookDetailFragment;
import com.example.administrator.myapplication.ui.fragment.BookItemFragment;
import com.example.administrator.myapplication.ui.fragment.BlankFragment;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getExternalFilesDir(null);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        List<Fragment> mFragments = new ArrayList<>();

        mFragments.add(BookItemFragment.newInstance(1));
        mFragments.add(BlankFragment.newInstance());

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.setmFragment(mFragments);

        mViewPager.setAdapter(adapter);
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
}
