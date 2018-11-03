package com.example.administrator.ezReader.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.ezReader.R;
import com.example.administrator.ezReader.bean.UserBook;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserBookRecyclerViewAdapter extends RecyclerView.Adapter<UserBookRecyclerViewAdapter.MyViewHolder> {
    List<UserBook> data;

    public UserBookRecyclerViewAdapter(List<UserBook> books) {
        data = books;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_userbook, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
//        Glide.with(myViewHolder.itemView.getContext())
//                .load(R.drawable.)
        myViewHolder.mTextView.setText(data.get(i).getName());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(myViewHolder.itemView.getContext(), data.get(i).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_userbook_iv)
        ImageView mImageView;
        @BindView(R.id.item_userbook_tv)
        TextView mTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
