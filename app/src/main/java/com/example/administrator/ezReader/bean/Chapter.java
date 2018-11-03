package com.example.administrator.ezReader.bean;
//本地图书筛选出来的章节
public class Chapter {

    public String name;  // 章节名字  如果是序言，章节名则为 preface
    public String content; // 每一章的内容
    private String url;

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setName(String name) {
        this.name = name;
    }
}
