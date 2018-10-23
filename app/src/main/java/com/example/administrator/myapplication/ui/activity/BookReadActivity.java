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

import com.example.administrator.myapplication.adapter.ChapterItemAdapter;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Chapter;
import com.example.administrator.myapplication.bean.Directory;
import com.example.administrator.myapplication.bean.Novel;
import com.example.administrator.myapplication.util.BookUtil;
import com.example.administrator.myapplication.util.SpiderUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xfangfang.paperviewlibrary.PaperView;

public class BookReadActivity extends AppCompatActivity {

    private static final String TAG = "BookReadActivity";

    SharedPreferences.Editor stateEditer;
    SharedPreferences stateReader;

    int current = 0;// 当前所在章节
    String bookType = "1"; //0,在线；1本地
    int textSize = 18; //14
    int textLine = 21;

    List<Directory> directories = new ArrayList<>();
    ArrayList<Chapter> chapterList = new ArrayList<>();
    List<String> chapterNames = new ArrayList<>();

    Animation menuShowAnim;
    Animation menuHideAnim;

    Novel novel = new Novel();
    URL book ;

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
        setContentView(R.layout.activity_read);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);

        initView();
        initData();

    }

    public void initView(){

        menuHideAnim = AnimationUtils.loadAnimation(this, R.anim.menu_hide);
        menuShowAnim = AnimationUtils.loadAnimation(this, R.anim.menu_show);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    }
    public void initData(){

        bookType = getIntent()
                .getStringExtra("bookType");

        if (bookType.equals("1")){
            String bookUrl = getIntent()
                    .getStringExtra("bookUrl");
            try {
                book = new URL(bookUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        } else {

            novel = (Novel) getIntent()
                    .getBundleExtra("novel")
                    .getSerializable("novel");
        }

        new GetBookTask().execute();
    }




    class GetBookTask extends AsyncTask<String,Void,Boolean> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(String... strings) {
            switch (bookType){

                case "0":
                    directories = SpiderUtil.getDirectory(novel.getUrl());
                    break;
                case "1":
                    Log.d(TAG, "doInBackground: 获取到url");
                    BookUtil bk = new BookUtil(getIntent()
                            .getStringExtra("bookUrl"));
                    chapterNames = bk.getChapterNames();
                    chapterList = bk.getChapterList();

                    Log.d(TAG, "doInBackground: chapterNames" + chapterNames.size());
                    Log.d(TAG, "doInBackground: chapterList" + chapterList.size());

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

            switch (bookType) {
                case "0":
                    new GetChapterTask().execute(directories.get(current).getUrl());
                    for (int i = 0; i < directories.size(); i ++){
                        chapterNames.add(directories.get(i).getTitle());
                    }
                    break;
                case "1":
                    new GetChapterTask().execute("offline");
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
                        case "0":
                            current = chapterIndex;
                            new GetChapterTask().execute(directories.get(chapterIndex).getUrl());
                            break;
                        case "1":
                            current = chapterIndex;
                            new GetChapterTask().execute();
                            break;
                            default:
                    }

                    mDrawerLayout.closeDrawer(Gravity.START);
                }

                @Override
                public void onItemLongClick(int chapterIndex) {
                    Log.d(TAG, "onItemLongClick: 长按");
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
                case "0":
                    chapter.setName(directories.get(current).getTitle());
                    chapter.setContent(SpiderUtil.getChapterContent(strings[0]));
                    break;
                case "1":
                    novel.setName("kgg");
                    Log.d(TAG, "doInBackground: " + chapterList.size());
                    chapter = chapterList.get(current);
                    break;
                    default:
            }

            return chapter;
        }


        @Override
        protected void onPostExecute(Chapter s) {

            pv.setExtraInfo(s.getName());
            pv.setText(s.getContent());
            pv.setChapterName(novel.getName());

            pv.setTextLine(textLine);
            pv.setTextSize(textSize);

            pv.setContentTextColor("#002505");
            pv.setInfoTextColor("#8a000000");

            pb.setVisibility(View.GONE);

        }
    }
    public void incTextSize(View view){
        if (textSize  <  22){
            textSize ++;
            textSize ++;
            textLine --;
            textLine --;
            new GetChapterTask().execute(directories.get(current).getUrl());

        } else {
            Snackbar.make(pv,"到最大了",Snackbar.LENGTH_SHORT).show();

        }

    }
    public void decTextSize(View view){
        if (textSize > 14){
            textSize --;
            textSize --;
            textLine ++;
            textLine ++;
            new GetChapterTask().execute(directories.get(current).getUrl());
        } else {
            Snackbar.make(pv,"到最小了",Snackbar.LENGTH_SHORT).show();

        }

    }


    public void nextChapter(View view){
        nextC();
    }
    public void lastChapter(View view){
        lastC();
    }

    public void nextC(){
        switch (bookType){
            case "0":
                if (current < directories.size() - 1){
                    current++;
                    new GetChapterTask().execute(directories.get(current).getUrl());
                } else {
                    Snackbar.make(pv,"走远了",Snackbar.LENGTH_SHORT).show();
                }
                break;
            case "1":
                if (current < chapterList.size() - 1){
                    current++;
                    new GetChapterTask().execute();
                } else {
                    Snackbar.make(pv,"走远了",Snackbar.LENGTH_SHORT).show();
                }
                break;
            default:
        }

    }
    public void lastC(){
        switch (bookType){
            case "0":
                if (current > 0){
                    current--;
                    new GetChapterTask().execute(directories.get(current).getUrl());
                } else {
                    Snackbar.make(pv,"没了",Snackbar.LENGTH_SHORT).show();
                }
                break;
            case "1":
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
