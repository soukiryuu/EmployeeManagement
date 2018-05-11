package com.example.employeemanagement;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.employeemanagement.Controller.CalendarPagerAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class TopActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    public int[] prev_cal_data, cal_data, next_cal_data;
    public TextView now_month, year_month;
    ArrayList<int[]> indexes = new ArrayList<int[]>();
    private ViewPager viewPager;
    private static final String[] sample = {"勤怠： XX:XX ~ XX:XX", "交通費： 〇〇 ~ ×× XXX円"};
    //日別の編集時の場合はfalse、一括選択時はtrue
    public boolean day_click_flg = false;
    //一括設定用の選択日付データ配列
    private ArrayList<String> selectDate = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //現在の日付を取得する
        Calendar now_cal = Calendar.getInstance();

        //現在の月をテキストにセットする
//        now_month = (TextView) findViewById(R.id.month_tv);
//        now_month.setText(now_cal.get(Calendar.YEAR)+"/"+(now_cal.get(Calendar.MONTH)+1));

        ActionBar actionBar = getSupportActionBar();
        // 通常表示されるタイトルを非表示にする。
        actionBar.setDisplayShowTitleEnabled(false);
        // 独自のビューを表示するように設定。
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM);
        // 独自のビューを指定。 (ここではレイアウトリソースの ID を指定しているが、View オブジェクトを渡すこともできる。)
        actionBar.setCustomView(R.layout.toolbar_layout);
        // 独自のActionBar設定後にその子ビューの内容を変更する
        year_month = (TextView) findViewById(R.id.year_month_tv);
        year_month.setText(now_cal.get(Calendar.YEAR)+"/"+(now_cal.get(Calendar.MONTH)+1));

        //ViewPagerにセットするデータを用意する
        CalendarPagerAdapter adapter = new CalendarPagerAdapter(this);
        adapter.addAll(setCalendar(now_cal.get(Calendar.YEAR), now_cal.get(Calendar.MONTH)));

        viewPager = (ViewPager) findViewById(R.id.calendar_pager);
        Log.d("TopActivity", "setAdapter");
        viewPager.setAdapter(adapter);
        Log.d("TopActivity", "setCurrentItem");
        viewPager.setOffscreenPageLimit(1);
        viewPager.setCurrentItem((indexes.size()/2)-1);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //カレンダーの高さを6週分の高さに抑えるための処理
        LinearLayout month_layout = (LinearLayout) findViewById(R.id.weekline_frame);
        int height = month_layout.getMeasuredHeight() * 6;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        viewPager.setLayoutParams(layoutParams);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sample_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ViewPager viewPager = (ViewPager)findViewById(R.id.calendar_pager);
        CalendarPagerAdapter adapter = (CalendarPagerAdapter) viewPager.getAdapter();
        indexes = null;
        indexes = adapter.getAll();
//        now_month.setText(indexes.get(position)[0]+"/"+(indexes.get(position)[1]+1));
        year_month.setText(indexes.get(position)[0]+"/"+(indexes.get(position)[1]+1));

        Log.d("onPageSelected", String.valueOf(indexes.get(position)));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            Log.d("onPageScrollStateChanged","スクロール");
            ViewPager viewPager = (ViewPager)findViewById(R.id.calendar_pager);
            CalendarPagerAdapter adapter = (CalendarPagerAdapter) viewPager.getAdapter();

            indexes = null;
            indexes = adapter.getAll();
            Log.d("onPageScrollStateChanged", String.valueOf(indexes));

            int currentPage = viewPager.getCurrentItem();
            Log.d("onPageScrollStateChanged:currentPage", String.valueOf(currentPage));
            if( currentPage != 0 && currentPage != indexes.size() - 1){
                //最初でも最後のページでもない場合処理を抜ける
                Log.d("onPageScrollStateChanged","途中のページ");
                return;
            }

            int next_page = 0;
            if(currentPage == 0){
                Log.d("onPageScrollStateChanged","最初のページ");
                //最初のページに到達
                next_page = 1;
//                adapter.destroyAllItem(viewPager);
                indexes.add(0,setPrevCalendar(indexes.get(0)[0], indexes.get(0)[1]));
                adapter.addAll(indexes);
                adapter.notifyDataSetChanged();
            }else if(currentPage == indexes.size() - 1){
                Log.d("onPageScrollStateChanged","最後のページ");
                //最後のページに到達
                next_page = currentPage;
//                adapter.destroyAllItem(viewPager);
                indexes.add(setNextCalendar(indexes.get(next_page)[0], indexes.get(next_page)[1]));
                adapter.addAll(indexes);
                adapter.notifyDataSetChanged();
            }

            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(1);
            viewPager.setCurrentItem(next_page);
        }

    }

    //2年分のデータを用意するメソッド
    public ArrayList<int[]> setCalendar(int year, int month){

        Log.d("setCalendar", "year:"+year+"month:"+month);

        int now_year = year;
        int now_month = month;

        now_year = now_year - 1;
        for (int i=0; i<25; i++) {
            prev_cal_data = new int[3];
            //前月の値をセット
            if (now_month == 11){ //前年になる場合
                prev_cal_data[0] = now_year + 1; //年
                prev_cal_data[1] = 0; //月
                prev_cal_data[2] = 1; //日3
            }else {
                prev_cal_data[0] = now_year; //年
                prev_cal_data[1] = now_month + 1; //月
                prev_cal_data[2] = 1; //日
            }
            indexes.add(prev_cal_data);

            now_year = prev_cal_data[0];
            now_month = prev_cal_data[1];
        }

        return indexes;
    }

    public int[] setNextCalendar(int year, int month){

        next_cal_data = new int[3];
        //次月の値をセット
        if (month == 11){ //次年になる場合
            Log.d("setCalendar:next_cal_data","次年");
            next_cal_data[0] = year + 1; //年
            next_cal_data[1] = 0; //月
            next_cal_data[2] = 1; //日
        }else {
            Log.d("setCalendar:next_cal_data","次月");
            next_cal_data[0] = year; //年
            next_cal_data[1] = month + 1; //月
            next_cal_data[2] = 1; //日
        }
        ;
        Log.d("setCalendar:next_cal_data", next_cal_data[0]+"/"+next_cal_data[1]);

        return next_cal_data;
    }

    public int[] setPrevCalendar(int year, int month){

        prev_cal_data = new int[3];
        //前月の値をセット
        if (month == 0){ //前年になる場合
            Log.d("setCalendar:prev_cal_data","前年");
            prev_cal_data[0] = year - 1; //年
            prev_cal_data[1] = 11; //月
            prev_cal_data[2] = 1; //日
        }else {
            Log.d("setCalendar:prev_cal_data","前月");
            prev_cal_data[0] = year; //年
            prev_cal_data[1] = month - 1; //月
            prev_cal_data[2] = 1; //日
        }

        Log.d("setCalendar:prev_cal_data", prev_cal_data[0]+"/"+prev_cal_data[1]+1);

        return prev_cal_data;
    }

    //日付がタップされた時のリスナー
    public void onDayClick(View view) {
        //タップされた日付のTextViewインスタンス生成
        TextView day_tv = (TextView) view.findViewById(R.id.day_tv);
        //選択前のflg状態の判断
        if (day_click_flg) {
            Log.d("SampleMainActivity", "day_click_flg:"+"true");
            //背景色で選択状態かどうか判断するために取得する
            ColorDrawable colorDrawable = (ColorDrawable) view.getBackground();
            try {
                //背景色をintに変換
                int colorInt = colorDrawable.getColor();
                Log.d("SampleMainActivity", "colorInt:"+colorInt);
                if (colorInt == Color.GRAY) { //背景色が選択色の時（選択状態）
                    //リストから削除する
                    for (int sort=0; sort<selectDate.size(); sort++){
                        if (selectDate.get(sort) == day_tv.getText()) {
                            selectDate.remove(sort);
                        }else {
                            continue;
                        }
                    }
                    view.setBackgroundColor(Color.TRANSPARENT);
                }else { //未選択の時
                    //選択した日付けをリストに格納
                    selectDate.add(day_tv.getText().toString());
                    //背景色設定
                    view.setBackgroundColor(Color.GRAY);
                }
            }catch (NullPointerException npe) { //背景色がnullで来た場合
                Log.d("SampleMainActivity", "NullPointerException");
                selectDate.add(day_tv.getText().toString());
                view.setBackgroundColor(Color.GRAY);
            }
        }else {
            Log.d("SampleMainActivity", "day_click_flg:"+"false");
            ListView listView = (ListView) findViewById(R.id.day_list);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sample);
            listView.setAdapter(arrayAdapter);
        }
    }

    //一括設定で選択されたデータを返す
    public ArrayList<String> getSelectDate() {
        return selectDate;
    }
}
