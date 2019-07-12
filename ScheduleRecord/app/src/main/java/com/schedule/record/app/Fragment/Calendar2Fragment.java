package com.schedule.record.app.Fragment;

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
import java.util.List;

import butterknife.OnClick;

public class Calendar2Fragment extends Fragment {

    private Button calendar2Button1;
    private ListView calendar2ListView;
    private View view;
    private List<CalenderWeekItem> dataList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_calendar_mode2, container, false);

//        unbinder = ButterKnife.bind(this, view);

        calendar2Button1 = view.findViewById(R.id.calendar2Button1);
        calendar2ListView = view.findViewById(R.id.calendar2ListView);
        //准备数据
        dataList = new ArrayList<CalenderWeekItem>();
        CalenderWeekItem things = new CalenderWeekItem("1", "This is test text. And it whill have three lines. And it while have suspension points.",
                "this is 2", "3", "4", "5", "6", "7");
        dataList.add(things);
        //创建适配器
        final CalenderWeekAdapter adapter = new CalenderWeekAdapter(getActivity(), dataList);
        //ListView关联适配器
        calendar2ListView.setAdapter(adapter);

        return view;
    }

    @OnClick(R.id.calendar2Button1)
    public void onViewClicked() {
    }
}
