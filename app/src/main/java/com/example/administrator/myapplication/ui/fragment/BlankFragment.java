package com.example.administrator.myapplication.ui.fragment;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.net.Uri;

import com.example.administrator.myapplication.ui.viewmodel.BlankViewModel;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.ui.activity.BookReadActivity;
import com.example.administrator.myapplication.util.ConnUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BlankFragment extends Fragment {


    private static final String TAG = "BlankFragment";
    private BlankViewModel mViewModel;
    private Button mButton;
    private Button mFileTest;
    private ConnUtil connUtil;
    private static final int FILE_SELECT_CODE = 0;
    private ImageView iv;

    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public static BlankFragment newInstance() {
        return new BlankFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        mButton = view.findViewById(R.id.mButtonTest);
        mFileTest = view.findViewById(R.id.blank_btn_fileTest);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BlankViewModel.class);

        // TODO: Use the ViewModel


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();

            }
        });

        mFileTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = context.getExternalFilesDir(null).getPath() + "/kong1337.txt";
                File file = new File(path);
                try {
                    if (!file.exists()) {
                        file.createNewFile();
                        FileOutputStream fos = new FileOutputStream(path);
                        fos.write("你好".getBytes());
                        fos.flush();
                        fos.close();

                        Log.d(TAG, "onClick: 创建成功！");
                    } else {
                        FileOutputStream fos = new FileOutputStream(path);
                        fos.write("你好".getBytes());
                        fos.flush();
                        fos.close();
                        Log.d(TAG, "onClick: you wen jian ");
                        Toast.makeText(context, "有文件了", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }



    private void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "选择文件"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "亲，木有文件管理器啊-_-!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode ==Activity.RESULT_OK&&requestCode==FILE_SELECT_CODE){
            Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
            String string =uri.toString();
            File file;
            String a[]=new String[2];
            //判断文件是否在sd卡中
            String path = new String ();
            if (string.indexOf(String.valueOf(Environment.getExternalStorageDirectory()))!=-1){
                //对Uri进行切割
                a = string.split(String.valueOf(Environment.getExternalStorageDirectory()));
                //获取到file
                file = new File(Environment.getExternalStorageDirectory(),a[1]);
                path = file.getAbsolutePath();
            }else if(string.indexOf(String.valueOf(Environment.getDataDirectory()))!=-1){ //判断文件是否在手机内存中
                //对Uri进行切割
                a =string.split(String.valueOf(Environment.getDataDirectory()));
                //获取到file
                file = new File(Environment.getDataDirectory(),a[1]);
                path = file.getAbsolutePath();
            }else{
                //出现其他没有考虑到的情况
                Log.d(TAG, "onActivityResult: 绝对路径" + getRealPathFromURI(data.getData()));
                path = getRealPathFromURI(data.getData());
                Toast.makeText(context, "获取到绝对路径", Toast.LENGTH_SHORT).show();
            }
            Log.d(TAG, "onActivityResult: " + path);

            Intent intent = new Intent(context,BookReadActivity.class);
            intent.putExtra("bookType","1");
            intent.putExtra("bookUrl",path);
            startActivity(intent);
        }

    }
    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if(null!=cursor&&cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }

    public void jueduilujing(){

        /*Intent intent = new Intent(context,BookReadActivity.class);
                intent.putExtra("bookType","1");
                intent.putExtra("bookUrl",context.getExternalFilesDir(null).getPath() + "/kong1337.txt");
                startActivity(intent);*/

    }
}
