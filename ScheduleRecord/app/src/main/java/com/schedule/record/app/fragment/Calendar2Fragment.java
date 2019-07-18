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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_calendar_mode2, container, false);
        unbinder = ButterKnife.bind(this, view);

        dataList1 = new ArrayList<>();
        dataList2 = new ArrayList<>();
        dataList3 = new ArrayList<>();
        dataList4 = new ArrayList<>();
        dataList5 = new ArrayList<>();
        dataList6 = new ArrayList<>();
        dataList7 = new ArrayList<>();
        dataList = new ArrayList<>();

        GetTodayWeek();

        PositionDetermine();

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

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void GetTodayWeek() {
        mode2ScrollView.smoothScrollTo(0,0);
        String today = getInternetTime();
        String todayweek = new CalculationWeek(today.substring(0, 11)).getWeek();
        switch (todayweek) {
            case "0":
                mode2WeekButton0.setBackgroundResource(R.drawable.abb_calendar_todayweek);
                break;
            case "1":
                mode2WeekButton1.setBackgroundResource(R.drawable.abb_calendar_todayweek);
                break;
            case "2":
                mode2WeekButton2.setBackgroundResource(R.drawable.abb_calendar_todayweek);
                break;
            case "3":
                mode2WeekButton3.setBackgroundResource(R.drawable.abb_calendar_todayweek);
                break;
            case "4":
                mode2WeekButton4.setBackgroundResource(R.drawable.abb_calendar_todayweek);
                break;
            case "5":
                mode2WeekButton5.setBackgroundResource(R.drawable.abb_calendar_todayweek);
                break;
            case "6":
                mode2WeekButton6.setBackgroundResource(R.drawable.abb_calendar_todayweek);
                break;
        }
        int dd = Integer.parseInt(today.substring(5,7));
        calendar2Button.setText(dd+"月");
    }

    private void PositionDetermine() {
        helper = new DaySQLite(getActivity(), DBName, null, version);
        helper.getReadableDatabase();
        DaySQLiteUserDao dao = new DaySQLiteUserDao(helper);
        mydData = dao.quiryAndSetWeekItem();
        for (int i = 0; i < mydData.size(); i++) {
            mydata = mydData.get(i);
            switch (mydata.getRepeat().substring(0, 8)) {
                case "everyday":
                    dataList1.add(mydata);
                    dataList2.add(mydata);
                    dataList3.add(mydata);
                    dataList4.add(mydata);
                    dataList5.add(mydata);
                    dataList6.add(mydata);
                    dataList7.add(mydata);
                    dataList.add(i);
                    break;
                case "everywee":
                    String re = mydata.getRepeat().substring(8);
                    while (!re.equals("")){
                        int a = Integer.parseInt(re.substring(0,1));
                        setChoiceWeek(a);
                        re = re.substring(1);
                    }
                    dataList.add(i);
                    break;
                case "everymou":
                    break;
                default:
//                    dataList1.add(mydata);
//                    dataList.add(i);
                    break;
            }
        }
    }

    private String getInternetTime() {
        //联网获取当前时间
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timesimple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timesimple.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return timesimple.format(new Date());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

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
}
