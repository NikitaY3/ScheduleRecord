package com.schedule.record.app.Fragment;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.schedule.record.app.R;
import com.schedule.record.app.function.FragmentCalendarController;

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
    private List<String> teamList;
    private Spinner calendarSpinner;
    private ArrayAdapter<String> arrayAdapter;

    @SuppressLint("ResourceType")
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
        calendarSpinner = view.findViewById(R.id.calendarSpinner);
        teamList = new ArrayList<>();
        teamList.add("我的一天");
        teamList.add("我的一周");
        teamList.add("我的一个月");
        arrayAdapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()),R.layout.main_calendar_item,teamList);
        arrayAdapter.setDropDownViewResource(R.layout.main_calendar_item);
        calendarSpinner.setAdapter(arrayAdapter);
        calendarSpinner.setSelection(0);
        calendarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String s=((TextView)view).getText().toString();
                Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
                calenderFrameLayout.showFragment(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}
