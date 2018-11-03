package com.example.administrator.ezReader.util;

import com.example.administrator.ezReader.bean.Constant;
import com.example.administrator.ezReader.bean.Directory;
import com.example.administrator.ezReader.bean.Novel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpiderUtil {

    // 正则表达式定义 分别是获取小说、小说章节、小说详情
    private static final String NOVEL_REG = "class=\"s2\"><a href=\"(.*?)\">(.*?)<.*?\">(.*?)<";
    private static final String DIR_REG = "<dd><a href=\"(.*?)\">(.*?)</a>";
    private static final String DETAIL_REG = "title\" content=\"(.*?)\"" + ".*?" + "description\" content=\"(.*?)\"/>"
            + ".*?" + "image\" content=\"(.*?)\"/>" + ".*?" + "author\" content=\"(.*?)\"/>" + ".*?"
            + "status\" content=\"(.*?)\"/>" + ".*?" + "update_time\" content=\"(.*?)\"/>" + ".*?"
            + "latest_chapter_name\" content=\"(.*?)\"/>";

    /**
     * 获取网页页面内的所有小说信息
     *
     * @param url 网页网址，具体看Constant类中的定义
     * @return 返回小说列表
     */
    public static List<Novel> getNovels(String url) {
        List<Novel> novels = new ArrayList<>(); // 存放小说
        List<String> names = new ArrayList<>(); // 存放小说名，防止重复

        // 创建Java里的正则表达式对象
        Pattern pattern = Pattern.compile(NOVEL_REG);
        Matcher matcher = pattern.matcher(getHtml(url, "\n"));

        while (matcher.find()) {
            if (names.indexOf(matcher.group(2)) == -1) {
                Novel newNovel = new Novel();
                newNovel.setAuthor(matcher.group(3));
                newNovel.setName(matcher.group(2));
                newNovel.setUrl(matcher.group(1));
                novels.add(newNovel);
                names.add(newNovel.getName());
            }
        }
        return novels;
    }


    /**
     * 获取所有 章节 小说一些详情
     *
     * @param bookUrl 小说详情页网址
     * @return 小说所有章节的列表
     */
    public static List<Directory> getDirectory(String bookUrl) {
        List<Directory> dirs = new ArrayList<>();

        Pattern pattern = Pattern.compile(DIR_REG);
        Matcher matcher = pattern.matcher(getHtml(bookUrl, "\n"));

        while (matcher.find()) {
            Directory newDir = new Directory();
            newDir.setTitle(matcher.group(2));
            newDir.setUrl(Constant.BIQUGE + matcher.group(1));
            dirs.add(newDir);
        }
        return dirs;
    }


    /**
     * 获取每一章的内容
     *
     * @param chapterUrl 传入每一章的网址
     * @return 返回该章书的内容
     */
    public static String getChapterContent(String chapterUrl) {

        String html = getHtml(chapterUrl, "\n");

        // 从网页代码中提取小说内容并将一些其他字符进行替代
        String content = html.toString()
                .substring(html.indexOf("id=\"content\">") + "id=\"content\">".length(),
                        html.indexOf("</div>", html.indexOf("id=\"content\">")))
                .replace("&nbsp;", " ").replace("<br />", "");

        return content;
    }


    /**
     * 依次返回小说的书名、简介、封面地址、作者、状态（更新、停更等）、最后更新时间、最新章节
     *
     * @param bookUrl 书的网页地址
     * @return
     */
    public static List<String> getNovelDetails(String bookUrl) {
        List<String> details = new ArrayList<String>();

        String html = getHtml(bookUrl, "");

        Pattern pattern = Pattern.compile(DETAIL_REG);
        Matcher matcher = pattern.matcher(html);

        if (matcher.find()) {
            details.add(matcher.group(1));
            details.add(matcher.group(2));
            details.add(matcher.group(3));
            details.add(matcher.group(4));
            details.add(matcher.group(5));
            details.add(matcher.group(6));
            details.add(matcher.group(7));
        }
        return details;
    }


    /**
     * @param url  网址
     * @param kind 换行符或者空 （考虑到有些网页换行之后不利于正则表达式处理）
     * @return 放回网页的代码
     */
    private static String getHtml(String url, String kind) {

        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = null;
            URL resultUrl = new URL(url);
            URLConnection conn = resultUrl.openConnection();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK"));

            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line + kind);
            }

            if (reader != null) {
                reader.close();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}

