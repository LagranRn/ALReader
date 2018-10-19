package com.example.administrator.myapplication.MyStar;

public class BlankPresenter implements BlankContract.Presenter {
    private BlankContract.View view;
    public BlankPresenter(BlankContract.View view) {
        this.view = view;

    }

    @Override
    public boolean canLoad() {
        return false;
    }
}
