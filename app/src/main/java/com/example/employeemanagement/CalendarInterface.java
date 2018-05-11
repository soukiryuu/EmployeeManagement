package com.example.employeemanagement;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.employeemanagement.Controller.DayList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 月のカレンダーを生成
 */

public class CalendarInterface {

    private Context mContext;
    private LayoutInflater _inflater;
    private ViewGroup container;
    private static final int WEEK_7 = 7;
    private static final int MAX_WEEK = 6;
    public int beginningWeek = Calendar.SUNDAY;
    public int position;
    public View month_view, weekline_view, day_view, icon_view;
    public ImageView icon_image;
    //ViewPagerに表示する為の年月データ配列
    private ArrayList<int[]> mIndexes = new ArrayList<int[]>();
    private ArrayList<LinearLayout> mWeeks = new ArrayList<LinearLayout>();

    public CalendarInterface(Context context, LayoutInflater _inflater, ArrayList<int[]> indexes, ViewGroup container, int position){
        this.mContext = context;
        this._inflater = _inflater;
        this.mIndexes = indexes;
        this.container = container;
        this.position = position;
    }

    public View createCalendar(){

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
            //1週間ずつで区切るためのView
            weekline_view = null;
            weekline_view = _inflater.inflate(R.layout.calendar_week_frame, container, false);
            mWeeks.add((LinearLayout) weekline_view);
            for (int j = 0; j < WEEK_7; j++) {
                //一日のView生成
                day_view = null;
                day_view = _inflater.inflate(R.layout.calendar_day_frame, container,false);
//                icon_view = null;
                icon_view = (LinearLayout) day_view.findViewById(R.id.icon_frame);
                final TextView day_tv = (TextView) day_view.findViewById(R.id.day_tv);
//                ImageView icon_img_1 = (ImageView) day_view.findViewById(R.id.icon_1);
//                ImageView icon_img_2 = (ImageView) day_view.findViewById(R.id.icon_2);
//                ImageView icon_img_3 = (ImageView) day_view.findViewById(R.id.icon_3);
                LinearLayout borderLine = (LinearLayout) day_view.findViewById(R.id.border_line_layout);

                if (i == 0 && skipCount > 0) {
                    //日付けの開始曜日でなければ空白文字をセット
                    day_tv.setText(" ");
//                    icon_img_1.setBackground(null);
//                    icon_img_2.setBackground(null);
//                    icon_img_3.setBackground(null);
                    skipCount--;
                } else if (dayCounter <= lastDay) {
                    //1ヶ月分の日付けをセットしていく
                    day_tv.setText(String.valueOf(dayCounter));

                    icon_image = null;
                    icon_image = new ImageView(mContext);
                    icon_image.setImageResource(R.drawable.calendar_icon_1);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(24, 24);
                    icon_image.setLayoutParams(layoutParams);

                    ((LinearLayout) icon_view).addView(icon_image);

                    dayCounter++;
                } else {
                    //最後の日以降は空白文字をセット
                    day_tv.setText(" ");
//                    icon_img_1.setBackground(null);
//                    icon_img_2.setBackground(null);
//                    icon_img_3.setBackground(null);
                    borderLine.setBackground(null);
                }

                //動的にレイアウト設定しないと等間隔にならない・・・なぜ・・・
                LinearLayout.LayoutParams llp =
                        new LinearLayout.LayoutParams(
                                0, LinearLayout.LayoutParams.WRAP_CONTENT);
                llp.weight = 1;

                ((LinearLayout) weekline_view).addView(day_view,llp);
            }
            //その週の生成が完了したら親のViewに格納
            ((LinearLayout) month_view).addView(weekline_view);
        }

        return month_view;
    }

    class ListAdapter extends ArrayAdapter<DayList> {

        private LayoutInflater mInflater;
        private TextView li_tv;

        public ListAdapter(Context context, List<DayList> objects) {
            super(context, 0, objects);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.day_list_layout, null);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("click", String.valueOf(position));
//                        switch (position) {
//                            case 0:
//                                break;
//                            case 1:
//                                break;
//                            case 2:
//                                break;
//                            case 3:
//                                break;
//                            case 4:
//                                Intent login_intent = new Intent(SettingActivity.this,LoginActivity.class);
//                                login_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(login_intent);
//                                finish();
//                                break;
//                        }
                    }
                });
            }
//            final DayList item = this.getItem(position);
//            if(item != null){
//                li_tv = (TextView)convertView.findViewById(R.id.list_title);
//                li_tv.setText(item.getInfo());
//            }
            return convertView;

        }

    }

}
