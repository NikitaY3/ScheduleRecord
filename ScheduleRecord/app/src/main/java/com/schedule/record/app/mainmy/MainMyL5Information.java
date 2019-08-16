package com.schedule.record.app.mainmy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.schedule.record.app.MainActivity;
import com.schedule.record.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMyL5Information extends AppCompatActivity {

    @BindView(R.id.informationButton1)
    Button informationButton1;
    @BindView(R.id.informationButton2)
    Button informationButton2;
    @BindView(R.id.informationButton3)
    Button informationButton3;
    @BindView(R.id.informationButton4)
    Button informationButton4;
    @BindView(R.id.informationButton5)
    Button informationButton5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_l5information);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.informationButton1, R.id.informationButton2, R.id.informationButton3, R.id.informationButton4, R.id.informationButton5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.informationButton1:
                Intent intent = new Intent(MainMyL5Information.this, MyL5NameSet.class);
                startActivity(intent);

//                finishActivity(MainActivity.class);
//                MainActivity.class.getClass().onDestroy();
//                MainActivity.class.finish();
                break;
            case R.id.informationButton2:
                break;
            case R.id.informationButton3:
                break;
            case R.id.informationButton4:
                break;
            case R.id.informationButton5:
                break;
        }
    }
}
