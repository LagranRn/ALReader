package com.example.administrator.ezReader.bean;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ezReader.R;
import com.example.library.BaseAdapter;
import com.example.library.IEntity;

import java.io.Serializable;

public class HayuBook implements Serializable,IEntity<HayuBook> {
    private String id;
    private String name;

    public HayuBook(String id,String name) {
        this.id = id;
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_hayu_book;
    }

    @Override
    public void bindView(BaseAdapter baseAdapter, BaseAdapter.ViewHolder holder, final HayuBook data, int position) {
        final View view = holder.getRootView();
        TextView textView = view.findViewById(R.id.item_hayu_tv);
        textView.setText(data.getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), data.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
