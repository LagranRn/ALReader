package com.example.administrator.myapplication.util;

import com.example.administrator.myapplication.bean.Chapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BookUtil {

    private static String PATTERN = "第.*?章.*";

    private ArrayList<Chapter> chapterList; // 章节
    private ArrayList<String> chapterName; // 章节名

    private BookUtil() {
        chapterList = new ArrayList<>();
        chapterName = new ArrayList<String>();
    }

    /*
     * 传入书的路径 初始化
     */
    public BookUtil(String bookUrl) {

        this();
        try {

            InputStreamReader isr = new InputStreamReader(new FileInputStream(new File(bookUrl)));
            handleBook(isr);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public BookUtil(InputStreamReader isr) {

        this();
        handleBook(isr);

    }

    /*
     * 返回章节数
     */
    public int getChapterCount() {
        return chapterName.size();
    }

    /*
     * 根据索引获取章节
     *
     */
    public Chapter getChapter(int index) {
        if (index >= 0 && index < chapterList.size())
            return chapterList.get(index);
        return null;
    }

    /*
     * 获取所有章节
     */
    public List<String> getChapterNames() {
        return chapterName;
    }

    private void handleBook(InputStreamReader isr) {

        try {
            String temp;
            InputStreamReader is = isr;
            BufferedReader br = new BufferedReader(is);
            Chapter newChapter = new Chapter();
            StringBuffer sb = new StringBuffer("");
            // 碰到一个章节头，将名字保存下来，直到碰到书的结尾或者下一章节开始才保存一个章节
            while ((temp = br.readLine()) != null) {

                if (temp.trim().matches(PATTERN)) {

                    if (chapterName.size() != 0) {
                        // 不是第一章
                        newChapter.setContent(sb.toString());
                        sb.delete(0, sb.length());
                        chapterList.add(newChapter);
                        newChapter = new Chapter();
                    } else if (!sb.toString().trim().equals("")) {
                        // 是第一章且序言不是一些空字符
                        newChapter.setContent(sb.toString());
                        newChapter.setName("preface");
                        chapterName.add("preface");
                        sb.delete(0, sb.length());
                        chapterList.add(newChapter);
                        newChapter = new Chapter();
                    }

                    newChapter.setName(temp.substring(temp.indexOf("章") + 1).trim());
                    chapterName.add(temp.substring(temp.indexOf("章") + 1).trim());

                } else {
                    sb.append(temp + "\n");
                }
            }

            newChapter.setContent(sb.toString());
            chapterList.add(newChapter);

            br.close();
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
