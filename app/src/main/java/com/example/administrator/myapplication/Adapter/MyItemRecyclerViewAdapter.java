package com.example.administrator.myapplication.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.ui.fragment.BookItemFragment.OnListFragmentInteractionListener;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Novel;

import java.io.IOException;
import java.net.URL;
import java.util.List;


public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "MyItemRecyclerViewAdapt";
    private final List<Novel> mNovels;
    private final OnListFragmentInteractionListener mListener;
    int i = 0;
    int j = 0;


    ViewHolder holder;


    public MyItemRecyclerViewAdapter(List<Novel> items, OnListFragmentInteractionListener listener) {
        mNovels = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: 1");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        this.holder = holder;
        holder.novel = mNovels.get(position);
        holder.mIdView.setText("作者："+mNovels.get(position).getAuthor());
        holder.mContentView.setText("标题："+mNovels.get(position).getName());
        Glide.with(holder.itemView.getContext())
                .load(mNovels.get(position).getCover())
                .into(holder.mImageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {

                    mListener.onListFragmentInteraction(holder.novel);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNovels.size();
    }

    public class CoverAsyncTask extends AsyncTask<URL,Void,Bitmap>{
        @Override
        protected Bitmap doInBackground(URL... urls) {
            Bitmap pngBM = null;
            try {
                Log.d(TAG, "doInBackground: 正在请求网页？");
                Log.d(TAG, "doInBackground: " + urls[0]);
                pngBM = BitmapFactory.decodeStream(urls[0].openStream());
                Log.d(TAG, "doInBackground: 已经获取到图片");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return pngBM;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            Log.d(TAG, "onPostExecute: 加载图片" + i++);
            holder.mImageView.setImageBitmap(bitmap);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView mImageView;
        public Novel novel;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            mIdView = (TextView) view.findViewById(R.id.item_author);
            mContentView = (TextView) view.findViewById(R.id.item_title);
            mImageView = (ImageView) view.findViewById(R.id.item_image);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

}
