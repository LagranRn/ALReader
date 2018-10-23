package com.example.administrator.myapplication.ui.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.administrator.myapplication.adapter.MyItemRecyclerViewAdapter;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Novel;
import com.example.administrator.myapplication.util.SpiderUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookItemFragment extends Fragment{

    private static final String TAG = "BookItemFragment";
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final int mColumnCount = 1;

    private OnListFragmentInteractionListener mListener;
    private String mUrl;


    @BindView(R.id.book_item_list)
    RecyclerView recyclerView;
    @BindView(R.id.book_item_progressBar)
    ProgressBar progressBar;

    public BookItemFragment() {
    }


    public static BookItemFragment newInstance(String url) {
        BookItemFragment fragment = new BookItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COLUMN_COUNT, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mUrl = getArguments().getString(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        ButterKnife.bind(this,view);

            Context context = view.getContext();
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
             new NovelAsyncTask().execute(mUrl);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    class NovelAsyncTask extends AsyncTask<String,Void,List<Novel>>{

        @Override
        protected List<Novel> doInBackground(String... strings) {
            return SpiderUtil.getNovels(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Novel> novels) {
            super.onPostExecute(novels);
            Log.d(TAG, "onPostExecute: " + novels.size());
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(novels, mListener));
            progressBar.setVisibility(View.INVISIBLE);

        }
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Novel novel);
    }
}
