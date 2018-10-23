package com.example.administrator.myapplication.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Novel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "MyItemRecyclerViewAdapt";

    private final List<Novel> mNovels;
    private OnListFragmentInteractionListener mListener;


    public MyItemRecyclerViewAdapter(List<Novel> items) {
        mNovels = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.novel = mNovels.get(position);

        holder.author.setText("作者："+mNovels.get(position).getAuthor());
        holder.title.setText("书名："+mNovels.get(position).getName());
        Glide.with(holder.itemView.getContext())
                .load(mNovels.get(position).getCover())
                .into(holder.image);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.novel);

                }
            }
        });
    }

    public void setClickListener(OnListFragmentInteractionListener listener){

        mListener = listener;
    }

    @Override
    public int getItemCount() {
        return mNovels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_author)
        TextView author;
        @BindView(R.id.item_title)
        TextView title;
        @BindView(R.id.item_image)
        ImageView image;

        private View mView;
        private Novel novel;

        private ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this,view);
        }
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Novel novel);
    }
}
