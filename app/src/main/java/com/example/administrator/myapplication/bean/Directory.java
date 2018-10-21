package com.example.administrator.myapplication.bean;

public class Directory {
    private String title;
    private String url;

    public Directory() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "title: " + title + "  url: " + url;
    }
}
