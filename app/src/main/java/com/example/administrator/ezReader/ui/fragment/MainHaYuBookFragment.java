package com.example.administrator.ezReader.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.ezReader.R;
import com.example.administrator.ezReader.bean.HayuBook;
import com.example.administrator.ezReader.util.ConnUtil;
import com.example.library.BaseAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainHaYuBookFragment extends Fragment {

    @BindView(R.id.main_ha_yu_book_fragment_rv)
    RecyclerView recyclerView;
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
        ButterKnife.bind(this,view);
        new GetHaYuBook().execute();
        return  view;
    }

    class GetHaYuBook extends AsyncTask<Void,Void,List<HayuBook>>{

        @Override
        protected List<HayuBook> doInBackground(Void... voids) {
           return ConnUtil.sendMsg();
        }

        @Override
        protected void onPostExecute(List<HayuBook> hayuBooks) {
            List<HayuBook> books = hayuBooks;
            BaseAdapter adapter = new BaseAdapter.Builder()
                    .setDataList(books)
                    .build();
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
        }

    }
}
