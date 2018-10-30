package com.example.administrator.ezReader.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

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

}
