package com.schedule.record.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMyLogonPhone extends AppCompatActivity {
    @BindView(R.id.phoneButton2)
    Button phoneButton2;
    @BindView(R.id.phoneEditText1)
    EditText phoneEditText1;
    @BindView(R.id.phoneEditText2)
    EditText phoneEditText2;
    @BindView(R.id.phoneButton1)
    Button phoneButton1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_logon_phone);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.phoneButton2, R.id.phoneButton1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.phoneButton2:
                break;
            case R.id.phoneButton1:
                if (!(phoneEditText1.getText().toString().length()==11)) {
                    Toast.makeText(this,"您输入的手机号有误",Toast.LENGTH_SHORT).show();
                    phoneEditText1.setText("");
                } else {

                }
                break;
        }
    }
}
