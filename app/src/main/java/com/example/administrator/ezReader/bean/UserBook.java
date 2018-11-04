package com.example.administrator.ezReader.bean;

import java.io.Serializable;

public class UserBook implements Serializable {

    private String name;
    private String url;

    public UserBook(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

}
