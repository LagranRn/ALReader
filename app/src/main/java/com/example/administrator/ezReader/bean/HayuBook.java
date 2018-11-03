package com.example.administrator.ezReader.bean;

import android.view.View;

import com.example.administrator.ezReader.R;

import java.io.Serializable;

public class HayuBook implements Serializable {
    private static final String TAG = "HayuBook";
    private String id;
    private String name;
    private String content;
    View v;

    public HayuBook(String id, String name) {
        this.id = id;
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLayoutId() {
        return R.layout.item_hayu_book;
    }


}
