package com.example.administrator.ezReader.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.administrator.ezReader.R;
import com.example.administrator.ezReader.util.ConnUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    String account;
    String password;

    @BindView(R.id.login_sign_in)
    Button signIn;
    @BindView(R.id.login_account)
    EditText et_account;
    @BindView(R.id.login_password)
    EditText et_password;
    @BindView(R.id.login_progressBar)
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        signIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_sign_in:
                login();
                break;

        }
    }

    public void login(){
        account = et_account.getText().toString();
        password = et_password.getText().toString();
        new LoginAsyncTask().execute();

    }

    class LoginAsyncTask extends AsyncTask<Void,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {

            return ConnUtil.login(account,password);
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.GONE);
            switch (s){
                case "1":
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case "2":
                    Toast.makeText(LoginActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
                    break;
                case "3":
                    Toast.makeText(LoginActivity.this, "无此用户！", Toast.LENGTH_SHORT).show();
                    break;
                    default:
                        Toast.makeText(LoginActivity.this, "连接失败！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
