package com.example.administrator.myapplication.ui.activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.myapplication.Adapter.ChapterItemAdapter;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Chapter;
import com.example.administrator.myapplication.util.BookUtil;

import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xfangfang.paperviewlibrary.PaperView;

public class BookReadActivity extends AppCompatActivity {
    private static final String TAG = "BookReadActivity";
    SharedPreferences.Editor stateEditer;
    SharedPreferences stateReader;

    BookUtil bk;
    int current = 0;

    @BindView(R.id.read_pv)
    PaperView pv;
    @BindView(R.id.read_progressBar)
    ProgressBar pb;
    @BindView(R.id.read_ll_menu)
    LinearLayout rl;
    @BindView(R.id.read_tv_pre_chapter)
    TextView last_Chapter;
    @BindView(R.id.read_tv_next_chapter)
    TextView next_Chapter;
    @BindView(R.id.read_tv_category)
    TextView category;
    @BindView(R.id.read_dl_slide)
    DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_read);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);

        initData();

        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    }

    public void initData(){

        new GetBookTask().execute("1本书");

    }


    class GetBookTask extends AsyncTask<String,Void,Boolean> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                bk = new BookUtil(new InputStreamReader(getAssets().open("test.txt")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean b) {
            pb.setVisibility(View.GONE);

            pv.setOnPageStateListener(new PaperView.onPageStateLinstener() {
                @Override
                public boolean getNextChapter() {
                    Log.d(TAG, "getNextChapter: 取下一章节");
                    return false;
                }

                @Override
                public boolean getPreChapter() {

                    Log.d(TAG, "getPreChapter: 取前一章节");
                    return false;
                }

                @Override
                public void isStartPoint() {
                    Log.d(TAG, "isStartPoint: 进入第一页时间");
                }

                @Override
                public void isEndPoint() {
                    Log.d(TAG, "isEndPoint: 到达最后一页时");
                }

                @Override
                public void centerClicked() {

                    if (rl.isShown()){
                        rl.setVisibility(View.INVISIBLE);

                    } else {
                        rl.setVisibility(View.VISIBLE);
                    }

                    Log.d(TAG, "centerClicked: 点击中部时");
                }

                @Override
                public void incChapter() {
                    Log.d(TAG, "incChapter: 增加章节");
                }

                @Override
                public void decChapter() {
                    Log.d(TAG, "decChapter: 减少章节");
                }

                @Override
                public void onEveryPageLoad(int currentPage, int wholePage) {
                    if (currentPage == wholePage){

                    }
                    Log.d(TAG, "onEveryPageLoad: 加载页面时" + currentPage);
                }
            });
            new GetChapterTask().execute(bk.getChapter(current));

            RecyclerView recyslerview = (RecyclerView) findViewById(R.id.rv_read_category);
            recyslerview.setLayoutManager(new LinearLayoutManager(new BookReadActivity()));
            ChapterItemAdapter adapter = new ChapterItemAdapter(bk.getChapterNames());
            recyslerview.setAdapter(adapter);

        }




    }

    class GetChapterTask extends AsyncTask<Chapter, Void, Chapter>{

        Chapter chapter = new Chapter();

        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected Chapter doInBackground(Chapter... chapters) {

            return chapters[0];
        }

        @Override
        protected void onPostExecute(Chapter s) {
            pv.setChapterName("桃源三结义");
            pv.setExtraInfo("第" + (current+1)+ "章 "+s.getName());
            pv.setBackgroundColor(0xC8E6C9);
            pv.setContentTextColor("#002505");
            pv.setInfoTextColor("#8a000000");
            pv.setText(s.getContent());
            pv.setTextLine(17);
            pv.setTextSize(17);

            pb.setVisibility(View.GONE);

        }
    }
    public void incTextSize(View view){
    }
    public void decTextSize(View view){

    }
    public void nextChapter(View view){
        if (current < bk.getChapterCount()){
            new GetChapterTask().execute(bk.getChapter(++current));
        } else {
            Snackbar.make(pv,"走远了",Snackbar.LENGTH_SHORT).show();
        }
    }
    public void lastChapter(View view){
        if (current > 0){
            new GetChapterTask().execute(bk.getChapter(--current));
        } else {
            Snackbar.make(pv,"没了",Snackbar.LENGTH_SHORT).show();
        }
    }

    public void openCategory(View view){
        mDrawerLayout.openDrawer(Gravity.START);
        rl.setVisibility(View.GONE);
    }

}
