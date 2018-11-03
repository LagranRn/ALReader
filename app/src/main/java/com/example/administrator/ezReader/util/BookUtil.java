package com.example.administrator.ezReader.util;

import com.example.administrator.ezReader.bean.Chapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class BookUtil {

    private static String PATTERN = "第.*?章.*";

    private ArrayList<Chapter> chapterList; // 存放章节
    private ArrayList<String> chapterName; // 存放章节名

    /*
     * 传入书的路径 初始化
     */
    public BookUtil(String bookUrl, String type) {
        // TODO: 2018/10/22 0022  文字编码判断，如果是汉语用gbk，如果是哈文就用unicode， 其他万物都一样
        this(); //调用无参构造函数进行变量初始化
        String charSet = "GBK";
        try {
            InputStreamReader isr = null;
            switch (type) {
                case "0":
                    charSet = "GBK";
                    break;
                case "1":
                    charSet = "Unicode";
                    break;
                default:
            }
            isr = new InputStreamReader(new FileInputStream(new File(bookUrl)), charSet);

            handleBook(isr); // 进行解析

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    /**
     * 构造函数
     *
     * @param isr 传入的输入流
     */
    public BookUtil(InputStreamReader isr) {
        this();
        handleBook(isr);
    }

    private BookUtil() {
        //进行初始化
        chapterList = new ArrayList<>();
        chapterName = new ArrayList<>();
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
            //进行索引合法判断
            return chapterList.get(index);
        return null;
    }

    /**
     * @return 返回所有章节名字的列表
     */
    public List<String> getChapterNames() {
        return chapterName;
    }


    /**
     * @return 返回所有章节
     */
    public ArrayList<Chapter> getChapterList() {
        return chapterList;
    }

    /**
     * 处理小说文件，提取其中的章节内容，章节名称，序言等
     *
     * @param isr 出入的文件输入流
     */
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
                        newChapter.setName("序");
                        chapterName.add("序");
                        sb.delete(0, sb.length());
                        chapterList.add(newChapter);
                        newChapter = new Chapter();
                    }

                    newChapter.setName(temp.substring(temp.indexOf("第")).trim());
                    chapterName.add(temp.substring(temp.indexOf("第")).trim());

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
