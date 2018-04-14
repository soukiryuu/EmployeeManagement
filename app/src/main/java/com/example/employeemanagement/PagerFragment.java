package com.example.employeemanagement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by watanabehiroaki on 2018/03/09.
 */

public class PagerFragment extends Fragment {

    private static final int WEEK_7 = 7;
    private static final int MAX_WEEK = 6;
    public int beginningWeek = Calendar.SUNDAY;
    public View month_view,weekline_view,day_view;
    private ArrayList<LinearLayout> mWeeks = new ArrayList<LinearLayout>();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("PagerFragment", "onCreateView");
//
        Bundle bundle = getArguments();
        int index = bundle.getInt("INDEX");

        month_view = inflater.inflate(R.layout.activity_sample, container, false);
        TextView textView = (TextView) month_view.findViewById(R.id.sample_tv);
        textView.setText("index:"+index);

//        Bundle bundle = getArguments();
//
//        Calendar cal = Calendar.getInstance();
//        cal.clear(); // まずクリアしてからセットしないといけない
//        Log.d("PagerFragment",
//                String.valueOf(bundle.getInt("YEAR")+"/"+(bundle.getInt("MONTH")+1)+"/"+bundle.getInt("DAY")));
//        cal.set(Calendar.YEAR, bundle.getInt("YEAR"));
//        cal.set(Calendar.MONTH, bundle.getInt("MONTH"));
//        cal.set(Calendar.DAY_OF_MONTH, bundle.getInt("DAY"));
//
//        // カレンダーの最初の空白の個数を求める。
//        int skipCount; // 空白の個数
//        int firstDayMonthWeek = cal.get(Calendar.DAY_OF_WEEK); // 1日の曜日
//        if (beginningWeek > firstDayMonthWeek) {
//            skipCount = firstDayMonthWeek - beginningWeek + WEEK_7;
//        } else {
//            skipCount = firstDayMonthWeek - beginningWeek;
//        }
//        int lastDay = cal.getActualMaximum(Calendar.DATE); // 月の最終日
//
//        month_view = inflater.inflate(R.layout.calendar_month_frame, container,false);
//        // 日付を生成する。
//        int dayCounter = 1;
//        for (int i = 0; i < MAX_WEEK; i++) {
//            weekline_view = inflater.inflate(R.layout.calendar_week_frame, container, false);
//            mWeeks.add((LinearLayout) weekline_view);
//            for (int j = 0; j < WEEK_7; j++) {
//                day_view = inflater.inflate(R.layout.calendar_day_frame, container,false);
//                TextView day_tv = (TextView) day_view.findViewById(R.id.day_tv);
////                ImageView icon_img_1 = (ImageView) day_view.findViewById(R.id.icon_1);
////                ImageView icon_img_2 = (ImageView) day_view.findViewById(R.id.icon_2);
////                ImageView icon_img_3 = (ImageView) day_view.findViewById(R.id.icon_3);
//                LinearLayout borderLine = (LinearLayout) day_view.findViewById(R.id.border_line_layout);
//
//                if (i == 0 && skipCount > 0) {
//                    //日付けの開始曜日でなければ空白文字をセット
//                    day_tv.setText(" ");
////                    icon_img_1.setBackground(null);
////                    icon_img_2.setBackground(null);
////                    icon_img_3.setBackground(null);
//                    skipCount--;
//                } else if (dayCounter <= lastDay) {
//                    //1ヶ月分の日付けをセットしていく
//                    day_tv.setText(String.valueOf(dayCounter));
//
//                    dayCounter++;
//                } else {
//                    //最後の日以降は空白文字をセット
//                    day_tv.setText(" ");
////                    icon_img_1.setBackground(null);
////                    icon_img_2.setBackground(null);
////                    icon_img_3.setBackground(null);
//                    borderLine.setBackground(null);
//                }
//
//                //動的にレイアウト設定しないと等間隔にならない・・・なぜ・・・
//                LinearLayout.LayoutParams llp =
//                        new LinearLayout.LayoutParams(
//                                0, LinearLayout.LayoutParams.WRAP_CONTENT);
//                llp.weight = 1;
//                ((LinearLayout) weekline_view).addView(day_view,llp);
//            }
//            ((LinearLayout) month_view).addView(weekline_view);
//        }

        return month_view;
    }

}
