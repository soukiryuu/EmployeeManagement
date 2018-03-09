package com.example.employeemanagement;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class SampleActivity extends AppCompatActivity {

    private static final int WEEK_7 = 7;
    private static final int MAX_WEEK = 6;
    public int beginningWeek = Calendar.SUNDAY;
    private ArrayList<LinearLayout> mWeeks = new ArrayList<LinearLayout>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_frame);

        beginningWeek = Calendar.SUNDAY; // 週の開始曜日を指定

        Calendar cal = Calendar.getInstance();
        cal.clear(); // まずクリアしてからセットしないといけない
        cal.set(Calendar.YEAR, 2018);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        // カレンダーの最初の空白の個数を求める。
        int skipCount; // 空白の個数
        int firstDayMonthWeek = cal.get(Calendar.DAY_OF_WEEK); // 1日の曜日
        Log.d("firstDayMonthWeek", String.valueOf(firstDayMonthWeek));
        if (beginningWeek > firstDayMonthWeek) {
            skipCount = firstDayMonthWeek - beginningWeek + WEEK_7;
        } else {
            skipCount = firstDayMonthWeek - beginningWeek;
        }
        Log.d("skipCount", String.valueOf(skipCount));
        int lastDay = cal.getActualMaximum(Calendar.DATE); // 月の最終日
        Log.d("lastDay", String.valueOf(lastDay));

        LinearLayout ll = (LinearLayout)findViewById(R.id.days_contents);
        // 日付を生成する。
        int dayCounter = 1;
        for (int i = 0; i < MAX_WEEK; i++) {
            LinearLayout weekLine = new LinearLayout(this);
            mWeeks.add(weekLine);
            for (int j = 0; j < WEEK_7; j++) {
                View view = getLayoutInflater().inflate(R.layout.calendar_day_frame, null);
                TextView dayView = (TextView) view.findViewById(R.id.day_tv);
                ImageView icon_img_1 = (ImageView) view.findViewById(R.id.icon_1);
                ImageView icon_img_2 = (ImageView) view.findViewById(R.id.icon_2);
                ImageView icon_img_3 = (ImageView) view.findViewById(R.id.icon_3);
                LinearLayout borderLine = (LinearLayout) view.findViewById(R.id.border_line_layout);

                if (i == 0 && skipCount > 0) {
                    //日付けの開始曜日でなければ空白文字をセット
                    dayView.setText(" ");
                    icon_img_1.setBackground(null);
                    icon_img_2.setBackground(null);
                    icon_img_3.setBackground(null);
                    skipCount--;
                } else if (dayCounter <= lastDay) {
                    //1ヶ月分の日付けをセットしていく
                    dayView.setText(String.valueOf(dayCounter));

                    dayCounter++;
                } else {
                    //最後の日以降は空白文字をセット
                    dayView.setText(" ");
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
                weekLine.addView(view,llp);
            }

            ll.addView(weekLine);
        }
    }
}
