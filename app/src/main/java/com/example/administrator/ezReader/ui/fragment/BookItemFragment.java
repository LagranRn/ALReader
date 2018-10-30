package com.example.administrator.ezReader.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.administrator.ezReader.adapter.MyItemRecyclerViewAdapter;
import com.example.administrator.ezReader.R;
import com.example.administrator.ezReader.bean.Novel;
import com.example.administrator.ezReader.ui.activity.BookDetailActivity;
import com.example.administrator.ezReader.util.SpiderUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookItemFragment extends Fragment{

    private static final String TAG = "BookItemFragment";
    private static final String ARG_COLUMN_COUNT = "column-count";

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

        new NovelAsyncTask().execute(mUrl);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
            progressBar.setVisibility(View.INVISIBLE);
            MyItemRecyclerViewAdapter adapter = new MyItemRecyclerViewAdapter(novels);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            adapter.setClickListener(new MyItemRecyclerViewAdapter.OnListFragmentInteractionListener() {
                @Override
                public void onListFragmentInteraction(Novel novel) {
                    Intent intent = new Intent(getContext(),BookDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("novel",novel);
                    intent.putExtra(BookDetailFragment.ARG_ITEM_ID,bundle);
                    startActivity(intent);
                }
            });
        }
    }
}
