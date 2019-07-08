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
import android.widget.TextView;

import com.schedule.record.app.R;
import com.schedule.record.app.adapter.CalenderDayAdapter;
import com.schedule.record.app.function.CalenderDayItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Calendar1Fragment extends Fragment {

    @BindView(R.id.calendar1Button2)
    Button calendar1Button2;

    Unbinder unbinder;
    private View view;
    private ListView calendar1ListView;
    private List<CalenderDayItem> dataList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_calendar_mode1, container, false);

        Adapter();

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void Adapter() {
        calendar1ListView = view.findViewById(R.id.calendar1ListView);
        //准备数据
        dataList = new ArrayList<CalenderDayItem>();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.calendar1Button2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.calendar1Button2:
                CalenderDayItem things = new CalenderDayItem("00:00","This is test text. And it whill have three lines. And it while have suspension points.");
                dataList.add(things);
                //创建适配器
                final CalenderDayAdapter adapter = new CalenderDayAdapter(getActivity(), dataList);
                //ListView关联适配器
                calendar1ListView.setAdapter(adapter);
                break;
        }
    }
}
