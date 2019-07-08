package com.schedule.record.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.RadioGroup;

import com.schedule.record.app.function.FragmentController;

import butterknife.ButterKnife;

public class MainCalender1Edit extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_calender_edit);
    }
}
