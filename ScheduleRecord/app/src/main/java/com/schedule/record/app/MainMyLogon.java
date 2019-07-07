package com.schedule.record.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMyLogon extends AppCompatActivity {

    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.logonButton1)
    Button logonButton1;
    @BindView(R.id.logonButton2)
    Button logonButton2;
    @BindView(R.id.logonButton3)
    Button logonButton3;
    @BindView(R.id.logonButton4)
    Button logonButton4;
    @BindView(R.id.logonButton5)
    Button logonButton5;
    @BindView(R.id.logonButton6)
    Button logonButton6;
    @BindView(R.id.logonTextView2)
    TextView logonTextView2;
    @BindView(R.id.logonTextView3)
    TextView logonTextView3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_logon);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.logonButton1, R.id.logonButton2, R.id.logonButton3, R.id.logonButton4, R.id.logonButton5, R.id.logonButton6, R.id.logonTextView2, R.id.logonTextView3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.logonButton1:
                Toast.makeText(MainMyLogon.this,"登录/注册",Toast.LENGTH_LONG).show();
                Intent intent= new Intent(MainMyLogon.this,MainMyLogonPhone.class);
                startActivity(intent);
                break;
            case R.id.logonButton2:
                break;
            case R.id.logonButton3:
                break;
            case R.id.logonButton4:
                break;
            case R.id.logonButton5:
                break;
            case R.id.logonButton6:
                break;
            case R.id.logonTextView2:
                break;
            case R.id.logonTextView3:
                break;
        }
    }
}
