package com.example.administrator.ezReader.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.administrator.ezReader.R;
import com.example.administrator.ezReader.adapter.HayuBookRecyclerViewAdapter;
import com.example.administrator.ezReader.bean.HayuBook;
import com.example.administrator.ezReader.util.ConnUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainHaYuBookFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.main_ha_yu_book_fragment_rv)
    RecyclerView recyclerView;
    @BindView(R.id.main_ha_yu_book_fragment_progressBar)
    ProgressBar progressBar;
    @BindView(R.id.main_ha_yu_book_fragment_search)
    Button search;

    public MainHaYuBookFragment() {
        // Required empty public constructor
    }

    public static MainHaYuBookFragment newInstance() {
        MainHaYuBookFragment fragment = new MainHaYuBookFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ha_yu_book, container, false);
        ButterKnife.bind(this, view);
        search.setOnClickListener(this);
        new GetHaYuBook().execute();
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_ha_yu_book_fragment_search:
                Toast.makeText(getContext(), "进入搜索", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    class GetHaYuBook extends AsyncTask<Void, Void, List<HayuBook>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<HayuBook> doInBackground(Void... voids) {
            return ConnUtil.sendMsg();
        }

        @Override
        protected void onPostExecute(List<HayuBook> hayuBooks) {
            progressBar.setVisibility(View.GONE);
            if (hayuBooks == null){
                Toast.makeText(getContext(), "连接服务器失败！", Toast.LENGTH_SHORT).show();
                return;
            }
            search.setVisibility(View.VISIBLE);
            List<HayuBook> books = hayuBooks;
            HayuBookRecyclerViewAdapter adapter = new HayuBookRecyclerViewAdapter(books);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
        }

    }
}
