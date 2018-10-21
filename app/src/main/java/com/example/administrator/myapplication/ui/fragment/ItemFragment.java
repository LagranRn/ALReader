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

import com.example.administrator.myapplication.Adapter.MyItemRecyclerViewAdapter;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Constant;
import com.example.administrator.myapplication.bean.Novel;
import com.example.administrator.myapplication.ui.activity.MainActivity;
import com.example.administrator.myapplication.util.SpiderUtil;

import java.util.List;

public class ItemFragment extends Fragment {
    private static final String TAG = "ItemFragment";
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    ProgressBar progressBar;


    public ItemFragment() {
    }


    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_item_list, container, false);
        Log.d(TAG, "onCreateView: 1");
        // Set the adapter

        recyclerView = view.findViewById(R.id.list);
        progressBar = view.findViewById(R.id.item_progressBar);

            Log.d(TAG, "onCreateView: 2");
            Context context = view.getContext();
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            Log.d(TAG, "onCreateView: " + "加载书");
            new NovelAsyncTask().execute();

        Log.d(TAG, "onCreateView: 3");
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

    public class NovelAsyncTask extends AsyncTask<Void,Void,List<Novel>>{
        @Override
        protected List<Novel> doInBackground(Void... voids) {

            Constant.NOVELS = SpiderUtil.getNovels(Constant.FANTACY_URL);

            Log.d(TAG, "doInBackground: " + Constant.NOVELS.size());
            return Constant.NOVELS;
        }

        @Override
        protected void onPostExecute(List<Novel> novels) {
            super.onPostExecute(novels);
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(novels, mListener));
            progressBar.setVisibility(View.INVISIBLE);

        }
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Novel novel);
    }
}
