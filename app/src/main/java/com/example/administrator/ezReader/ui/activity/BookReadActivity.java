package com.example.administrator.ezReader.ui.activity;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.administrator.ezReader.adapter.ChapterRecyclerViewAdapter;
import com.example.administrator.ezReader.R;
import com.example.administrator.ezReader.bean.Chapter;
import com.example.administrator.ezReader.bean.Directory;
import com.example.administrator.ezReader.bean.HayuBook;
import com.example.administrator.ezReader.bean.Novel;
import com.example.administrator.ezReader.util.BookUtil;
import com.example.administrator.ezReader.util.ConnUtil;
import com.example.administrator.ezReader.util.SpiderUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xfangfang.paperviewlibrary.PaperView;

public class BookReadActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "BookReadActivity";

    SharedPreferences.Editor stateEditer;
    SharedPreferences stateReader;

    int current = 0;// 当前所在章节
    String bookType = "1"; //0,在线；1本地
    String localType = "1";

    int textSize = 18; //14
    int textLine = 21;
    int chapterState = -1;

    HayuBook hybook;
    Novel novel = new Novel();

    List<Directory> directories = new ArrayList<>();
    ArrayList<Chapter> chapterList = new ArrayList<>();
    List<String> chapterNames = new ArrayList<>();

    Animation menuShowAnim;
    Animation menuHideAnim;


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
    @BindView(R.id.read_tv_add2book)
    TextView add2book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);

        initView();
        initData();


    }

    public void initView() {

        menuHideAnim = AnimationUtils.loadAnimation(this, R.anim.menu_hide);
        menuShowAnim = AnimationUtils.loadAnimation(this, R.anim.menu_show);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        add2book.setOnClickListener(this);
        category.setOnClickListener(this);

    }

    public void initData() {

        Intent intent = getIntent();
        bookType = intent.getStringExtra("bookType");
        Log.d(TAG, "initData: type 为" + bookType);


        switch (bookType) {
            case "0"://在线网络小说
                Log.d(TAG, "initData: " + bookType);
                novel = (Novel) intent
                        .getBundleExtra("novel")
                        .getSerializable("novel");
                new GetBookTask().execute();
                break;
            case "1"://本地图书
                localType = intent.getStringExtra("localType");
                new GetBookTask().execute();
                break;
            case "2"://在线哈语图书
                Toast.makeText(this, "获取到哈语图书！", Toast.LENGTH_SHORT).show();
                hybook = (HayuBook) intent.getBundleExtra("bundle").getSerializable("data");
                new GetBookTask().execute();
                break;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.read_tv_add2book:
                Toast.makeText(this, "已经加入到书架！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.read_tv_category:
                mDrawerLayout.openDrawer(Gravity.START);
                rl.setVisibility(View.GONE);
                rl.startAnimation(menuHideAnim);
                break;
        }
    }

    class GetBookTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            switch (bookType) {
                case "0"://在线小说
                    directories = SpiderUtil.getDirectory(novel.getUrl());
                    break;
                case "1"://本地小说
                    BookUtil bk = new BookUtil(getIntent()
                            .getStringExtra("bookUrl"), localType);
                    chapterNames = bk.getChapterNames();
                    chapterList = bk.getChapterList();
                    break;
                case "2"://在线哈语
                    String content = ConnUtil.getHayuContent(hybook.getId());
                    hybook.setContent(content);
                    break;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean b) {
            pb.setVisibility(View.GONE);
            pv.setOnPageStateListener(new PaperView.onPageStateLinstener() {
                @Override
                public boolean getNextChapter() {
                    return true;
                }

                @Override
                public boolean getPreChapter() {
                    return true;
                }

                @Override
                public void isStartPoint() {
                }

                @Override
                public void isEndPoint() {
                }

                @Override
                public void centerClicked() {

                    if (rl.isShown()) {
                        rl.setVisibility(View.INVISIBLE);
                        rl.startAnimation(menuHideAnim);
                    } else {
                        rl.setVisibility(View.VISIBLE);
                        rl.startAnimation(menuShowAnim);
                    }

                }

                @Override
                public void incChapter() {
                    //滑动增加章节
                    chapterState *= -1;
                    if (chapterState == 1) {
                        nextC();
                    }
                }

                @Override
                public void decChapter() {
                    //滑动减少章节
                    chapterState *= -1;
                    if (chapterState == 1) {
                        lastC();
                    }
                }

                @Override
                public void onEveryPageLoad(int currentPage, int wholePage) {
                    /*if (rl.isShown()){
                        rl.setVisibility(View.INVISIBLE);
                        rl.startAnimation(menuHideAnim);
                    }*/
                    Log.d(TAG, "onEveryPageLoad: 当前页面" + currentPage);
                }
            });

            initClickView();

        }

        private void initClickView() {

            switch (bookType) {
                case "0"://在线网络小说
                    new GetChapterTask().execute(directories.get(current).getUrl());
                    for (int i = 0; i < directories.size(); i++) {
                        chapterNames.add(directories.get(i).getTitle());
                    }
                    break;
                case "1"://本地小说
                    new GetChapterTask().execute();
                    break;
                case "2"://在线哈语小说
                    chapterNames.add("无目录");
                    new GetChapterTask().execute();
                    break;
            }

            RecyclerView recyclerView = findViewById(R.id.read_category);

            recyclerView.setLayoutManager(new LinearLayoutManager(new BookReadActivity()));
            ChapterRecyclerViewAdapter adapter = new ChapterRecyclerViewAdapter(chapterNames);

            adapter.setClickListener(new ChapterRecyclerViewAdapter.IChapterItemAdapterListener() {
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
            recyclerView.setAdapter(adapter);

        }
    }

    class GetChapterTask extends AsyncTask<String, Void, Chapter> {

        Chapter chapter = new Chapter();

        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected Chapter doInBackground(String... strings) {
            switch (bookType) {
                case "0":
                    chapter.setName(directories.get(current).getTitle());
                    chapter.setContent(SpiderUtil.getChapterContent(strings[0]));
                    break;
                case "1":
                    String bookUrl = getIntent()
                            .getStringExtra("bookUrl");
                    String name = bookUrl.split("/")[bookUrl.split("/").length - 1].split("\\.")[0];
                    novel.setName(name);
                    chapter = chapterList.get(current);
                    break;
                case "2":
                    Chapter c = new Chapter();
                    c.setName(hybook.getName());
                    c.setContent(hybook.getContent());
                    chapter = c;
                    break;
                default:
            }
            Log.d(TAG, "doInBackground: 返回的chapter: name" + chapter.getName());
            Log.d(TAG, "doInBackground: content" + chapter.getContent());
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

    public void incTextSize(View view) {
        if (textSize < 22) {
            textSize++;
            textSize++;
            textLine--;
            textLine--;
            new GetChapterTask().execute(directories.get(current).getUrl());

        } else {
            Snackbar.make(pv, "再大就装不下了~", Snackbar.LENGTH_SHORT).show();

        }

    }

    public void decTextSize(View view) {
        if (textSize > 14) {
            textSize--;
            textSize--;
            textLine++;
            textLine++;
            new GetChapterTask().execute(directories.get(current).getUrl());
        } else {
            Snackbar.make(pv, "再小就看不见了~", Snackbar.LENGTH_SHORT).show();

        }

    }


    public void nextChapter(View view) {
        nextC();
    }

    public void lastChapter(View view) {
        lastC();
    }

    public void nextC() {
        switch (bookType) {
            case "0":
                if (current < directories.size() - 1) {
                    current++;
                    new GetChapterTask().execute(directories.get(current).getUrl());
                } else {
                    Snackbar.make(pv, "欢迎来到世界的尽头~", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case "1":
                if (current < chapterList.size() - 1) {
                    current++;
                    new GetChapterTask().execute();
                } else {
                    Snackbar.make(pv, "欢迎来到世界的尽头~", Snackbar.LENGTH_SHORT).show();
                }
                break;
            default:
        }

    }

    public void lastC() {
        switch (bookType) {
            case "0":
                if (current > 0) {
                    current--;
                    new GetChapterTask().execute(directories.get(current).getUrl());
                } else {
                    Snackbar.make(pv, "欢迎来到世界的起点~", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case "1":
                if (current > 0) {
                    current--;
                    new GetChapterTask().execute();
                } else {
                    Snackbar.make(pv, "欢迎来到世界的起点~", Snackbar.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }


}
