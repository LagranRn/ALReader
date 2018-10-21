package com.example.administrator.myapplication.ui.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Constant;
import com.example.administrator.myapplication.bean.Novel;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class BookDetailFragment extends Fragment {
    private static final String TAG = "BookDetailFragment";
    public static final String ARG_ITEM_ID = "3";

    private Novel novel;

    int position = 0;

    public BookDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            String bookUrl = getArguments().getString(ARG_ITEM_ID);
            for (int i = 0; i < Constant.NOVELS.size(); i ++){
                if (bookUrl.equals(Constant.NOVELS.get(i).getUrl())){
                    novel = Constant.NOVELS.get(i);
                    break;
                }
            }

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle("《"+novel.getName()+"》");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.layout,container,false);

        return view;
    }

}
