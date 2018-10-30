package com.example.administrator.ezReader.util;

import android.util.Log;

import com.example.administrator.ezReader.bean.HayuBook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ConnUtil {
    private static final String TAG = "ConnUtil";
    public static String ipAddress = "192.168.43.172";
    public static int port = 8808;

    public static void Conn2Server(){
        try {
            Socket socket = new Socket(ipAddress,port);
            Log.d(TAG, "Conn2Server: 连接成功");
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);

            pw.write("你好");
            pw.flush();

            socket.shutdownOutput();
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            while ((info = br.readLine()) != null){
                Log.d(TAG, "Conn2Server: 服务器：" + info);
            }

            br.close();
            is.close();
            pw.close();
            os.close();
            Log.d(TAG, "Conn2Server: 关闭所有链接！");

        } catch (IOException e) {
            Log.d(TAG, "Conn2Server: 连接失败！");
            e.printStackTrace();
        }
    }

    public static List<HayuBook> sendMsg(){
        try {
            System.out.println("ready to conn...");
            Socket socket = new Socket("132.232.118.114",9999);
            System.out.println("conn success...");
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            System.out.println("send msg..");
            pw.write("00001,\n");
            pw.flush();
            System.out.println("send success..");
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String temp;
            StringBuffer sb = new StringBuffer();
            System.out.println("ready receive...");
            List<HayuBook> books = new ArrayList<>();
            while ((temp = br.readLine())!=null){
                Log.d(TAG, "sendMsg: 333" + temp);
                String[] msg = temp.split(",");
                books.add(new HayuBook(msg[0],msg[1]));
            }
            Log.d(TAG, "sendMsg: the size of book is :" + books.size());
            os.close();
            socket.close();
            isr.close();
            is.close();
            return books;
        } catch (IOException e) {
            System.out.println("conn error");
            e.printStackTrace();
        }
        return null;
    }

}
