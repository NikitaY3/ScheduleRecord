package com.schedule.record.app.fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
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
import com.schedule.record.app.sqlite.FinishSQLite;
import com.schedule.record.app.sqlite.FutureSQLite;
import com.schedule.record.app.sqlite.dao.FinishSQLiteUserDao;
import com.schedule.record.app.sqlite.dao.FutureSQLiteUserDao;
import com.schedule.record.app.sqlite.dao.TodaySQLiteUserDao;
import com.schedule.record.app.adapter.CalenderWeek2Adapter;
import com.schedule.record.app.adapter.CalenderWeekAdapter1;
import com.schedule.record.app.adapter.CalenderWeekAdapter2;
import com.schedule.record.app.adapter.CalenderWeekAdapter3;
import com.schedule.record.app.function.CalculationWeek;
import com.schedule.record.app.function.CalenderWeekItem;
import com.schedule.record.app.sqlite.TodaySQLite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

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

    private CalenderWeekItem mydata;
    private List<CalenderWeekItem> dataList1;
    private List<CalenderWeekItem> dataList2;
    private List<CalenderWeekItem> dataList3;
    private List<CalenderWeekItem> dataList4;
    private List<CalenderWeekItem> dataList5;
    private List<CalenderWeekItem> dataList6;
    private List<CalenderWeekItem> dataList7;
    private List<Integer> dataList;

    public int version = 1;
    public List<CalenderWeekItem> finishData;
    public FinishSQLite helper1;
    public String DBName1 = "finish";

    public List<CalenderWeekItem> todayData;
    public TodaySQLite helper2;
    public String DBName2 = "today";

    public List<CalenderWeekItem> futureData;
    public FutureSQLite helper3;
    public String DBName3 = "future";

    private String today1;
    private int week;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_calendar_mode2, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @SuppressLint("SetTextI18n")
    public void onResume1() {
        //0、初始化
        StartSet();
        //1、获取当前日期
        today1 = getInternetTime();
        int dd = Integer.parseInt(today1.substring(5,7));
        calendar2Button.setText(dd+"月");
        //2、判断今天星期
        String todayweek = new CalculationWeek(today1.substring(0, 10)).getWeek();
        week = Integer.parseInt(todayweek);//0-6

        CalenderWeekAdapter1 weekAdapter1;
        CalenderWeekAdapter2 weekAdapter2;
        CalenderWeekAdapter3 weekAdapter3;

        //3、判断星期日
        if (0<week){
            weekAdapter1 = new CalenderWeekAdapter1(getActivity(), dataList1);
            calendar2ListView1.setAdapter(weekAdapter1);
            String finday = getDay(0-week);
            setFinishItem(finday);
        }else{
            weekAdapter2 = new CalenderWeekAdapter2(getActivity(), dataList1);
            calendar2ListView1.setAdapter(weekAdapter2);
            mode2WeekButton0.setBackgroundResource(R.drawable.abb_calendar_todayweek);
            setTodayItem();
        }

        //4、判断星期一
        if (1<week){
            weekAdapter1 = new CalenderWeekAdapter1(getActivity(), dataList2);
            calendar2ListView2.setAdapter(weekAdapter1);
            String finday = getDay(1-week);
            setFinishItem(finday);
        }else if (1==week){
            weekAdapter2 = new CalenderWeekAdapter2(getActivity(), dataList2);
            calendar2ListView2.setAdapter(weekAdapter2);
            mode2WeekButton1.setBackgroundResource(R.drawable.abb_calendar_todayweek);
            setTodayItem();
        }else {
            weekAdapter3 = new CalenderWeekAdapter3(getActivity(), dataList2);
            calendar2ListView2.setAdapter(weekAdapter3);
            String futday = getDay(1-week);
            setFutureItem(futday);
        }

        //5、判断星期二
        if (2<week){
            weekAdapter1 = new CalenderWeekAdapter1(getActivity(), dataList3);
            calendar2ListView3.setAdapter(weekAdapter1);
            String finday = getDay(2-week);
            setFinishItem(finday);
        }else if (2==week){
            weekAdapter2 = new CalenderWeekAdapter2(getActivity(), dataList3);
            calendar2ListView3.setAdapter(weekAdapter2);
            mode2WeekButton2.setBackgroundResource(R.drawable.abb_calendar_todayweek);
            setTodayItem();
        }else {
            weekAdapter3 = new CalenderWeekAdapter3(getActivity(), dataList3);
            calendar2ListView3.setAdapter(weekAdapter3);
            String futday = getDay(2-week);
            setFutureItem(futday);
        }

        //6、判断星期三
        if (3<week){
            weekAdapter1 = new CalenderWeekAdapter1(getActivity(), dataList4);
            calendar2ListView4.setAdapter(weekAdapter1);
            String finday = getDay(3-week);
            setFinishItem(finday);
        }else if (3==week){
            weekAdapter2 = new CalenderWeekAdapter2(getActivity(), dataList4);
            calendar2ListView4.setAdapter(weekAdapter2);
            mode2WeekButton3.setBackgroundResource(R.drawable.abb_calendar_todayweek);
            setTodayItem();
        }else {
            weekAdapter3 = new CalenderWeekAdapter3(getActivity(), dataList4);
            calendar2ListView4.setAdapter(weekAdapter3);
            String futday = getDay(3-week);
            setFutureItem(futday);
        }

        //7、判断星期四
        if (4<week){
            weekAdapter1 = new CalenderWeekAdapter1(getActivity(), dataList5);
            calendar2ListView5.setAdapter(weekAdapter1);
            String finday = getDay(4-week);
            setFinishItem(finday);
        }else if (4==week){
            weekAdapter2 = new CalenderWeekAdapter2(getActivity(), dataList5);
            calendar2ListView5.setAdapter(weekAdapter2);
            mode2WeekButton4.setBackgroundResource(R.drawable.abb_calendar_todayweek);
            setTodayItem();
        }else {
            weekAdapter3 = new CalenderWeekAdapter3(getActivity(), dataList5);
            calendar2ListView5.setAdapter(weekAdapter3);
            String futday = getDay(4-week);
            setFutureItem(futday);
        }

        //8、判断星期五
        if (5<week){
            weekAdapter1 = new CalenderWeekAdapter1(getActivity(), dataList6);
            calendar2ListView6.setAdapter(weekAdapter1);
            String finday = getDay(5-week);
            setFinishItem(finday);
        }else if (5==week){
            weekAdapter2 = new CalenderWeekAdapter2(getActivity(), dataList6);
            calendar2ListView6.setAdapter(weekAdapter2);
            mode2WeekButton5.setBackgroundResource(R.drawable.abb_calendar_todayweek);
            setTodayItem();
        }else {
            weekAdapter3 = new CalenderWeekAdapter3(getActivity(), dataList6);
            calendar2ListView6.setAdapter(weekAdapter3);
            String futday = getDay(5-week);
            setFutureItem(futday);
        }

        //9、判断星期六
        if (6==week){
            weekAdapter2 = new CalenderWeekAdapter2(getActivity(), dataList7);
            calendar2ListView7.setAdapter(weekAdapter2);
            mode2WeekButton6.setBackgroundResource(R.drawable.abb_calendar_todayweek);
            setTodayItem();
        }else {
            weekAdapter3 = new CalenderWeekAdapter3(getActivity(), dataList7);
            calendar2ListView7.setAdapter(weekAdapter3);
            String futday = getDay(6-week);
            setFutureItem(futday);
        }
    }

    private void StartSet() {

        mode2ScrollView.smoothScrollTo(0,0);

        dataList1 = new ArrayList<>();
        dataList2 = new ArrayList<>();
        dataList3 = new ArrayList<>();
        dataList4 = new ArrayList<>();
        dataList5 = new ArrayList<>();
        dataList6 = new ArrayList<>();
        dataList7 = new ArrayList<>();
        dataList = new ArrayList<>();

        //数量
        CalenderWeek2Adapter week2Adapter = new CalenderWeek2Adapter(getActivity(), dataList);
        calendar2ListView.setAdapter(week2Adapter);
    }

    //判断以前显示哪些日程
    private void setFinishItem(String day) {
        helper1 = new FinishSQLite(getActivity(), DBName1, null, version);
        helper1.getReadableDatabase();
        FinishSQLiteUserDao dao = new FinishSQLiteUserDao(helper1);

        //判断星期
        String finishweek = new CalculationWeek(day).getWeek();
        int fweek = Integer.parseInt(finishweek);

        finishData = dao.quiryAndSetWeekItem(day);
        for (int i = 0; i < finishData.size(); i++) {
            mydata = finishData.get(i);
            setChoiceWeek(fweek,mydata);
        }
    }

    //判断当天显示哪些日程
    private void setTodayItem() {
        helper2 = new TodaySQLite(getActivity(), DBName2, null, version);
        helper2.getReadableDatabase();
        TodaySQLiteUserDao dao = new TodaySQLiteUserDao(helper2);

        //取得登录用户的ID
        SharedPreferences sharedPreferences;
        sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("myuser", MODE_PRIVATE);
        String nameid = sharedPreferences.getString("nameid", "");

        todayData = dao.quiryAndSetWeekItem(nameid);
        for (int i = 0; i < todayData.size(); i++) {
            mydata = todayData.get(i);
            setChoiceWeek(week,mydata);
            dataList.add(i);
        }
    }

    //判断以后显示哪些日程
    private void setFutureItem(String day) {
        helper3 = new FutureSQLite(getActivity(), DBName3, null, version);
        helper3.getReadableDatabase();
        FutureSQLiteUserDao dao = new FutureSQLiteUserDao(helper3);

        //判断星期
        String futureweek = new CalculationWeek(day).getWeek();
        int f1week = Integer.parseInt(futureweek);

        int dayint = Integer.parseInt((day.substring(0, 4) + day.substring(5, 7) + day.substring(8, 10)));
        int m = Integer.parseInt(day.substring(9,10));

        futureData = dao.quiryAndSetWeekItem(dayint,m,futureweek,day);

        for (int i = 0; i < futureData.size(); i++) {
            mydata = futureData.get(i);
            setChoiceWeek(f1week,mydata);
        }
    }

    //联网获取当前时间yyyy-MM-dd HH:mm:ss
    private String getInternetTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timesimple = new SimpleDateFormat("yyyy-MM-dd");
        timesimple.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return timesimple.format(new Date());
    }

    //根据0123456设置日程显示在哪一天
    public void setChoiceWeek(int posion, CalenderWeekItem data) {
        if (posion == 0) {
            dataList1.add(data);
        }
        if (posion == 1) {
            dataList2.add(data);
        }
        if (posion == 2) {
            dataList3.add(data);
        }
        if (posion == 3) {
            dataList4.add(data);
        }
        if (posion == 4) {
            dataList5.add(data);
        }
        if (posion == 5) {
            dataList6.add(data);
        }
        if (posion == 6) {
            dataList7.add(data);
        }
    }


    //获取当前天的前后日期
    @SuppressLint("SimpleDateFormat")
    public String getDay(int i) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(today1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day1 = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day1 + i);

        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
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


//    //判断某一天是否和今天在同一周
//    private boolean oneWeek(int x) {
//        int s = 20190106;
//        int m = (todayint1 - s)/7;
//        int n = (x - s)/7;
//        return n == m;
//    }
//
//    //把字符串转为日期
//    public static Date ConverToDate(String strDate) throws Exception
//    {
//        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        return df.parse(strDate);
//    }
//
//    //把日期转为字符串
//    public static String ConverToString(Date date)
//    {
//        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//
//        return df.format(date);
//    }

}
