package com.schedule.record.app.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.schedule.record.app.R;
import com.schedule.record.app.utils.RefreshUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EquimentFragment extends Fragment {

    @BindView(R.id.equButton)
    Button equButton;
    Unbinder unbinder;

//    private String today;
//
//    protected TodaySQLite helper2;
//    private String DBName2 = "today";
//
//    protected FutureSQLite helper3;
//    private String DBName3 = "future";

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_equiment, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.equButton)
    public void onViewClicked() {

//        today = getInternetTime();
//        int todayint = Integer.parseInt((today.substring(0,4)+today.substring(5,7)+today.substring(8,10)));
//        String week = new CalculationWeek(today).getWeek();
//
//        int version = 1;
//
//        //1.判断将Today日程插入到Finish日程,判断Today日程是否Pass
//        String todayb = getDay(-1);
//        int todaybint = Integer.parseInt((todayb.substring(0,4)+todayb.substring(5,7)+todayb.substring(8,10)));
//        helper2 = new TodaySQLite(getActivity(), DBName2, null, version);
//        TodaySQLiteUserDao dao2 = new TodaySQLiteUserDao(helper2);
//        dao2.TodayToFinishPass(getActivity(),todayb,todaybint);
//
//        //2.判断Future日程是否应该插入到Today
//        helper3 = new FutureSQLite(getActivity(), DBName3, null, version);
//        FutureSQLiteUserDao dao3 = new FutureSQLiteUserDao(helper3);
//        dao3.FutureToToday(getActivity(),todayint, Integer.parseInt(today.substring(9,10)),week,today);

        new RefreshUtils(Objects.requireNonNull(getActivity()));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
