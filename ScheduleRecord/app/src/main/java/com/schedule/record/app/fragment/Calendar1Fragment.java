package com.schedule.record.app.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.schedule.record.app.R;
import com.schedule.record.app.sqlite.dao.TodaySQLiteUserDao;
import com.schedule.record.app.adapter.CalenderDayAdapter;
import com.schedule.record.app.dialog.TodayDialog;
import com.schedule.record.app.function.Mode1ProgressBar;
import com.schedule.record.app.sqlite.user.TodaySQLiteUser;
import com.schedule.record.app.sqlite.TodaySQLite;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Calendar1Fragment extends Fragment{

    @BindView(R.id.calendar1Button2)
    Button calendar1Button2;
    Unbinder unbinder;
    @BindView(R.id.mode1ProgressBar)
    ProgressBar mode1ProgressBar;

    private View view;
    private ListView calendar1ListView;
    private List<TodaySQLiteUser> dataList;

    private TodaySQLite helper;
    String DBName = "today";
    int version = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_calendar_mode1, container, false);
        unbinder = ButterKnife.bind(this, view);

        calendar1ListView = view.findViewById(R.id.calendar1ListView);
        onResume1();

        return view;
    }

    @Override
    public void onResume() {
        onResume1();
        super.onResume();
    }

    public void onResume1() {
        helper = new TodaySQLite(getActivity(), DBName, null, version);
        TodaySQLiteUserDao dao = new TodaySQLiteUserDao(helper);
        dataList = dao.quiryAndSetItem();
        CalenderDayAdapter adapter = new CalenderDayAdapter(getActivity(),dataList,mode1ProgressBar);
        int countBar = dao.CountBar();

        new Mode1ProgressBar(countBar,dataList.size(),mode1ProgressBar);
        calendar1ListView.setAdapter(adapter);
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
                TodayDialog one = new TodayDialog(getActivity(), calendar1ListView, dataList,mode1ProgressBar);
                one.show();
                break;
        }
    }
}

