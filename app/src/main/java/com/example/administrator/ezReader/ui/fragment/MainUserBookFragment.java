package com.example.administrator.ezReader.ui.fragment;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.ezReader.R;
import com.example.administrator.ezReader.adapter.UserBookRecyclerViewAdapter;
import com.example.administrator.ezReader.bean.Constant;
import com.example.administrator.ezReader.bean.UserBook;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainUserBookFragment extends Fragment {


    @BindView(R.id.main_my_book_fragment_rv)
    RecyclerView recyclerView;


    public MainUserBookFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MainUserBookFragment newInstance() {
        MainUserBookFragment fragment = new MainUserBookFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_my_book, container, false);
        ButterKnife.bind(this, view);

        if (Constant.NOWUSER == 1){
            new GetUserBookTask().execute();
        } else {
            Toast.makeText(getContext(), "请先登录！", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    class GetUserBookTask extends AsyncTask<Void,Void,List<UserBook>>{
        @Override
        protected List<UserBook> doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(List<UserBook> userBooks) {
            super.onPostExecute(userBooks);
            List<UserBook> books = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                UserBook userBook = new UserBook("第" + String.valueOf(i));
                books.add(userBook);
            }

            UserBookRecyclerViewAdapter adapter = new UserBookRecyclerViewAdapter(books);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            recyclerView.setAdapter(adapter);

        }
    }
}
