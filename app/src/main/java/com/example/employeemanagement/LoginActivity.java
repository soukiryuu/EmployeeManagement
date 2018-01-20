package com.example.employeemanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.employeemanagement.R;

public class LoginActivity extends AppCompatActivity {

    private WebView webView;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //ログインは統合管理システムのサイトに任せたい
        //ログイン後はボタンを押してもらってAPIにログイン状況の問合せをする
        //ログインのレスポンスが返却されればカレンダーに遷移
        webView = (WebView)findViewById(R.id.login_webview);
        //basic認証のダイアログが出ないので上手く作るしかない？←嫌じゃ
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedHttpAuthRequest(WebView view,
                                                  HttpAuthHandler handler, String host, String realm) {
                handler.proceed("blueinc", "blue1234");
            }
        });
        webView.loadUrl("http://163.44.174.191/ic_v2/login");

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

                Intent intent = new Intent(LoginActivity.this,CalenderActivity.class);
                startActivity(intent);
                finish();
//                }else ("ログインしていなかったら"){
//                    //webviewをリロード
//                }

            }
        });
    }
}
