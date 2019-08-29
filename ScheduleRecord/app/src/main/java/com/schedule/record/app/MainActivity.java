package com.schedule.record.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.schedule.record.app.fragment.FragmentCalendarController;
import com.schedule.record.app.fragment.FragmentController;
import com.schedule.record.app.mainmy.MainMyLogonPhone;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.mainFrameLayout)
    FrameLayout mainFrameLayout;
    @BindView(R.id.radioButton1)
    RadioButton radioButton1;
    @BindView(R.id.radioButton2)
    RadioButton radioButton2;
    @BindView(R.id.radioButton3)
    RadioButton radioButton3;
    @BindView(R.id.mainRadioGroup)
    RadioGroup mainRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = this.getSharedPreferences("myuser", MODE_PRIVATE);
        String a = sharedPreferences.getString("nameid","");
        if (a.equals("")){

            Intent intent2 = new Intent(MainActivity.this, MainMyLogonPhone.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent2);

            finish();

        }else {
            final FragmentController controller;
            controller = FragmentController.getInstance(MainActivity.this, R.id.mainFrameLayout);
            if (controller != null) {
                controller.showFragment(0);
            }
            mainRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.radioButton1:
                            Objects.requireNonNull(controller).showFragment(0);
                            controller.getFragment(0).onResume();

                            break;
                        case R.id.radioButton2:
                            Objects.requireNonNull(controller).showFragment(1);
                            controller.getFragment(1).onResume();

                            break;
                        case R.id.radioButton3:
                            Objects.requireNonNull(controller).showFragment(2);
                            controller.getFragment(2).onResume();

                            break;
                    }
                }
            });
        }
    }


    @Override
    public void finish() {

        FragmentCalendarController.destoryController();
        FragmentController.destoryController();

        super.finish();
    }
}
