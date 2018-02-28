package com.example.employeemanagement;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.employeemanagement.Controller.SettingList;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    Log.d("navigation","navigation_home");
                    Intent setting_it = new Intent(SettingActivity.this,CalenderActivity.class);
                    startActivity(setting_it);
                    finish();
                    return true;
                case R.id.navigation_dashboard:
//                    Intent bebuser_it = new Intent(SettingActivity.this,BEBUserActivity.class);
//                    startActivity(bebuser_it);
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    private String title[] = {"パスワード","ログアウト"};
    private String lessons[] = {"・ログインパスワードの設定","・ログアウトします。"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        BottomNavigationView navigationView = (BottomNavigationView)findViewById(R.id.navigation);
        navigationView.setSelectedItemId(R.id.navigation_notifications);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ListView listView = (ListView)findViewById(R.id.list);

        SettingList settingList_1 = new SettingList();
        settingList_1.setTitle(title[0]);
        settingList_1.setLessons(lessons[0]);

        SettingList settingList_2 = new SettingList();
        settingList_2.setTitle(title[1]);
        settingList_2.setLessons(lessons[1]);

        List<SettingList> list = new ArrayList<SettingList>();
        list.add(settingList_1);
        list.add(settingList_2);

        ListAdapter adapter = new ListAdapter(getApplicationContext(),list);

        listView.setAdapter(adapter);
    }

    class ListAdapter extends ArrayAdapter<SettingList> {

        private LayoutInflater mInflater;
        private TextView title_tv,lessons_tv;
        private Button mButton;

        public ListAdapter(Context context, List<SettingList> objects) {
            super(context, 0, objects);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.setting_layout, null);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("click", String.valueOf(position));
                        switch (position) {
                            case 0:
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                            case 4:
                                Intent login_intent = new Intent(SettingActivity.this,LoginActivity.class);
                                login_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(login_intent);
                                finish();
                                break;
                        }
                    }
                });
            }
            final SettingList item = this.getItem(position);
            if(item != null){
                title_tv = (TextView)convertView.findViewById(R.id.list_title);
                title_tv.setText(item.getTitle());
                lessons_tv = (TextView)convertView.findViewById(R.id.lessons);
                lessons_tv.setText(item.getLessons());
            }
            return convertView;

        }

    }
}
