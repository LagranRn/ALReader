package com.example.administrator.ezReader.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.ezReader.R;
import com.example.administrator.ezReader.bean.HayuBook;
import com.example.library.BaseAdapter;

import java.util.ArrayList;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ha_yu_book, container, false);
        ButterKnife.bind(this,view);
        List<HayuBook> books = new ArrayList<>();
        for (int i = 0; i < 20; i ++){
            HayuBook book = new HayuBook(String.valueOf(i),"第" + i +"本书");
            books.add(book);
        }
        BaseAdapter adapter = new BaseAdapter.Builder()
                .setDataList(books)
                .build();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return  view;
    }

}
