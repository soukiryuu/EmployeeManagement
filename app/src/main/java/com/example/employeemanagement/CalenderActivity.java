package com.example.employeemanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.employeemanagement.Controller.CalenderController;
import com.example.employeemanagement.Helper.DBHelper;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CalenderActivity extends AppCompatActivity {

    private Button p_btn,n_btn;
    private TextView cl_header_tv,total_time;
    public CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("yyyy年MM月カレンダー", Locale.getDefault());
    private SimpleDateFormat dateFormatForDay = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM");
    private DBHelper dbHelper = null;
    private String user_id = "test0003";
    private SharedPreferences sharedPreferences;
    private CalenderController calenderController;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    Intent bebuser_it = new Intent(CalenderActivity.this,TopActivity.class);
                    startActivity(bebuser_it);
                    return true;
                case R.id.navigation_notifications:
                    Intent setting_it = new Intent(CalenderActivity.this,SettingActivity.class);
                    setting_it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(setting_it);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Date curDate = Calendar.getInstance().getTime();

        //カレンダーライブラリのインスタンス生成
        compactCalendarView = (CompactCalendarView)findViewById(R.id.compactcalendar_view);
        compactCalendarView.setCurrentDate(curDate);
        compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        compactCalendarView.setLocale(TimeZone.getDefault(), Locale.JAPAN);
        compactCalendarView.shouldDrawIndicatorsBelowSelectedDays(true);
        compactCalendarView.setEventIndicatorStyle(CompactCalendarView.FILL_LARGE_INDICATOR);

        cl_header_tv = (TextView)findViewById(R.id.cal_month_now_tv);
        cl_header_tv.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        Log.d("日付",dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        //カレンダーにイベントの登録
//        Calendar cal = Calendar.getInstance();
//        cal.set(2017, 9, 10, 0, 0, 0);
//        Event event = new Event(
//                Color.BLUE,                  // 表示する予定の色
//                cal.getTimeInMillis(), // 表示する日時
//                "テストだよ〜"
//        );
//        compactCalendarView.addEvent(event,false);

        //ここでAPIに問い合わせて表示している月のデータをもらう

        //前月ボタンインスタンス
        p_btn = (Button)findViewById(R.id.prev_month_btn);
        //クリックリスナーの登録
        p_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //カレンダーを一月前に移動する
                compactCalendarView.showPreviousMonth();
            }
        });

        //次月ボタンインスタンス
        n_btn = (Button)findViewById(R.id.next_month_btn);
        //クリックリスナーの登録
        n_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //カレンダーを一月後に移動する
                compactCalendarView.showNextMonth();
            }
        });

        //カレンダーのリスナー登録
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            //日付がクリックされた時
            @Override
            public void onDayClick(Date dateClicked) {
                Log.d("onDayClick",dateClicked.toString());
                //その日に登録されているイベント取得
                List<Event> eventList = compactCalendarView.getEvents(dateClicked);
                //Event型に置き換える
                if (eventList.size() != 0) {
                    Event event1 = eventList.get(0);
                    Log.d("Event",event1.getData().toString());
                }

                Intent it = new Intent(CalenderActivity.this,ManagementDetailActivity.class);
                it.putExtra("day",dateFormatForDay.format(dateClicked));
                it.putExtra("day2",dateFormat.format(dateClicked));
                it.putExtra("day3",dateFormat2.format(dateClicked));
                startActivity(it);
            }

            //カレンダーが横スクロールされた時
            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                cl_header_tv.setText(dateFormatForMonth.format(firstDayOfNewMonth));
                Log.d("onMonthScroll",firstDayOfNewMonth.toString());
                //ここでAPIに問い合わせて表示している月のデータをもらう
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //コントローラーのAPI呼ぶメソッド
//    public ...

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("onPause","onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("onRestart","onRestart");

        //アクセストークンの有効確認？

        BottomNavigationView navigationView = (BottomNavigationView)findViewById(R.id.navigation);
        navigationView.setSelectedItemId(R.id.navigation_home);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_btn, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addButton:
                // ボタンをタップした際の処理を記述
                //文字列作製処理

                break;
        }
        return true;
    }

}
