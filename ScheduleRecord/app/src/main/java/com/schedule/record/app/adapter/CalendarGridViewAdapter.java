package com.schedule.record.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.schedule.record.app.aboutmycalendar.CalendarUtil;
import com.schedule.record.app.aboutmycalendar.FinishDayUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarGridViewAdapter  extends BaseAdapter {
    /** 日历item中默认id从0xff0000开始 */
    private final static int DEFAULT_ID = 0xff0000;
    private Calendar calStartDate = Calendar.getInstance();// 当前显示的日历
    private Calendar calSelected = Calendar.getInstance(); // 选择的日历

    /** 标注的日期 */
    private List<Date> markDates;

    private Context mContext;
    private Calendar calToday = Calendar.getInstance(); // 今日
    private ArrayList<Date> titles;

    String d;
    Date myDate;

    public CalendarGridViewAdapter(Context context, Calendar cal, List<Date> dates) {
        calStartDate = cal;
        this.mContext = context;
        titles = getDates();
        this.markDates = dates;

        setFinishDay();
    }

    public void setFinishDay() {
        d = new FinishDayUtil(mContext,myDate).toString();
        notifyDataSetChanged();
    }

    private ArrayList<java.util.Date> getDates() {

        UpdateStartDateForMonth();
        ArrayList<java.util.Date> alArrayList = new ArrayList<java.util.Date>();

        for (int i = 1; i <= 42; i++) {
            alArrayList.add(calStartDate.getTime());
            calStartDate.add(Calendar.DAY_OF_MONTH, 1);
        }

        return alArrayList;
    }


    public CalendarGridViewAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"WrongConstant", "ResourceAsColor"})
    @SuppressWarnings("deprecation")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            // 整个Item
            LinearLayout itemLayout = new LinearLayout(mContext);
            itemLayout.setId(position + DEFAULT_ID);
            itemLayout.setGravity(Gravity.CENTER);
            itemLayout.setOrientation(1);
            itemLayout.setBackgroundColor(Color.WHITE);

            myDate = (Date) getItem(position);
            itemLayout.setTag(myDate);
            Calendar calCalendar = Calendar.getInstance();
            calCalendar.setTime(myDate);

            // 显示日期day
            TextView textDay = new TextView(mContext);// 日期
            LinearLayout.LayoutParams text_params = new LinearLayout.LayoutParams( WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            textDay.setGravity(Gravity.CENTER_VERTICAL);
            textDay.setTextColor(Color.argb(0xff, 0x00, 0x85, 0x77));
            textDay.setTextSize(20);
            textDay.setPadding(15,3,0,0);
            int day = myDate.getDate(); // 日期
            textDay.setText(String.valueOf(day));
            textDay.setId(position + DEFAULT_ID);
            itemLayout.addView(textDay, text_params);

            // 自定义显示完成数量
            TextView finishDay = new TextView(mContext);
            LinearLayout.LayoutParams finish_params = new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            finishDay.setGravity(Gravity.CENTER_HORIZONTAL);
            finishDay.setTextSize(9);
            finishDay.setTextColor(Color.argb(0xff, 0x00, 0x57, 0x4b));
            finishDay.setText(d);
            itemLayout.addView(finishDay, finish_params);
            if (equalsDate(calToday.getTime(), myDate)) {
                // 如果是当前日期则显示不同颜色
                itemLayout.setBackgroundColor(Color.argb(0xff, 0xcc, 0x99, 0xff));
            }
            if (equalsDate(calSelected.getTime(), myDate)) {
                // 选择的
                itemLayout.setBackgroundColor(Color.argb(0xff, 0xff, 0x00, 0x00));
            } else if (!CalendarUtil.compare(myDate, calToday.getTime())) {
                // 这里用于比对是不是比当前日期小，如果比当前日期小则显示浅灰色
                itemLayout.setBackgroundColor(Color.argb(0xff,0xe7,0xe9,0xeb));
            }
            if (markDates != null) {
                /** 设置标注日期颜色 */
                final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                for (Date date : markDates) {
                    if (format.format(myDate).equals(format.format(date))) {
                        itemLayout.setBackgroundColor(Color.argb(0xff, 0x00, 0x00, 0x00));
                        break;
                    }
                }
            }

        return itemLayout;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @SuppressWarnings("deprecation")
    private Boolean equalsDate(Date date1, Date date2) {
        return date1.getYear() == date2.getYear()
                && date1.getMonth() == date2.getMonth()
                && date1.getDate() == date2.getDate();
    }

    // 根据改变的日期更新日历
    // 填充日历控件用
    private void UpdateStartDateForMonth() {
        calStartDate.set(Calendar.DATE, 1); // 设置成当月第一天
        // 星期一是2 星期天是1 填充剩余天数
        int iDay = 0;
        int iFirstDayOfWeek = Calendar.MONDAY;
        if (iFirstDayOfWeek == Calendar.MONDAY) {
            iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
            if (iDay < 0){
                iDay = 6;
            }
        }
        if (iFirstDayOfWeek == Calendar.SUNDAY) {
            iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
            if (iDay < 0){
                iDay = 6;
            }
        }
        calStartDate.add(Calendar.DAY_OF_WEEK, -iDay);
        calStartDate.add(Calendar.DAY_OF_MONTH, -1);// 周日第一位
    }

    public void setSelectedDate(Calendar cal) {
        calSelected = cal;
    }

}

