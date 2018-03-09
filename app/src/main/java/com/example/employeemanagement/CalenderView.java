package com.example.employeemanagement;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by watanabehiroaki on 2018/02/28.
 */

public class CalenderView extends LinearLayout {
    private static final int WEEK_7 = 7;
    private static final int MAX_WEEK = 6;
    public CalenderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOrientation(VERTICAL);
        // dp-->px　変換用の値
        final float scale =
                context.getResources().getDisplayMetrics().density;
        // パディング
        final int padding = (int) (scale * 5);
        // 右パディング
        final int paddingR = (int) (scale * 15);
        // タイトルとのパディング
        final int paddintT = (int) (scale * 20);
        titleFontPx = scale * 30;
        // 本日の日付を保持
        mToday = Calendar.getInstance();
        // タイトル部　年月表示
        Log.v("CalendarView", "タイトル部　年月表示");
        mTitle = new TextView(context);
        mTitle.setGravity(Gravity.CENTER_HORIZONTAL); // 中央に表示
        mTitle.setTypeface(null, Typeface.BOLD); // 太字
        mTitle.setText("DEBUG");
        mTitle.setPadding(0, 0, 0, paddintT);
        addView(mTitle, new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        dayFontPx = mTitle.getTextSize(); // 現在のフォントサイズを保持
        defaultColor = mTitle.getTextColors().getDefaultColor();
        // 週表示部　日月火水木金土
        Log.v("CalendarView", "週表示部　日月火水木金土");
        mWeekLayout = new LinearLayout(context); // 週表示レイアウト
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, beginningWeek); // 週の頭をセット
        SimpleDateFormat formatter = new SimpleDateFormat("E"); // 曜日を取得するフォーマッタ
        for (int i = 0; i < WEEK_7; i++) {
            TextView textView = new TextView(context);
            textView.setText(formatter.format(cal.getTime())); // テキストに曜日を表示
            textView.setGravity(Gravity.CENTER_HORIZONTAL); // 中央に表示
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                    0, LayoutParams.WRAP_CONTENT);
            llp.weight = 1;
            mWeekLayout.addView(textView, llp);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        addView(mWeekLayout, new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        // カレンダー部 最大6行必要
        Log.v("CalendarView", "カレンダー部 最大6行必要");
        for (int i = 0; i < MAX_WEEK; i++) {
            LinearLayout weekLine = new LinearLayout(context);
            mWeeks.add(weekLine);
            // 1週間分の日付ビュー作成
            for (int j = 0; j < WEEK_7; j++) {
                TextView dayView = new TextView(context);
                dayView.setText(String.valueOf((i * WEEK_7) + (j + 1))); // TODO:DEBUG
                dayView.setGravity(Gravity.RIGHT); // 右寄せで表示
                dayView.setPadding(0, padding, paddingR, padding);
                LinearLayout.LayoutParams llp =
                        new LinearLayout.LayoutParams(
                                0, LayoutParams.WRAP_CONTENT);
                llp.weight = 1;
                weekLine.addView(dayView, llp);
            }
            this.addView(weekLine, new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }
        init(mToday.get(Calendar.YEAR), mToday.get(Calendar.MONTH));
    }
    // 表示設定
    public void init(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.clear(); // まずクリアしてからセットしないといけない
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int todayYear = mToday.get(Calendar.YEAR);
        int todayMonth = mToday.get(Calendar.MONTH);
        int todayDay = mToday.get(Calendar.DAY_OF_MONTH);
        // タイトル 年月 設定
        String formatString =
                mTitle.getContext().getString(R.string.format_month_year); // 年月フォーマット文字列
        mTitle.setTextSize(titleFontPx);
        // 年月を取得するフォーマッタ
        SimpleDateFormat formatter = new SimpleDateFormat(formatString);
        mTitle.setText(formatter.format(cal.getTime()));
        // 週表示部　日月火水木金土
        Log.v("CalendarView", "週表示部　日月火水木金土");
        Calendar week = Calendar.getInstance();
        week.set(Calendar.DAY_OF_WEEK, beginningWeek); // 週の頭をセット
        SimpleDateFormat weekFormatter = new SimpleDateFormat("E"); // 曜日を取得するフォーマッタ
        for (int i = 0; i < WEEK_7; i++) {
            TextView textView = (TextView) mWeekLayout.getChildAt(i);
            textView.setText(weekFormatter.format(week.getTime())); // テキストに曜日を表示
            week.add(Calendar.DAY_OF_MONTH, 1);
        }
        // カレンダーの最初の空白の個数を求める。
        int skipCount; // 空白の個数
        int firstDayMonthWeek = cal.get(Calendar.DAY_OF_WEEK); // 1日の曜日
        if (beginningWeek > firstDayMonthWeek) {
            skipCount = firstDayMonthWeek - beginningWeek + WEEK_7;
        } else {
            skipCount = firstDayMonthWeek - beginningWeek;
        }
        int lastDay = cal.getActualMaximum(Calendar.DATE); // 月の最終日
        // 日付を生成する。
        int dayCounter = 1;
        for (int i = 0; i < MAX_WEEK; i++) {
            LinearLayout weekLayout = mWeeks.get(i);
            weekLayout.setBackgroundColor(defaultBackgroundColor);
            for (int j = 0; j < WEEK_7; j++) {
                TextView dayView = (TextView) weekLayout.getChildAt(j);
                if (i == 0 && skipCount > 0) {
                    dayView.setText(" ");
                    skipCount--;
                } else if (dayCounter <= lastDay) {
                    dayView.setText(String.valueOf(dayCounter));
                    // 今日の日付を赤文字にする
                    if (todayYear == year
                            && todayMonth == month
                            && todayDay == dayCounter) {
                        dayView
                                .setTextColor(todayColor); // 赤文字
                        dayView.setTypeface(null,
                                Typeface.BOLD); // 太字
                        weekLayout
                                .setBackgroundColor(todayBackgroundColor); // 週の背景グレー
                    } else {
                        dayView.setTextColor(defaultColor);
                        dayView.setTypeface(null,
                                Typeface.NORMAL);
                    }
                    dayCounter++;
                } else {
                    dayView.setText(" ");
                }
            }
        }
    }
    /** 週の始まりの曜日を保持する */
    public int beginningWeek = Calendar.SUNDAY;
    /** 今日のフォント色 */
    public int todayColor = Color.RED;
    /** 今週の背景色 */
    public int todayBackgroundColor = Color.LTGRAY;
    /** 通常の背景色 */
    public int defaultBackgroundColor = Color.TRANSPARENT;
    /** 通常のフォント色 */
    public int defaultColor;
    /** 年月のフォントサイズ */
    public float titleFontPx;
    /** 日付のフォントサイズ */
    public float dayFontPx;
    private TextView mTitle; // 年月表示部分
    private LinearLayout mWeekLayout;
    private ArrayList<LinearLayout> mWeeks = new ArrayList<LinearLayout>();
    private Calendar mToday;
}
