package com.example.administrator.myapplication.MyStar;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.reading.BookReadActivity;

public class BlankFragment extends Fragment implements BlankContract.View {

    private BlankViewModel mViewModel;
    private Button mButton;
    public static BlankFragment newInstance() {
        return new BlankFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.blank_fragment, container, false);
        mButton = view.findViewById(R.id.mButtonTest);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BlankViewModel.class);
        // TODO: Use the ViewModel

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),BookReadActivity.class));
            }
        });

    }

    @Override
    public void loadMore() {
        System.out.println(111);
    }
}
