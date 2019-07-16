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

import com.schedule.record.app.R;
import com.schedule.record.app.adapter.CalenderWeekAdapter;
import com.schedule.record.app.function.CalenderWeekItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;

public class Calendar2Fragment extends Fragment {

    private Button calendar2Button1;
    private ListView calendar2ListView;
    private View view;
    private List<CalenderWeekItem> dataList;

    private ListView gview;
    private CalenderWeekAdapter sim_adapter;

    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_calendar_mode2, container, false);

        //准备数据
        dataList = new ArrayList<CalenderWeekItem>();
        CalenderWeekItem things = new CalenderWeekItem("1", "This is test text. And it whill have three lines. And it while have suspension points.",
                "this is 2", "3", "4", "5", "6", "7");
        dataList.add(things);



        gview = view.findViewById(R.id.calendar2ListView1);
        sim_adapter = new CalenderWeekAdapter(getActivity(), dataList);
        //配置适配器
        gview.setAdapter(sim_adapter);



        return view;
    }
    @OnClick(R.id.calendar2Button1)
    public void onViewClicked() {
    }
}
