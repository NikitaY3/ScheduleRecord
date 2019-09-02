package com.schedule.record.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.schedule.record.app.R;
import com.schedule.record.app.sqlite.FinishSQLite;
import com.schedule.record.app.sqlite.FutureSQLite;
import com.schedule.record.app.sqlite.TodaySQLite;
import com.schedule.record.app.sqlite.dao.FinishSQLiteUserDao;
import com.schedule.record.app.sqlite.dao.FutureSQLiteUserDao;
import com.schedule.record.app.sqlite.dao.TodaySQLiteUserDao;
import com.schedule.record.app.utils.CalendarDateUtils;

import static android.content.Context.MODE_PRIVATE;

public class CalendarDateAdapter extends BaseAdapter {
    private int[] days = new int[42];

    private Context context;
    private int year;
    private int month;
    private LayoutInflater inflater;

    public CalendarDateAdapter(Context context, int[][] days, int year, int month) {
        this.context = context;
        int dayNum = 0;

        //将二维数组转化为一维数组
        int i = 0;
        while (i < days.length) {
            for (int j = 0; j < days[i].length; j++) {
                this.days[dayNum] = days[i][j];
                dayNum++;
            }
            i++;
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

    @SuppressLint({"SetTextI18n", "InflateParams"})
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.main_calendar_mode3_item, null);
            viewHolder = new ViewHolder();
            viewHolder.date_item = view.findViewById(R.id.m3ItemButton);
            viewHolder.m3ItemText2 = view.findViewById(R.id.m3ItemText2);
            viewHolder.lin = view.findViewById(R.id.m3ItemText);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //获取年月
        year = CalendarDateUtils.getYear();
        month = CalendarDateUtils.getMonth();

        viewHolder.date_item.setText(days[i] + "");

        if (i < 7 && days[i] > 20) {
            viewHolder.date_item.setTextColor(Color.rgb(204, 204, 204));//将上个月的和下个月的设置为灰色
            viewHolder.m3ItemText2.setTextColor(Color.rgb(204, 204, 204));//将上个月的和下个月的设置为灰色
            viewHolder.m3ItemText2.setText("");
        } else if (i > 20 && days[i] < 15) {
            viewHolder.date_item.setTextColor(Color.rgb(204, 204, 204));
            viewHolder.m3ItemText2.setTextColor(Color.rgb(204, 204, 204));
            viewHolder.m3ItemText2.setText("");
        }else {
            //获取日期
            int today = CalendarDateUtils.getCurrentDayOfMonth();

            if (i == today){
                //格式区别
                viewHolder.lin.setBackgroundResource(R.drawable.abaa_item_important2);
                //对今天的日程求和
                TodaySQLite helper1;
                String DBName1 = "today";
                helper1 = new TodaySQLite(context, DBName1, null, 1);
                TodaySQLiteUserDao dao = new TodaySQLiteUserDao(helper1);

                //取得登录用户的ID
                SharedPreferences sharedPreferences;
                sharedPreferences = context.getSharedPreferences("myuser", MODE_PRIVATE);
                String nameid = sharedPreferences.getString("nameid", "");

                String sText = "日程：" + dao.CountBar(nameid) + "/" + dao.CountAllBar(nameid);
                viewHolder.m3ItemText2.setText(sText);

            }else if (i < today){
                //对过去的日程求和
                FinishSQLite helper2;
                String DBName2 = "finish";
                helper2 = new FinishSQLite(context, DBName2, null, 1);
                FinishSQLiteUserDao dao = new FinishSQLiteUserDao(helper2);

                //格式问题
                String text = year + "-";
                if (month < 10 && days[i] < 10) {
                    text = text + "0" + month + "-0" + days[i];
                } else if (days[i] < 10) {
                    text = text + month + "-0" + days[i];
                } else if (month < 10) {
                    text = text + "0" + month + "-" + days[i];
                } else {
                    text = text + month + "-" + days[i];
                }

                String sText = "日程：" + dao.CountFinishByDay(text) + "/" + dao.CountByDay(text);
                viewHolder.m3ItemText2.setText(sText);

            }else {
                //对未来的日程求和
                FutureSQLite helper3;
                String DBName3 = "future";
                helper3 = new FutureSQLite(context, DBName3, null, 1);
                FutureSQLiteUserDao dao = new FutureSQLiteUserDao(helper3);

                String sText = "共" + dao.CountAll() + "条日程";
                viewHolder.m3ItemText2.setText(sText);
            }
        }

        viewHolder.date_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                //是否考虑转跳查看日程

                String text = year + "年" + month + "月" + days[i] + "日";
                if (!(i < 7 && days[i] > 20) && !(i > 20 && days[i] < 15)) {
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }

    class ViewHolder {
        Button date_item;
        TextView m3ItemText2;
        LinearLayout lin;
    }
}