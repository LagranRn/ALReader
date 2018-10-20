package com.example.administrator.myapplication.util;

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

public class Spider {

    private static final String NOVEL_REG = "class=\"s2\"><a href=\"(.*?)\">(.*?)<.*?\">(.*?)<";
    private static final String CHAPTER_REG = "id=\"content\">(.*?)</div>";
    private static final String DIR_REG = "<dd><a href=\"(.*?)\">(.*?)</a>";

    // 获取小说
    public static List<Novel> getNovels(String url) {
        List<Novel> novels = new ArrayList<>(); // 存放小说
        List<String> names = new ArrayList<>(); // 存放小说名，防止重复

        try {
            BufferedReader reader = null;
            URL resultUrl = new URL(url);
            URLConnection conn = resultUrl.openConnection();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = null;
            StringBuilder sb = new StringBuilder();
            // 获取网页
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            Pattern pattern = Pattern.compile(NOVEL_REG);
            Matcher matcher = pattern.matcher(sb.toString());

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

            if (reader != null) {
                reader.close();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return novels;
    }

    // 获取所有 章节
    public static List<Directory> getDirectory(String bookUrl) {
        List<Directory> dirs = new ArrayList<>();

        try {
            BufferedReader reader = null;
            URL resultUrl = new URL(bookUrl);
            URLConnection conn = resultUrl.openConnection();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = null;
            StringBuilder sb = new StringBuilder();
            // 获取网页
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            Pattern pattern = Pattern.compile(DIR_REG);
            Matcher matcher = pattern.matcher(sb.toString());

            while (matcher.find()) {
                Directory newDir = new Directory();
                newDir.setTitle(matcher.group(2));
                newDir.setUrl(Constant.BIQUGE + matcher.group(1));
                dirs.add(newDir);
            }

            if (reader != null) {
                reader.close();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dirs;
    }

    // 获取每一章的内容
    public static String getChapterContent(String chapterUrl) {

        String content = "";

        try {
            BufferedReader reader = null;
            URL resultUrl = new URL(chapterUrl);
            URLConnection conn = resultUrl.openConnection();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = null;
            StringBuilder sb = new StringBuilder();
            // 获取网页
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            // 获取内容
            content = sb.toString()
                    .substring(sb.indexOf("id=\"content\">") + "id=\"content\">".length(),
                            sb.indexOf("</div>", sb.indexOf("id=\"content\">")))
                    .replace("&nbsp;", " ").replace("<br />", "");

            if (reader != null) {
                reader.close();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

}

