package com.example.employeemanagement.Controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.employeemanagement.PagerFragment;
import com.example.employeemanagement.R;
import com.example.employeemanagement.SecondFragment;
import com.example.employeemanagement.ThirdFragment;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by watanabehiroaki on 2018/03/09.
 */

public class CalendarPagerAdapter extends PagerAdapter {

    private LayoutInflater _inflater;
    private ArrayList<int[]> mIndexes = new ArrayList<int[]>();
    private static final int MAX_PAGE = 25;
    private static final int WEEK_7 = 7;
    private static final int MAX_WEEK = 6;
    public Fragment fragment;

    public int beginningWeek = Calendar.SUNDAY;
    public View month_view,weekline_view,day_view;
    private ArrayList<LinearLayout> mWeeks = new ArrayList<LinearLayout>();

    public CalendarPagerAdapter(Context context) {
        super();
        _inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

//    @Override
//    public Fragment getItem(int position) {
//
//        Log.d("getItem:position", String.valueOf(position));
//        Bundle bundle = new Bundle();
//        bundle.putInt("INDEX", mIndexes.get(position));
//        bundle.putInt("MONTH", mIndexes.get(position)[1]);
//        bundle.putInt("DAY", mIndexes.get(position)[2]);
//        Log.d("getItem:data", String.valueOf(mIndexes.get(position)));
//        PagerFragment fragment = new PagerFragment();
//        fragment.setArguments(bundle);

//        Fragment fragment = null;
//        int diff = (position - (MAX_PAGE_NUM / 2)) % OBJECT_NUM;
//        Log.d("getItem:diff", String.valueOf(diff));
//        int index = (0 > diff) ? (OBJECT_NUM + diff) : diff;
//        switch(index) {
//            case 0:
//                Log.d("getItem:index", String.valueOf(index));
//                fragment = new PagerFragment();
//                break;
//            case 1:
//                Log.d("getItem:index", String.valueOf(index));
//                fragment = new SecondFragment();
//                break;
//            case 2:
//                Log.d("getItem:index", String.valueOf(index));
//                fragment = new ThirdFragment();
//                break;
//        }

//        Bundle bundle = new Bundle();
//        bundle.putInt("YEAR", mIndexes.get(position)[0]);
//        bundle.putInt("MONTH", mIndexes.get(position)[1]);
//        bundle.putInt("DAY", mIndexes.get(position)[2]);
//        Log.d("getItem:data", String.valueOf(mIndexes.get(position)));
//        PagerFragment fragment = new PagerFragment();
//        fragment.setArguments(bundle);
//        Log.d("getItem", String.valueOf(fragment));

//        return fragment;
//    }

    @Override
    public int getCount() {
        return mIndexes.size();
//        return  MAX_PAGE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d("CalendarPagerAdapter", "instantiateItem");
//        month_view = _inflater.inflate(R.layout.activity_sample,container,false);
//        TextView textView = (TextView) month_view.findViewById(R.id.sample_tv);
//        textView.setText("index:"+mIndexes.get(position));

        Calendar cal = Calendar.getInstance();
        cal.clear(); // まずクリアしてからセットしないといけない
        Log.d("CalendarPagerAdapter",
                String.valueOf(mIndexes.get(position)[0]+"/"+(mIndexes.get(position)[1]+1)+"/"+mIndexes.get(position)[2]));
        cal.set(Calendar.YEAR, mIndexes.get(position)[0]);
        cal.set(Calendar.MONTH, mIndexes.get(position)[1]);
        cal.set(Calendar.DAY_OF_MONTH, mIndexes.get(position)[2]);

        // カレンダーの最初の空白の個数を求める。
        int skipCount; // 空白の個数
        int firstDayMonthWeek = cal.get(Calendar.DAY_OF_WEEK); // 1日の曜日
        if (beginningWeek > firstDayMonthWeek) {
            skipCount = firstDayMonthWeek - beginningWeek + WEEK_7;
        } else {
            skipCount = firstDayMonthWeek - beginningWeek;
        }
        int lastDay = cal.getActualMaximum(Calendar.DATE); // 月の最終日

        month_view = null;
        month_view = _inflater.inflate(R.layout.calendar_month_frame, container,false);
        // 日付を生成する。
        int dayCounter = 1;
        for (int i = 0; i < MAX_WEEK; i++) {
            weekline_view = null;
            weekline_view = _inflater.inflate(R.layout.calendar_week_frame, container, false);
            mWeeks.add((LinearLayout) weekline_view);
            for (int j = 0; j < WEEK_7; j++) {
                day_view = null;
                day_view = _inflater.inflate(R.layout.calendar_day_frame, container,false);
                TextView day_tv = (TextView) day_view.findViewById(R.id.day_tv);
                ImageView icon_img_1 = (ImageView) day_view.findViewById(R.id.icon_1);
                ImageView icon_img_2 = (ImageView) day_view.findViewById(R.id.icon_2);
                ImageView icon_img_3 = (ImageView) day_view.findViewById(R.id.icon_3);
                LinearLayout borderLine = (LinearLayout) day_view.findViewById(R.id.border_line_layout);

                if (i == 0 && skipCount > 0) {
                    //日付けの開始曜日でなければ空白文字をセット
                    day_tv.setText(" ");
                    icon_img_1.setBackground(null);
                    icon_img_2.setBackground(null);
                    icon_img_3.setBackground(null);
                    skipCount--;
                } else if (dayCounter <= lastDay) {
                    //1ヶ月分の日付けをセットしていく
                    day_tv.setText(String.valueOf(dayCounter));

                    dayCounter++;
                } else {
                    //最後の日以降は空白文字をセット
                    day_tv.setText(" ");
                    icon_img_1.setBackground(null);
                    icon_img_2.setBackground(null);
                    icon_img_3.setBackground(null);
                    borderLine.setBackground(null);
                }

                //動的にレイアウト設定しないと等間隔にならない・・・なぜ・・・
                LinearLayout.LayoutParams llp =
                        new LinearLayout.LayoutParams(
                                0, LinearLayout.LayoutParams.WRAP_CONTENT);
                llp.weight = 1;
                ((LinearLayout) weekline_view).addView(day_view,llp);
            }
            ((LinearLayout) month_view).addView(weekline_view);
        }

        container.addView(month_view);
        return month_view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public int getItemPosition(Object object) {
        Log.d("CalendarPagerAdapter", "getItemPosition");
        return POSITION_NONE;
    }

    public void destroyAllItem(ViewPager pager) {
        for (int i = 0; i < getCount(); i++) {
            Log.d("destroyAllItem", String.valueOf(getCount()));
            Log.d("destroyAllItem:i", String.valueOf(i));
            try {
                Object obj = this.instantiateItem(pager, i);
                if (obj != null){
                    destroyItem(pager, i, obj);
                }
            } catch (Exception e) {
                Log.d("error",e.toString());
            }
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        Log.d("CalendarPagerAdapter","destroyItem:position"+position);
        container.removeView((View) object);
//        container.removeAllViews();

//        if (position <= getCount()) {
//            FragmentManager manager = ((Fragment) object).getFragmentManager();
//            FragmentTransaction trans = manager.beginTransaction();
//            trans.remove((Fragment) object);
//            trans.commit();
//        }
    }

    public void addAll(ArrayList<int[]> indexes) {
        Log.d("addAll", String.valueOf(indexes));
        mIndexes = indexes;
        Log.d("addAll", String.valueOf(mIndexes));
//        notifyDataSetChanged();
    }

    public ArrayList<int[]> getAll() {
        return mIndexes;
    }
}
