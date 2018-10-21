package com.example.administrator.myapplication.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MemoryUtil {
    private static final String TAG = "MemoryUtil";

    public static void saveBook(Context context){
        Log.d(TAG, "saveBook: 进来了");
        String data = "wo shi 你爸爸！";
        FileOutputStream fos = null;
        BufferedWriter bw = null;

        try {
            fos = context.openFileOutput("data",Context.MODE_PRIVATE);
            Log.d(TAG, "saveBook: 获取到文件资源");
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(data);
            Log.d(TAG, "saveBook: 结束！");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null){
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void save() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file = new File("/storage/emulated/0/kgg");
            if (!file.exists()) {
                file.mkdirs();
            } else {
                Log.d(TAG, "save: fuck");
            }
            Log.d(TAG, "save: 1" + Environment.getExternalStorageDirectory().getPath());
            Log.d(TAG, "save: 2" + file.getAbsolutePath());
        }
    }
}
