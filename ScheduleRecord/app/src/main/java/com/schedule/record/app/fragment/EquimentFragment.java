package com.schedule.record.app.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.schedule.record.app.R;
import com.schedule.record.app.function.CalculationWeek;
import com.schedule.record.app.function.CalenderWeekItem;
import com.schedule.record.app.sqlite.FinishSQLite;
import com.schedule.record.app.sqlite.FutureSQLite;
import com.schedule.record.app.sqlite.TodaySQLite;
import com.schedule.record.app.sqlite.dao.FutureSQLiteUserDao;
import com.schedule.record.app.sqlite.dao.TodaySQLiteUserDao;
import com.schedule.record.app.sqlite.user.FutureSQLiteUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EquimentFragment extends Fragment {
    View view;
    @BindView(R.id.equButton)
    Button equButton;
    Unbinder unbinder;

    private String today;

    int version = 1;
//    private FinishSQLite helper1;
//    String DBName1 = "finish";

    private TodaySQLite helper2;
    String DBName2 = "today";

    private FutureSQLite helper3;
    String DBName3 = "future";

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_equiment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @OnClick(R.id.equButton)
    public void onViewClicked() {

        today = getInternetTime();
        int todayint = Integer.parseInt((today.substring(0,4)+today.substring(5,7)+today.substring(8,10)));
        String week = new CalculationWeek(today).getWeek();

        //1.判断Future日程是否应该插入到Today
        helper3 = new FutureSQLite(getActivity(), DBName3, null, version);
        FutureSQLiteUserDao dao3 = new FutureSQLiteUserDao(helper3);
        dao3.FutureToToday(getActivity(),todayint, Integer.parseInt(today.substring(9,10)),week,today);

        //2.判断将Today日程插入到Finish日程,判断Today日程是否Pass
        String todayb = getDay(-1);
        int todaybint = Integer.parseInt((today.substring(0,4)+today.substring(5,7)+today.substring(8,10)));
        helper2 = new TodaySQLite(getActivity(), DBName2, null, version);
        TodaySQLiteUserDao dao2 = new TodaySQLiteUserDao(helper2);
        dao2.TodayToFinishPass(getActivity(),todayb,todaybint);

        //刷新Today的列表

    }

    //联网获取当前时间yyyy-MM-dd
    private String getInternetTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timesimple = new SimpleDateFormat("yyyy-MM-dd");
        timesimple.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return timesimple.format(new Date());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //获取当前天的前后日期
    @SuppressLint("SimpleDateFormat")
    public String getDay(int i) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day1 = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day1 + i);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());

        return dayAfter;
    }
}
