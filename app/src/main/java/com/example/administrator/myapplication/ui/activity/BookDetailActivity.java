package com.example.administrator.myapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.bean.Novel;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.ui.fragment.BookDetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class BookDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "BookDetailActivity";
    private Novel novel;
    private Bundle savedInstanceState;


    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.detail_image)
    ImageView imageView;
    @BindView(R.id.detail_image_new)
    ImageView newImageView;
    @BindView(R.id.detail_fab)
    FloatingActionButton fab;
    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        initData();
        initView();


    }

    public void initData(){
        //初始化novel信息
        novel = (Novel) getIntent()
                .getBundleExtra(BookDetailFragment.ARG_ITEM_ID)
                .getSerializable("novel");
        //加载背景虚化图片
        Glide.with(this)
                .load(novel.getCover())
                .bitmapTransform(new BlurTransformation(this, 15))
                .into(imageView);
        //加载图片
        Glide.with(this)
                .load(novel.getCover())
                .into(newImageView);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //传值到fragment
        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();
            arguments.putSerializable(BookDetailFragment.ARG_ITEM_ID,novel);
            BookDetailFragment fragment = new BookDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();

        }
    }

    public void initView(){
        fab.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_fab:

                Bundle bundle = new Bundle();
                bundle.putSerializable("novel",novel);
                Intent intent = new Intent(BookDetailActivity.this,BookReadActivity.class);
                intent.putExtra("novel",bundle);
                intent.putExtra("bookType","0");
                startActivity(intent);
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
