package com.example.employeemanagement.Controller;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.employeemanagement.CalendarInterface;

import java.util.ArrayList;

/**
 * Created by watanabehiroaki on 2018/03/09.
 */

public class CalendarPagerAdapter extends PagerAdapter {

    private LayoutInflater _inflater;
    private ArrayList<int[]> mIndexes = new ArrayList<int[]>();
    public View month_view;
    public Context mContext;

    public CalendarPagerAdapter(Context context) {
        super();
        this.mContext = context;
        _inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mIndexes.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d("CalendarPagerAdapter", "instantiateItem");

        CalendarInterface calendarInterface = new CalendarInterface(mContext, _inflater, mIndexes, container, position);
        month_view = calendarInterface.createCalendar();
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
