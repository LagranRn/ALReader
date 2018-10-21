package com.example.administrator.myapplication.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.bean.Constant;
import com.example.administrator.myapplication.bean.Novel;
import com.example.administrator.myapplication.ui.activity.MainActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.ui.fragment.BookDetailFragment;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class BookDetailActivity extends AppCompatActivity {
    private static final String TAG = "BookDetailActivity";
    Bitmap pngBM = null;
    Novel novel;
    AppBarLayout appBarLayout;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        appBarLayout = findViewById(R.id.app_bar);

        imageView = findViewById(R.id.detail_image);

        for (int i = 0; i < Constant.NOVELS.size(); i ++){
            if (Constant.NOVELS.get(i).getUrl().trim().equals(getIntent().getStringExtra(BookDetailFragment.ARG_ITEM_ID))){
                novel = Constant.NOVELS.get(i);
                break;
            }
        }

        Glide.with(this)
                .load(novel.getCover())
                .into(imageView);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String URL = getIntent().getStringExtra(BookDetailFragment.ARG_ITEM_ID);
                Log.d(TAG, "onClick: " + URL);
                Intent intent = new Intent(BookDetailActivity.this,BookReadActivity.class);
                intent.putExtra("bookurl",URL);
                startActivity(intent);

            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();

            arguments.putString(BookDetailFragment.ARG_ITEM_ID, getIntent().getStringExtra(BookDetailFragment.ARG_ITEM_ID));

            BookDetailFragment fragment = new BookDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
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
