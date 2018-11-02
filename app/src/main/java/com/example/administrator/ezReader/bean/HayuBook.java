package com.example.administrator.ezReader.bean;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ezReader.R;
import com.example.administrator.ezReader.ui.activity.BookReadActivity;
import com.example.administrator.ezReader.util.ConnUtil;
import com.example.library.BaseAdapter;
import com.example.library.IEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class HayuBook implements Serializable,IEntity<HayuBook> {
    private static final String TAG = "HayuBook";
    private String id;
    private String name;
    private String content;
    View v ;

    public HayuBook(String id,String name) {
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

                Bundle bundle = new Bundle();
                bundle.putSerializable("data",data);
                Intent intent = new Intent(view.getContext(),BookReadActivity.class);
                intent.putExtra("bookType","2");
                intent.putExtra("bundle",bundle);
                view.getContext().startActivity(intent);

//                Toast.makeText(view.getContext(), data.getContent(), Toast.LENGTH_SHORT).show();

                /*try {
                    File file = new File(view.getContext().getExternalFilesDir(null).getAbsolutePath() + "/"+data.getName()+".txt");
                    Log.d(TAG, "onClick: 文件路径：" + file.getAbsolutePath());
                    FileOutputStream fos = new FileOutputStream(file);
                    for (int i = 0; i < data.getName().toCharArray().length; i ++){
                        fos.write(data.getName().toCharArray()[i]);
                    }
                    fos.close();
                    Log.d(TAG, "onClick: 文件写入结束");
                } catch (FileNotFoundException e) {
                    Log.d(TAG, "onClick: 文件创建异常");
                    e.printStackTrace();
                } catch (IOException e){
                    Log.d(TAG, "onClick: 文件写入异常");
                    e.printStackTrace();
                }*/
            }
        });
    }


}
