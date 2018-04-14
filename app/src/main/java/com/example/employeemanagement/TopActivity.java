package com.example.employeemanagement;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.employeemanagement.Controller.CalendarPagerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class TopActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {

    public int[] prev_cal_data, cal_data, next_cal_data;
    public TextView now_month;
    ArrayList<int[]> indexes = new ArrayList<int[]>();
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_frame);

        //現在の日付を取得する
        Calendar now_cal = Calendar.getInstance();

        //現在の月をテキストにセットする
        now_month = (TextView) findViewById(R.id.month_tv);
        now_month.setText(now_cal.get(Calendar.YEAR)+"/"+(now_cal.get(Calendar.MONTH)+1));

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
    protected void onDestroy() {
        super.onDestroy();
        //メモリ解放
        viewPager = null;
        indexes = null;
        prev_cal_data = null;
        next_cal_data = null;
        now_month = null;
        Log.d("TopActivity", "onDestroy");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        Log.d("onPageScrolled", String.valueOf(position));
    }

    @Override
    public void onPageSelected(int position) {
        ViewPager viewPager = (ViewPager)findViewById(R.id.calendar_pager);
        CalendarPagerAdapter adapter = (CalendarPagerAdapter) viewPager.getAdapter();
        indexes = null;
        indexes = adapter.getAll();
        now_month.setText(indexes.get(position)[0]+"/"+(indexes.get(position)[1]+1));

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
}
