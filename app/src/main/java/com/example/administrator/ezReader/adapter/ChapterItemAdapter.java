package com.example.administrator.ezReader.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.ezReader.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChapterItemAdapter extends  RecyclerView.Adapter<ChapterItemAdapter.MyHolder> {
    private static final String TAG = "ChapterItemAdapter";
    private  List<String> chapters;
    private IChapterItemAdapterListener mListener;

    public ChapterItemAdapter(List<String> chapters) {
        this.chapters = chapters;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chapter,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i) {
        myHolder.tv_Chapter.setText(chapters.get(i));
        myHolder.tv_Chapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(i);
            }
        });

        myHolder.tv_Chapter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mListener.onItemLongClick(i);
                return false;
            }
        });
    }

    public void setClickListener(IChapterItemAdapterListener listener){
        mListener = listener;
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.category_tv_chapter)
        TextView tv_Chapter;

        MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }

    public interface IChapterItemAdapterListener {

        //传入章节索引
        void onItemClick(int chapterIndex);
        void onItemLongClick(int chapterIndex);

    }
}