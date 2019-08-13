package com.schedule.record.app.mainmy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.schedule.record.app.MainActivity;
import com.schedule.record.app.R;
import com.schedule.record.app.sqlite.GeneralUserSQLite;
import com.schedule.record.app.sqlite.dao.GeneralSQLiteUserDao;
import com.schedule.record.app.sqlite.user.GeneralSQLiteUser;

import java.util.List;

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

    private List<GeneralSQLiteUser> dataList;
    private GeneralUserSQLite helper;
    String DBName = "general";
    int version = 1;


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
                Intent intent = new Intent(this, MyFogetPassword.class);
                startActivity(intent);
                break;
            case R.id.phoneButton1:
                if (!(phoneEditText1.getText().toString().length()==11)) {
                    Toast.makeText(this,"您输入的手机号有误",Toast.LENGTH_SHORT).show();
                    phoneEditText1.setText("");
                } else {
                    GeneralSQLiteUser things = new GeneralSQLiteUser(phoneEditText1.getText().toString(),null,phoneEditText2.getText().toString(),null,null,null);
                    helper=new GeneralUserSQLite(MainMyLogonPhone.this,DBName,null,version);
                    helper.getReadableDatabase();
                    GeneralSQLiteUserDao dao=new GeneralSQLiteUserDao(helper);
                    dao.deleteAll();
                    dao.insert(things);
                    phoneEditText1.setText("");
                    phoneEditText2.setText("");

                    Intent intent2 = new Intent(this, MainActivity.class);
                    startActivity(intent2);
                }
                break;
        }
    }
}
