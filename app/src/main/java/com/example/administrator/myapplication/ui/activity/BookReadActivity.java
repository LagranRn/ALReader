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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.myapplication.Adapter.ChapterItemAdapter;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Chapter;
import com.example.administrator.myapplication.bean.Directory;
import com.example.administrator.myapplication.util.BookUtil;
import com.example.administrator.myapplication.util.SpiderUtil;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xfangfang.paperviewlibrary.PaperView;

public class BookReadActivity extends AppCompatActivity {

    private static final String TAG = "BookReadActivity";
    SharedPreferences.Editor stateEditer;
    SharedPreferences stateReader;

    BookUtil bk;
    int current = 0;

    String url;
    int bookType = 1; //0,在线；1本地
    List<Directory> directories = new ArrayList<>();

    Chapter chapter = new Chapter();
    Animation menuShowAnim;
    Animation menuHideAnim;

    String titleName;
    String content;
    String titleUrl;

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

        url = getIntent().getStringExtra("bookurl");
        initData();

        Log.d(TAG, "onCreate: " + url);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    }

    public void initData(){
        if (url != null ){
            Log.d(TAG, "initData: online");
            bookType = 0;

            new GetBookTask().execute("1本书");

        } else {
            Log.d(TAG, "initData: offline");

            new GetBookTask().execute("1本书");

        }
        menuHideAnim = AnimationUtils.loadAnimation(this, R.anim.menu_hide);
        menuShowAnim = AnimationUtils.loadAnimation(this, R.anim.menu_show);

    }

    class GetOnlineTasl extends AsyncTask<Void,Void,Boolean>{
        @Override
        protected Boolean doInBackground(Void... voids) {

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

        }
    }

    class GetBookTask extends AsyncTask<String,Void,Boolean> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(String... strings) {
            switch (bookType){

                case 0:
                    directories = SpiderUtil.getDirectory(url);
                    titleUrl = directories.get(current).getUrl();
                    titleName = directories.get(current).getTitle();


                    break;

                case 1:

                    try {
                        bk = new BookUtil(new InputStreamReader(getAssets().open("test.txt")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                    default:

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
                        rl.startAnimation(menuHideAnim);
                    } else {
                        rl.setVisibility(View.VISIBLE);
                        rl.startAnimation(menuShowAnim);
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



            List<String> chapterNames = new ArrayList<>();
            switch (bookType) {
                case 0:
                    new GetChapterTask().execute(titleUrl);
                    for (int i = 0; i < directories.size(); i ++){
                        chapterNames.add(directories.get(i).getTitle());
                    }
                    break;
                case 1:
                    new GetChapterTask().execute("offline");
                    chapterNames = bk.getChapterNames();
                    break;
                    default:
            }


            RecyclerView recyslerview = (RecyclerView) findViewById(R.id.rv_read_category);
            recyslerview.setLayoutManager(new LinearLayoutManager(new BookReadActivity()));
            ChapterItemAdapter adapter = new ChapterItemAdapter(chapterNames);
            adapter.setClickListener(new ChapterItemAdapter.IChapterItemAdapterListener() {
                @Override
                public void onItemClick(int chapterIndex) {
                    switch (bookType) {
                        case 0:
                            titleUrl = directories.get(chapterIndex).getUrl();
                            current = chapterIndex;
                            new GetChapterTask().execute(titleUrl);
                            break;
                        case 1:
                            current = chapterIndex;
                            new GetChapterTask().execute();
                            break;
                            default:
                    }

                    mDrawerLayout.closeDrawer(Gravity.START);
                    // TODO 添加 处理事件
                    Log.d(TAG, "onItemClick: fff");
                }

                @Override
                public void onItemLongClick(int chapterIndex) {
                    // TODO 添加 处理事件
                    Log.d(TAG, "onItemLongClick: fff");
                }
            });
            recyslerview.setAdapter(adapter);

        }

    }

    class GetChapterTask extends AsyncTask<String, Void, Chapter>{

        Chapter chapter = new Chapter();

        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected Chapter doInBackground(String... strings) {
            switch (bookType){
                case 0:
                    content = SpiderUtil.getChapterContent(titleUrl);
                    chapter.setName(titleName);
                    chapter.setContent(content);
                    break;
                case 1:
                    chapter = bk.getChapter(current);
                    break;
                    default:
            }

            return chapter;
        }


        @Override
        protected void onPostExecute(Chapter s) {
            pv.setChapterName("桃源三结义");
            pv.setExtraInfo("第" + (current+1)+ "章 "+s.getName());
//            pv.setBackgroundColor(0xC8E6C9);
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
        switch (bookType){
            case 0:
                if (current < directories.size()){
                    current++;
                    titleUrl = directories.get(current).getUrl();
                    new GetChapterTask().execute(titleUrl);
                }
                break;
            case 1:
                if (current < bk.getChapterCount()){
                    current++;
                    new GetChapterTask().execute();
                } else {
                    Snackbar.make(pv,"走远了",Snackbar.LENGTH_SHORT).show();
                }
                break;
                default:
        }

    }
    public void lastChapter(View view){
        Log.d(TAG, "lastChapter: 点击");
        switch (bookType){
            case 0:
                if (current > 0){
                    current--;
                    titleUrl = directories.get(current).getUrl();
                    new GetChapterTask().execute(titleUrl);
                }
                break;
            case 1:
                if (current > 0){
                    current--;
                    new GetChapterTask().execute();
                } else {
                    Snackbar.make(pv,"没了",Snackbar.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    public void openCategory(View view){
        mDrawerLayout.openDrawer(Gravity.START);
        rl.setVisibility(View.GONE);
        rl.startAnimation(menuHideAnim);
    }

}
