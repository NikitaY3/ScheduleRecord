package com.schedule.record.app.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.schedule.record.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CalendarFragment extends Fragment {

    @BindView(R.id.calenderTextView1)
    TextView calenderTextView1;
    Unbinder unbinder;

    private View view;
    private FragmentCalendarController calenderFrameLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_calendar, container, false);
        unbinder = ButterKnife.bind(this, view);

        calenderFrameLayout = FragmentCalendarController.getInstance(this, R.id.calenderFrameLayout);
        calenderFrameLayout.showFragment(0);

        MySpinner();

        return view;
    }

    private void MySpinner() {
        Spinner calendarSpinner = view.findViewById(R.id.calendarSpinner);

        List<String> teamList = new ArrayList<>();
        teamList.add("我的一天");
        teamList.add("我的一周");
        teamList.add("我的一个月");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.main_calendar_item, teamList);
        arrayAdapter.setDropDownViewResource(R.layout.main_calendar_item);
        calendarSpinner.setAdapter(arrayAdapter);
        calendarSpinner.setSelection(0);
        calendarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                calenderFrameLayout.showFragment(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onResume() {
        calenderFrameLayout.getFragment(0).onResume();
        calenderFrameLayout.getFragment(1).onResume();
        calenderFrameLayout.getFragment(2).onResume();
        super.onResume();
    }
}
