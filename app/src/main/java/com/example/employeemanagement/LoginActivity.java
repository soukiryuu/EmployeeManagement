package com.example.employeemanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.employeemanagement.Controller.AsyncAPIRequest;
import com.example.employeemanagement.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Button login_btn;
    private String api_url = "http://150.95.134.62/api/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new AsyncAPIRequest().execute(api_url);

        //ログインをしたら押す
        login_btn = (Button)findViewById(R.id.login_button);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //APIに問合せる
                //同期処理でスピナーだす
//                if ("ログインだったら"){
                //プリファレンスにAPIからもらった会員のIDを一時的に保持する
                sharedPreferences = getSharedPreferences("BEBPreferences", Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("user_id","test");
                editor.commit();

                Intent intent = new Intent(LoginActivity.this,TopActivity.class);
                startActivity(intent);
                finish();
//                }else ("ログインしていなかったら"){
//                    //webviewをリロード
//                }

            }
        });
    }
}
