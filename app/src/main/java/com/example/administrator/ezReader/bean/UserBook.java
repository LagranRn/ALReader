package com.example.administrator.ezReader.bean;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ezReader.R;
import com.example.library.BaseAdapter;
import com.example.library.IEntity;

import java.io.Serializable;

public class UserBook implements IEntity<UserBook>,Serializable {
    private String name;
    private String url;

    public UserBook(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_userbook;
    }

    @Override
    public void bindView(BaseAdapter baseAdapter, BaseAdapter.ViewHolder holder, final UserBook data, final int position) {
        final View view = holder.getRootView();
        ImageView iv = view.findViewById(R.id.item_userbook_iv);
        TextView tv = view.findViewById(R.id.item_userbook_tv);
        tv.setText(data.getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), data.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
