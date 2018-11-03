package com.example.administrator.ezReader.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.ezReader.R;
import com.example.administrator.ezReader.bean.HayuBook;
import com.example.administrator.ezReader.ui.activity.BookReadActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HayuBookRecyclerViewAdapter extends RecyclerView.Adapter<HayuBookRecyclerViewAdapter.MyViewHolder> {

    List<HayuBook> data;

    public HayuBookRecyclerViewAdapter(List<HayuBook> books) {
        data = books;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_book, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.mTextView.setText(data.get(i).getName());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("data", data.get(i));
                Intent intent = new Intent(myViewHolder.itemView.getContext(), BookReadActivity.class);
                intent.putExtra("bookType", "2");
                intent.putExtra("bundle", bundle);
                myViewHolder.itemView.getContext().startActivity(intent);

//                Toast.makeText(view.getContext(), data.getContent(), Toast.LENGTH_SHORT).show();

                /*try {
                    File file = new File(view.getContext().getExternalFilesDir(null).getAbsolutePath() + "/"+data.getName()+".txt");
                    Log.d(TAG, "onClick: 文件路径：" + file.getAbsolutePath());
                    FileOutputStream fos = new FileOutputStream(file);
                    for (int i = 0; i < data.getName().toCharArray().length; i ++){
                        fos.write(data.getName().toCharArray()[i]);
                    }
                    fos.close();
                    Log.d(TAG, "onClick: 文件写入结束");
                } catch (FileNotFoundException e) {
                    Log.d(TAG, "onClick: 文件创建异常");
                    e.printStackTrace();
                } catch (IOException e){
                    Log.d(TAG, "onClick: 文件写入异常");
                    e.printStackTrace();
                }*/
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_hayu_tv)
        TextView mTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
