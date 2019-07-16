//package com.schedule.record.app.aboutmycalendar;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.view.Gravity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.schedule.record.app.R;
//
//public class WeekGridAdapter extends BaseAdapter {
//
//    final String[] titles = new String[] { "日", "一", "二", "三", "四", "五", "六" };
//    private Context mContext;
//
//    public WeekGridAdapter(Context context) {
//        this.mContext = context;
//    }
//
//    @Override
//    public int getCount() {
//        return titles.length;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return titles[position];
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        TextView week = new TextView(mContext);
//        android.view.ViewGroup.LayoutParams week_params = new WindowManager.LayoutParams(
//                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
//                android.view.ViewGroup.LayoutParams.MATCH_PARENT);
//        week.setLayoutParams(week_params);
//        week.setPadding(0, 0, 0, 0);
//        week.setGravity(Gravity.CENTER);
//        week.setFocusable(false);
////        week.setBackgroundResource(R.drawable.abc_calendar_datehead);
//        week.setBackgroundColor(R.color.white);
//
//        if (position == 6) { // 周六
//            week.setBackgroundColor(R.color.bule);
//            week.setTextColor(R.color.white);
//        } else if (position == 0) { // 周日
//            week.setBackgroundColor(R.color.red);
//            week.setTextColor(R.color.white);
//        } else {
//            week.setTextColor(R.color.varygray2);
//        }
//        week.setText(getItem(position) + "");
//        return week;
//    }
//}
//
