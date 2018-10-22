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
import android.widget.TextView;

import com.example.administrator.myapplication.Adapter.MyItemRecyclerViewAdapter;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Constant;
import com.example.administrator.myapplication.bean.Novel;
import com.example.administrator.myapplication.util.SpiderUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookItemFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "BookItemFragment";
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.item_progressBar)
    ProgressBar progressBar;
    @BindView(R.id.list_china)
    TextView china;
    @BindView(R.id.list_city)
    TextView city;
    @BindView(R.id.list_fantacy)
    TextView fantacy;
    @BindView(R.id.list_history)
    TextView history;
    @BindView(R.id.list_onlineGame)
    TextView onlineGame;

    public BookItemFragment() {
    }


    public static BookItemFragment newInstance(int columnCount) {
        BookItemFragment fragment = new BookItemFragment();
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
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        ButterKnife.bind(this,view);

            Context context = view.getContext();
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
             new NovelAsyncTask().execute(Constant.CHINA_URL);
            china.setOnClickListener(this);
            fantacy.setOnClickListener(this);
            city.setOnClickListener(this);
            history.setOnClickListener(this);
            onlineGame.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        switch (v.getId()){
            case R.id.list_china:
                Log.d(TAG, "onClick: china" );
                new NovelAsyncTask().execute(Constant.CHINA_URL);
                break;
            case R.id.list_city:
                Log.d(TAG, "onClick: city");
                new NovelAsyncTask().execute(Constant.CITY_URL);
                break;
            case R.id.list_fantacy:
                Log.d(TAG, "onClick: fantacy");
                new NovelAsyncTask().execute(Constant.FANTACY_URL);
                break;
            case R.id.list_history:
                new NovelAsyncTask().execute(Constant.HISTORY_URL);
                break;
            case R.id.list_onlineGame:
                new NovelAsyncTask().execute(Constant.ONLINEGAME_URL);
                break;
        }
    }

    public class NovelAsyncTask extends AsyncTask<String,Void,List<Novel>>{

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
