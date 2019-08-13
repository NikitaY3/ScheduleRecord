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

import com.schedule.record.app.R;
import com.schedule.record.app.function.CalculationWeek;
import com.schedule.record.app.function.CalenderWeekItem;
import com.schedule.record.app.sqlite.FinishSQLite;
import com.schedule.record.app.sqlite.FutureSQLite;
import com.schedule.record.app.sqlite.TodaySQLite;
import com.schedule.record.app.sqlite.dao.FutureSQLiteUserDao;
import com.schedule.record.app.sqlite.user.FutureSQLiteUser;

import java.text.SimpleDateFormat;
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

    int version = 1;
    private List<CalenderWeekItem> finishData;
    private FinishSQLite helper1;
    String DBName1 = "finish";

    private List<CalenderWeekItem> todayData;
    private TodaySQLite helper2;
    String DBName2 = "today";


    private List<CalenderWeekItem> futureData;
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
        //1.判断Future日程是否应该插入到Today
        helper3 = new FutureSQLite(getActivity(), DBName3, null, version);
        FutureSQLiteUserDao dao3 = new FutureSQLiteUserDao(helper3);
        String today = getInternetTime();
        int todayint = Integer.parseInt((today.substring(0,4)+today.substring(5,7)+today.substring(8,10)));
        String week = new CalculationWeek(today).getWeek();
        dao3.FutureToToday(getActivity(),todayint, Integer.parseInt(today.substring(9,10)),week);

        //2.判断将Today日程插入到Finish日程

        //3.判断Today日程是否Pass
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
}
