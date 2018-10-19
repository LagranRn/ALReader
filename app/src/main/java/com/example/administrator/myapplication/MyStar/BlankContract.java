package com.example.administrator.myapplication.MyStar;

public interface BlankContract {
    interface View{
        void loadMore();
    }
    interface Presenter{
        boolean canLoad();
    }
}
