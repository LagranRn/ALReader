package com.example.administrator.myapplication.util;


public class Novel {
    private String name;  // 小说名字
    private String url; //小说地址  用来查询小说章节
    private String author; // 作者
    private String cover; // 封面

    public Novel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        String tail = url.substring(url.indexOf('_') + 1).replace("/", "s.jpg");
        cover = url.replace("tw/", "tw/files/article/image/").replace('_', '/') + tail;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public String getCover() {
        return cover;
    }

    @Override
    public String toString() {
        return "name: " + name + " author: " + author + " url: " + url + " cover: " + cover;
    }

}