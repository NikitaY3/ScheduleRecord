package com.schedule.record.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.schedule.record.app.fragment.FragmentController;
import com.schedule.record.app.mainmy.MainMyLogonPhone;

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

    private FragmentController controller;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        controller = FragmentController.getInstance(MainActivity.this, R.id.mainFrameLayout);
        if (controller != null) {
            controller.showFragment(0);
        }

        mainRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton1:
                        controller.showFragment(0);
                        break;
                    case R.id.radioButton2:
                        controller.showFragment(1);
                        break;
                    case R.id.radioButton3:
                        controller.showFragment(2);
                        break;
                }
            }
        });

        sharedPreferences = this.getSharedPreferences("myuser",MODE_PRIVATE);
        String a = sharedPreferences.getString("nameid","");
        if (a.equals("")){

            Intent intent2 = new Intent(MainActivity.this, MainMyLogonPhone.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent2);

            MainActivity.this.onDestroy();
        }
    }

//    @Override
//    protected void onResume() {
//        controller = FragmentController.getInstance(MainActivity.this, R.id.mainFrameLayout);
//        if (controller != null) {
//            controller.showFragment(0);
//        }
//        super.onResume();
//    }

    @Override
    public void finish() {

//        MainActivity.this.onDestroy();

        super.finish();
    }
}
