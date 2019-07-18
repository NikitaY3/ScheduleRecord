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
import android.widget.Toast;

import com.schedule.record.app.R;
import com.schedule.record.app.adapter.CalenderDayAdapter;
import com.schedule.record.app.dialog.DayDialog;
import com.schedule.record.app.function.DaySQLiteUser;
import com.schedule.record.app.function.DaySQLiteUserDao;
import com.schedule.record.app.function.Mode1ProgressBar;
import com.schedule.record.app.sqlite.DaySQLite;

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
    private List<DaySQLiteUser> dataList;

    private DaySQLite helper;
    String DBName = "day_1";
    int version = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_calendar_mode1, container, false);
        unbinder = ButterKnife.bind(this, view);

        calendar1ListView = view.findViewById(R.id.calendar1ListView);

        return view;
    }

    @Override
    public void onResume() {
        onResume1();
        super.onResume();
    }

    public void onResume1() {
        helper = new DaySQLite(getActivity(), DBName, null, version);
        helper.getReadableDatabase();
        DaySQLiteUserDao dao = new DaySQLiteUserDao(helper);
        dataList = (List<DaySQLiteUser>) dao.quiryAndSetItem();
        CalenderDayAdapter adapter = new CalenderDayAdapter(getActivity(),dataList,mode1ProgressBar);
        int countBar = dao.CountBar();
        Toast.makeText(getActivity(),"已完成数量为："+countBar,Toast.LENGTH_SHORT).show();

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
                DayDialog one = new DayDialog(getActivity(), calendar1ListView, dataList,mode1ProgressBar);
                one.show();
                break;
        }
    }
}

