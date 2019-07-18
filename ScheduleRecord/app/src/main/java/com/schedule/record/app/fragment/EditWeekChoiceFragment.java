package com.schedule.record.app.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.schedule.record.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EditWeekChoiceFragment extends Fragment {
    View view;
    CheckBox weekItemButton0;
    CheckBox weekItemButton1;
    CheckBox weekItemButton2;
    CheckBox weekItemButton3;
    CheckBox weekItemButton4;
    CheckBox weekItemButton5;
    CheckBox weekItemButton6;

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mode1_edit_weekchioce_item, container, false);

        weekItemButton0 = view.findViewById(R.id.weekItemButton0);
        weekItemButton1 = view.findViewById(R.id.weekItemButton1);
        weekItemButton2 = view.findViewById(R.id.weekItemButton2);
        weekItemButton3 = view.findViewById(R.id.weekItemButton3);
        weekItemButton4 = view.findViewById(R.id.weekItemButton4);
        weekItemButton5 = view.findViewById(R.id.weekItemButton5);
        weekItemButton6 = view.findViewById(R.id.weekItemButton6);

        return view;
    }

    public String getChoiceWeek() {
        String a = "";
        if (weekItemButton0.isChecked()) {
            a = a + "0";
        }
        if (weekItemButton1.isChecked()) {
            a = a + "1";
        }
        if (weekItemButton2.isChecked()) {
            a = a + "2";
        }
        if (weekItemButton3.isChecked()) {
            a = a + "3";
        }
        if (weekItemButton4.isChecked()) {
            a = a + "4";
        }
        if (weekItemButton5.isChecked()) {
            a = a + "5";
        }
        if (weekItemButton6.isChecked()) {
            a = a + "6";
        }
        return a;
    }

    public void setChoiceWeek(int posion) {
        if (posion == 0) {
            weekItemButton0.setChecked(true);
        }
        if (posion == 1) {
            weekItemButton1.setChecked(true);
        }
        if (posion == 2) {
            weekItemButton2.setChecked(true);
        }
        if (posion == 3) {
            weekItemButton3.setChecked(true);
        }
        if (posion == 4) {
            weekItemButton4.setChecked(true);
        }
        if (posion == 5) {
            weekItemButton5.setChecked(true);
        }
        if (posion == 6) {
            weekItemButton6.setChecked(true);
        }
    }

}
