package com.schedule.record.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.schedule.record.app.R;
import com.schedule.record.app.sqlite.FinishSQLite;
import com.schedule.record.app.sqlite.TodaySQLite;
import com.schedule.record.app.sqlite.dao.FinishSQLiteUserDao;
import com.schedule.record.app.sqlite.dao.TodaySQLiteUserDao;
import com.schedule.record.app.utils.CalendarDateUtils;

public class CalendarDateAdapter extends BaseAdapter {
    private int[] days = new int[42];

    private Context context;
    private int year;
    private int month;
    private LayoutInflater inflater;

    public CalendarDateAdapter(Context context, int[][] days, int year, int month) {
        this.context = context;
        int dayNum = 0;
        //将二维数组转化为一维数组，方便使用
        for (int i = 0; i < days.length; i++) {
            for (int j = 0; j < days[i].length; j++) {
                this.days[dayNum] = days[i][j];
                dayNum++;
            }
        }
        this.year = year;
        this.month = month;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return days.length;
    }

    @Override
    public Object getItem(int i) {
        return days[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.main_calendar_mode3_item, null);
            viewHolder = new ViewHolder();
            viewHolder.date_item = view.findViewById(R.id.m3ItemButton);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (i < 7 && days[i] > 20) {
            viewHolder.date_item.setTextColor(Color.rgb(204, 204, 204));//将上个月的和下个月的设置为灰色
        } else if (i > 20 && days[i] < 15) {
            viewHolder.date_item.setTextColor(Color.rgb(204, 204, 204));
        }

        int today = CalendarDateUtils.getCurrentDayOfMonth();
        if (i == today){
            viewHolder.date_item.setBackgroundResource(R.drawable.abaa_item_im_no);
            //对今天的日程求和

            TodaySQLite helper1;
            String DBName1 = "today";
            helper1 = new TodaySQLite(context, DBName1, null, 1);
            TodaySQLiteUserDao dao = new TodaySQLiteUserDao(helper1);

            String sText = days[i] + "\n完成日程：" + dao.CountBar() + "\n" + dao.CountAllBar();
            viewHolder.date_item.setText(sText);

        }else if (i < today){
            //对过去的日程求和

//            FinishSQLite helper2;
//            String DBName2 = "finish";
//            helper2 = new FinishSQLite(context, DBName2, null, 1);
//            FinishSQLiteUserDao dao = new FinishSQLiteUserDao(helper2);
//            dao.CountByDay(String.valueOf(today));
            viewHolder.date_item.setText(days[i] + "");

        }else {
            //对未来的日程求和
            viewHolder.date_item.setText(days[i] + "");

        }




        viewHolder.date_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //是否考虑转跳查看日程
                String text = CalendarDateUtils.getYear() + "年" + CalendarDateUtils.getMonth() + "月" + days[i] + "日";

                if (i < 7 && days[i] > 20) {
                } else if (i > 20 && days[i] < 15) {
                } else {
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    /**
     * 优化Adapter
     */
    class ViewHolder {
        TextView date_item;
    }
}