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
import android.widget.Toast;

import com.schedule.record.app.R;
import com.schedule.record.app.adapter.CalenderDayAdapter;
import com.schedule.record.app.function.CalenderDayItem;
import com.schedule.record.app.function.DaySQLiteUser;
import com.schedule.record.app.function.DaySQLiteUserDao;
import com.schedule.record.app.sqlite.DaySQLite;

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

    private DaySQLite helper;
    String DBName="day_1";
    int version=1;
    int Dayid1 = -1;

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

                helper=new DaySQLite(getActivity(),DBName,null,version);
                helper.getReadableDatabase();
                DaySQLiteUserDao dao=new DaySQLiteUserDao(helper);
                Dayid1++;
                String Dayid = String.valueOf(Dayid1);
                dao.insert(new DaySQLiteUser(Dayid,false,"0000-00-00 00:00:00",
                        "ddddddddd","12","dddd","0000-00-00","this is diary","test"));
                Toast.makeText(getActivity(),"添加图书信息成功",Toast.LENGTH_SHORT).show();

                break;
        }
    }
}
