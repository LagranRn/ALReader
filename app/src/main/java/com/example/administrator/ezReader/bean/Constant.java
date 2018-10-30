package com.example.administrator.ezReader.bean;

import java.util.ArrayList;
import java.util.List;

public class Constant {

    public static List<String> BOOKURLS = new ArrayList<>();
    public static String BIQUGE = "http://www.biquge.com.tw";
    static {
        BOOKURLS.add("http://www.biquge.com.tw/nvsheng/");
        BOOKURLS.add("http://www.biquge.com.tw/xiuzhen/");
        BOOKURLS.add("http://www.biquge.com.tw/dushi/");
        BOOKURLS.add("http://www.biquge.com.tw/xuanhuan/");
        BOOKURLS.add("http://www.biquge.com.tw/lishi/");
        BOOKURLS.add("http://www.biquge.com.tw/wangyou/");
        BOOKURLS.add("http://www.biquge.com.tw/kehuan/");
    }
}
