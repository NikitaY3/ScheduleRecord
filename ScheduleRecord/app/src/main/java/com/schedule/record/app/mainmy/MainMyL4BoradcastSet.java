package com.schedule.record.app.mainmy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.schedule.record.app.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMyL4BoradcastSet extends AppCompatActivity {

    @BindView(R.id.bSetSwitch1)
    Switch bSetSwitch1;
    @BindView(R.id.bSetSwitch2)
    Switch bSetSwitch2;
    @BindView(R.id.bSetSpinner)
    Spinner bSetSpinner;

    private ArrayAdapter<String> arrayAdapter;
    private List<String> bSetSpinnerList;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_l4broadcastsetting);
        ButterKnife.bind(this);

        SpinnerList();

        StartSet();

    }

    @OnClick({R.id.bSetSwitch1, R.id.bSetSwitch2})
    public void onViewClicked(View view) {
        SharedPreferences.Editor delaytime = sharedPreferences.edit();
        switch (view.getId()) {
            case R.id.bSetSwitch1:
                if (bSetSwitch1.isChecked()){
                    delaytime.putString("mobilecolock","true");
                }else {
                    delaytime.putString("mobilecolock","false");
                }
                delaytime.apply();
                break;
            case R.id.bSetSwitch2:
                if (bSetSwitch1.isChecked()){
                    delaytime.putString("boxcolock","true");
                }else {
                    delaytime.putString("boxcolock","false");
                }
                delaytime.apply();
                break;
        }
    }

    //初始化设置
    private void StartSet() {
        sharedPreferences = getSharedPreferences("delaytime",MODE_PRIVATE);
        String s = sharedPreferences.getString("time","");
        switch (s) {
            case "30分钟":
                bSetSpinner.setSelection(0);
                break;
            case "1小时":
                bSetSpinner.setSelection(1);
                break;
            case "2小时":
                bSetSpinner.setSelection(2);
                break;
            case "3小时":
                bSetSpinner.setSelection(3);
                break;
            case "4小时":
                bSetSpinner.setSelection(4);
                break;
        }

        String s1 = sharedPreferences.getString("mobilecolock","");
        if (s1.equals("true")) {
            bSetSwitch1.setChecked(true);
        }else {
            bSetSwitch1.setChecked(false);
        }

        String s2 = sharedPreferences.getString("boxcolock","");
        if (s2.equals("true")) {
            bSetSwitch2.setChecked(true);
        }else {
            bSetSwitch2.setChecked(false);
        }
    }

    //设置下拉框的列表内容
    private void SpinnerList() {
        bSetSpinnerList = new ArrayList<>();
        bSetSpinnerList.add("30分钟");
        bSetSpinnerList.add("1小时");
        bSetSpinnerList.add("2小时");
        bSetSpinnerList.add("3小时");
        bSetSpinnerList.add("4小时");

        MySpinner(bSetSpinnerList,bSetSpinner);
    }

    private void MySpinner(List<String> teamList, Spinner spinner) {
        //下拉列表函数
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.main_calendar_item,teamList);
        arrayAdapter.setDropDownViewResource(R.layout.main_calendar_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String s=((TextView)view).getText().toString();

                SharedPreferences.Editor delaytime = sharedPreferences.edit();

                switch (s) {
                    case "30分钟":
                        delaytime.putString("time","30分钟");
                        break;
                    case "1小时":
                        delaytime.putString("time","1小时");
                        break;
                    case "2小时":
                        delaytime.putString("time","2小时");
                        break;
                    case "3小时":
                        delaytime.putString("time","3小时");
                        break;
                    case "4小时":
                        delaytime.putString("time","4小时");
                        break;
                }
                delaytime.apply();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}
