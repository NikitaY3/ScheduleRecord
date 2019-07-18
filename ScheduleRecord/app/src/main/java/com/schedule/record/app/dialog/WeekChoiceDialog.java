package com.schedule.record.app.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

import com.schedule.record.app.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;


public class WeekChoiceDialog extends Dialog {
    Context context;
    @BindView(R.id.weekItemButton0)
    CheckBox weekItemButton0;
    @BindView(R.id.weekItemButton1)
    CheckBox weekItemButton1;
    @BindView(R.id.weekItemButton2)
    CheckBox weekItemButton2;
    @BindView(R.id.weekItemButton3)
    CheckBox weekItemButton3;
    @BindView(R.id.weekItemButton4)
    CheckBox weekItemButton4;
    @BindView(R.id.weekItemButton5)
    CheckBox weekItemButton5;
    @BindView(R.id.weekItemButton6)
    CheckBox weekItemButton6;
    @BindView(R.id.weekIteBtn1)
    Button weekIteBtn1;
    @BindView(R.id.weekIteBtn2)
    Button weekIteBtn2;

    public WeekChoiceDialog(Context context) {
        super(context);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode1_edit_weekitem);

    }

    public String ChoiceWeek() {
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

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = Objects.requireNonNull(getWindow()).getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(layoutParams);
    }

    @OnClick({R.id.weekIteBtn1, R.id.weekIteBtn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.weekIteBtn1:

                break;
            case R.id.weekIteBtn2:
                break;
        }
    }
}
