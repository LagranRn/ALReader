package com.example.administrator.ezReader.bean;


import java.io.Serializable;

public class Novel implements Serializable {
    private static final String TAG = "Novel";
    private String name;  // 小说名字
    private String url; //小说地址  用来查询小说章节
    private String author; // 作者
    private String cover; // 封面
//    private Bitmap coverBitmap;

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

//        Log.d(TAG, "setUrl: 正在加载图片" + cover);
//        Bitmap pngBM = null;
//        try {
//            pngBM = BitmapFactory.decodeStream(new URL(cover).openStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        coverBitmap = pngBM;
//        Log.d(TAG, "setUrl: 加载完成" + cover);
//        Log.d(TAG, "setUrl: 图片大小 高" + coverBitmap.getHeight() + "宽" +coverBitmap.getWidth());
//

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

//    public Bitmap getCoverBitmap() {
//        return coverBitmap;
//    }

    @Override
    public String toString() {
        return "name: " + name + " author: " + author + " url: " + url + " cover: " + cover;
    }

}