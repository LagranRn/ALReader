package com.example.administrator.myapplication.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

import java.util.List;

public class ChapterItemAdapter extends  RecyclerView.Adapter<ChapterItemAdapter.MyHolder> {

    private  List<String> chapters;
    public ChapterItemAdapter(List<String> chapters) {
        this.chapters = chapters;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chapter,viewGroup,false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        myHolder.tv_Chapter.setText("第"+(i+1)+"章 "+chapters.get(i));
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        public TextView tv_Chapter;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_Chapter = (TextView) itemView.findViewById(R.id.category_tv_chapter);
        }
    }
}
