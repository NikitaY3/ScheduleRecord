package com.schedule.record.app.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;

import com.schedule.record.app.R;
import com.schedule.record.app.adapter.CalenderWeek2Adapter;
import com.schedule.record.app.adapter.CalenderWeekAdapter;
import com.schedule.record.app.function.CalculationWeek;
import com.schedule.record.app.function.CalenderWeekItem;
import com.schedule.record.app.function.DaySQLiteUserDao;
import com.schedule.record.app.sqlite.DaySQLite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Calendar2Fragment extends Fragment {

    @BindView(R.id.calendar2ListView)
    ListView calendar2ListView;
    @BindView(R.id.calendar2ListView1)
    ListView calendar2ListView1;
    @BindView(R.id.calendar2ListView2)
    ListView calendar2ListView2;
    @BindView(R.id.calendar2ListView3)
    ListView calendar2ListView3;
    @BindView(R.id.calendar2ListView4)
    ListView calendar2ListView4;
    @BindView(R.id.calendar2ListView5)
    ListView calendar2ListView5;
    @BindView(R.id.calendar2ListView6)
    ListView calendar2ListView6;
    @BindView(R.id.calendar2ListView7)
    ListView calendar2ListView7;
    @BindView(R.id.calendar2Button1)
    Button calendar2Button;
    @BindView(R.id.mode2WeekButton0)
    Button mode2WeekButton0;
    @BindView(R.id.mode2WeekButton1)
    Button mode2WeekButton1;
    @BindView(R.id.mode2WeekButton2)
    Button mode2WeekButton2;
    @BindView(R.id.mode2WeekButton3)
    Button mode2WeekButton3;
    @BindView(R.id.mode2WeekButton4)
    Button mode2WeekButton4;
    @BindView(R.id.mode2WeekButton5)
    Button mode2WeekButton5;
    @BindView(R.id.mode2WeekButton6)
    Button mode2WeekButton6;
    @BindView(R.id.mode2ScrollView)
    ScrollView mode2ScrollView;
    Unbinder unbinder;


    private View view;
    private CalenderWeekItem mydata;
    private List<CalenderWeekItem> dataList1;
    private List<CalenderWeekItem> dataList2;
    private List<CalenderWeekItem> dataList3;
    private List<CalenderWeekItem> dataList4;
    private List<CalenderWeekItem> dataList5;
    private List<CalenderWeekItem> dataList6;
    private List<CalenderWeekItem> dataList7;
    private List<Integer> dataList;

    private CalenderWeekAdapter weekAdapter;
    private CalenderWeek2Adapter week2Adapter;

    private List<CalenderWeekItem> mydData;
    private DaySQLite helper;
    String DBName = "day_1";
    int version = 1;

    String today1;
    String todayweek;
    int todayint1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_calendar_mode2, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    public void onResume1() {
        mode2ScrollView.smoothScrollTo(0,0);
        StartSet();
        today1 = getInternetTime();
        todayint1 = Integer.parseInt(today1.substring(0,4)+ today1.substring(5,7)+today1.substring(8,10));

//        Toast.makeText(getActivity(),"结束日期"+todayint,Toast.LENGTH_SHORT).show();
        GetTodayWeek(today1);
        PositionDetermine(today1,todayint1);
    }

    private void StartSet() {
        dataList1 = new ArrayList<>();
        dataList2 = new ArrayList<>();
        dataList3 = new ArrayList<>();
        dataList4 = new ArrayList<>();
        dataList5 = new ArrayList<>();
        dataList6 = new ArrayList<>();
        dataList7 = new ArrayList<>();
        dataList = new ArrayList<>();

        //每周的日程——周日
        weekAdapter = new CalenderWeekAdapter(getActivity(), dataList1);
        calendar2ListView1.setAdapter(weekAdapter);

        //每周的日程——周一
        weekAdapter = new CalenderWeekAdapter(getActivity(), dataList2);
        calendar2ListView2.setAdapter(weekAdapter);

        //每周的日程——周二
        weekAdapter = new CalenderWeekAdapter(getActivity(), dataList3);
        calendar2ListView3.setAdapter(weekAdapter);

        //每周的日程——周三
        weekAdapter = new CalenderWeekAdapter(getActivity(), dataList4);
        calendar2ListView4.setAdapter(weekAdapter);

        //每周的日程——周四
        weekAdapter = new CalenderWeekAdapter(getActivity(), dataList5);
        calendar2ListView5.setAdapter(weekAdapter);

        //每周的日程——周五
        weekAdapter = new CalenderWeekAdapter(getActivity(), dataList6);
        calendar2ListView6.setAdapter(weekAdapter);

        //每周的日程——周六
        weekAdapter = new CalenderWeekAdapter(getActivity(), dataList7);
        calendar2ListView7.setAdapter(weekAdapter);

        //数量
        week2Adapter = new CalenderWeek2Adapter(getActivity(), dataList);
        calendar2ListView.setAdapter(week2Adapter);
    }

    //查询当天日期，并将布局标记好
    @SuppressLint({"SetTextI18n", "WrongConstant"})
    public void GetTodayWeek(String day) {
        todayweek = new CalculationWeek(day.substring(0, 10)).getWeek();
        Calendar calendar = new GregorianCalendar();
        try {
            calendar.setTime(ConverToDate(today1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date d;
        String c;
        int todayint;

        switch (todayweek) {
            case "0":
                mode2WeekButton0.setBackgroundResource(R.drawable.abb_calendar_todayweek);

                calendar.add(Calendar.DATE,1);
                d = calendar.getTime();
                c = ConverToString(d);
                todayint = Integer.parseInt(c.substring(0,4)+ c.substring(5,7)+c.substring(8,10));
                PositionDetermine(c,todayint);

                calendar.add(Calendar.DATE,2);
                d = calendar.getTime();
                c = ConverToString(d);
                todayint = Integer.parseInt(c.substring(0,4)+ c.substring(5,7)+c.substring(8,10));
                PositionDetermine(c,todayint);

                calendar.add(Calendar.DATE,3);
                d = calendar.getTime();
                c = ConverToString(d);
                todayint = Integer.parseInt(c.substring(0,4)+ c.substring(5,7)+c.substring(8,10));
                PositionDetermine(c,todayint);

                calendar.add(Calendar.DATE,4);
                d = calendar.getTime();
                c = ConverToString(d);
                todayint = Integer.parseInt(c.substring(0,4)+ c.substring(5,7)+c.substring(8,10));
                PositionDetermine(c,todayint);

                calendar.add(Calendar.DATE,5);
                d = calendar.getTime();
                c = ConverToString(d);
                todayint = Integer.parseInt(c.substring(0,4)+ c.substring(5,7)+c.substring(8,10));
                PositionDetermine(c,todayint);

                calendar.add(Calendar.DATE,6);
                d = calendar.getTime();
                c = ConverToString(d);
                todayint = Integer.parseInt(c.substring(0,4)+ c.substring(5,7)+c.substring(8,10));
                PositionDetermine(c,todayint);

                break;
            case "1":

                calendar.add(Calendar.DATE,1);
                d = calendar.getTime();
                c = ConverToString(d);
                todayint = Integer.parseInt(c.substring(0,4)+ c.substring(5,7)+c.substring(8,10));
                PositionDetermine(c,todayint);

                calendar.add(Calendar.DATE,2);
                d = calendar.getTime();
                c = ConverToString(d);
                todayint = Integer.parseInt(c.substring(0,4)+ c.substring(5,7)+c.substring(8,10));
                PositionDetermine(c,todayint);

                calendar.add(Calendar.DATE,3);
                d = calendar.getTime();
                c = ConverToString(d);
                todayint = Integer.parseInt(c.substring(0,4)+ c.substring(5,7)+c.substring(8,10));
                PositionDetermine(c,todayint);

                calendar.add(Calendar.DATE,4);
                d = calendar.getTime();
                c = ConverToString(d);
                todayint = Integer.parseInt(c.substring(0,4)+ c.substring(5,7)+c.substring(8,10));
                PositionDetermine(c,todayint);

                calendar.add(Calendar.DATE,5);
                d = calendar.getTime();
                c = ConverToString(d);
                todayint = Integer.parseInt(c.substring(0,4)+ c.substring(5,7)+c.substring(8,10));
                PositionDetermine(c,todayint);

                mode2WeekButton1.setBackgroundResource(R.drawable.abb_calendar_todayweek);
                break;
            case "2":

                calendar.add(Calendar.DATE,1);
                d = calendar.getTime();
                c = ConverToString(d);
                todayint = Integer.parseInt(c.substring(0,4)+ c.substring(5,7)+c.substring(8,10));
                PositionDetermine(c,todayint);

                calendar.add(Calendar.DATE,2);
                d = calendar.getTime();
                c = ConverToString(d);
                todayint = Integer.parseInt(c.substring(0,4)+ c.substring(5,7)+c.substring(8,10));
                PositionDetermine(c,todayint);

                calendar.add(Calendar.DATE,3);
                d = calendar.getTime();
                c = ConverToString(d);
                todayint = Integer.parseInt(c.substring(0,4)+ c.substring(5,7)+c.substring(8,10));
                PositionDetermine(c,todayint);

                calendar.add(Calendar.DATE,4);
                d = calendar.getTime();
                c = ConverToString(d);
                todayint = Integer.parseInt(c.substring(0,4)+ c.substring(5,7)+c.substring(8,10));
                PositionDetermine(c,todayint);
                mode2WeekButton2.setBackgroundResource(R.drawable.abb_calendar_todayweek);
                break;
            case "3":

                calendar.add(Calendar.DATE,1);
                d = calendar.getTime();
                c = ConverToString(d);
                todayint = Integer.parseInt(c.substring(0,4)+ c.substring(5,7)+c.substring(8,10));
                PositionDetermine(c,todayint);

                calendar.add(Calendar.DATE,2);
                d = calendar.getTime();
                c = ConverToString(d);
                todayint = Integer.parseInt(c.substring(0,4)+ c.substring(5,7)+c.substring(8,10));
                PositionDetermine(c,todayint);

                calendar.add(Calendar.DATE,3);
                d = calendar.getTime();
                c = ConverToString(d);
                todayint = Integer.parseInt(c.substring(0,4)+ c.substring(5,7)+c.substring(8,10));
                PositionDetermine(c,todayint);
                mode2WeekButton3.setBackgroundResource(R.drawable.abb_calendar_todayweek);
                break;
            case "4":

                calendar.add(Calendar.DATE,1);
                d = calendar.getTime();
                c = ConverToString(d);
                todayint = Integer.parseInt(c.substring(0,4)+ c.substring(5,7)+c.substring(8,10));
                PositionDetermine(c,todayint);

                calendar.add(Calendar.DATE,2);
                d = calendar.getTime();
                c = ConverToString(d);
                todayint = Integer.parseInt(c.substring(0,4)+ c.substring(5,7)+c.substring(8,10));
                PositionDetermine(c,todayint);
                mode2WeekButton4.setBackgroundResource(R.drawable.abb_calendar_todayweek);
                break;
            case "5":

                calendar.add(Calendar.DATE,1);
                d = calendar.getTime();
                c = ConverToString(d);
                todayint = Integer.parseInt(c.substring(0,4)+ c.substring(5,7)+c.substring(8,10));
                PositionDetermine(c,todayint);
                mode2WeekButton5.setBackgroundResource(R.drawable.abb_calendar_todayweek);
                break;
            case "6":
                mode2WeekButton6.setBackgroundResource(R.drawable.abb_calendar_todayweek);
                break;
        }
        int dd = Integer.parseInt(today1.substring(5,7));
        calendar2Button.setText(dd+"月");
    }

    //判断today,todayint当天显示哪些日程
    private void PositionDetermine(String today,int todayint) {
        helper = new DaySQLite(getActivity(), DBName, null, version);
        helper.getReadableDatabase();
        DaySQLiteUserDao dao = new DaySQLiteUserDao(helper);

        //当天日期显示日程
        mydData = dao.quiryTodayWeek(todayint);

        for (int i = 0; i < mydData.size(); i++) {
            mydata = mydData.get(i);
            switch (mydata.getRepeat().substring(0, 8)) {
                case "everywee":
                    String re = mydata.getRepeat().substring(8);
                    while (!re.equals("")){
                        if (re.substring(0, 1).equals(todayweek)) {
                            setChoiceWeek(Integer.parseInt(todayweek));
                        }
                        re = re.substring(1);
                    }
                    break;
                case "everymou":
                    String re2 = mydata.getRepeat().substring(8);
                    int a = Integer.parseInt(re2.substring(0,4)+ re2.substring(5,7)+ re2.substring(8,10));
                    if(oneWeek(a)) {
                        String mo = mydata.getRepeat().substring(8, 10);
                        String mouweek = new CalculationWeek(today.substring(0, 9) + mo).getWeek();
                        if (todayweek.equals(mouweek)){
                            setChoiceWeek(Integer.parseInt(mouweek));
                            dataList.add(i);
                        }
                    }
                    break;
                default:
                    setChoiceWeek(Integer.parseInt(todayweek));
                    dataList.add(i);
                    break;
            }
        }

//        List<String> dayidList = dao.quiryPassDayid(todayint);

    }

    //判断某一天是否和今天在同一周
    private boolean oneWeek(int x) {
        int s = 20190106;
        int m = (todayint1 - s)/7;
        int n = (x - s)/7;
        return n == m;
    }

    //联网获取当前时间yyyy-MM-dd HH:mm:ss
    private String getInternetTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timesimple = new SimpleDateFormat("yyyy-MM-dd");
        timesimple.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return timesimple.format(new Date());
    }

    //把字符串转为日期
    public static Date ConverToDate(String strDate) throws Exception
    {
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(strDate);
    }

    //把日期转为字符串
    public static String ConverToString(Date date)
    {
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(date);
    }

    //根据0123456设置日程显示在哪一天
    public void setChoiceWeek(int posion) {
        if (posion == 0) {
            dataList1.add(mydata);
        }
        if (posion == 1) {
            dataList2.add(mydata);
        }
        if (posion == 2) {
            dataList3.add(mydata);
        }
        if (posion == 3) {
            dataList4.add(mydata);
        }
        if (posion == 4) {
            dataList5.add(mydata);
        }
        if (posion == 5) {
            dataList6.add(mydata);
        }
        if (posion == 6) {
            dataList7.add(mydata);
        }
    }

    @Override
    public void onResume() {
        onResume1();
        super.onResume();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
