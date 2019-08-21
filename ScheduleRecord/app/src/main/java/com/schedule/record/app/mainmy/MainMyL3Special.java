package com.schedule.record.app.mainmy;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.schedule.record.app.R;
import com.schedule.record.app.sqlite.AuthoritySQLite;
import com.schedule.record.app.sqlite.dao.AuthoritySQLiteUserDao;
import com.schedule.record.app.sqlite.user.AuthoritySQLiteUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("Registered")
public class MainMyL3Special extends AppCompatActivity {

    @BindView(R.id.specialEditText)
    EditText specialEditText;
    @BindView(R.id.specialButton1)
    Button specialButton1;
    @BindView(R.id.specialButton2)
    Button specialButton2;
    @BindView(R.id.specialTextView2)
    TextView specialTextView2;

    private AuthoritySQLiteUser user;
    private AuthoritySQLite helper;
    String DBName = "authority";
    int version = 1;
    private AuthoritySQLiteUserDao dao;
    private String nameid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_l3special);
        ButterKnife.bind(this);

        //取得登录用户的ID
        SharedPreferences sharedPreferences;
        sharedPreferences = this.getSharedPreferences("myuser",MODE_PRIVATE);
        nameid = sharedPreferences.getString("nameid","");
        user = new AuthoritySQLiteUser(nameid,null);

        //普通用户是我，特殊用户是编辑框里面的
        helper = new AuthoritySQLite(MainMyL3Special.this, DBName, null, version);
        dao = new AuthoritySQLiteUserDao(helper);
        specialTextView2.setText(dao.querySpecial(nameid));
    }

    @OnClick({R.id.specialButton1, R.id.specialButton2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.specialButton1:
                user.setSnameid(specialEditText.getText().toString());
                dao.insert(user);
                specialTextView2.setText(dao.querySpecial(nameid));
                break;
            case R.id.specialButton2:
                dao.deleteByGSNameid(nameid,specialEditText.getText().toString());
                specialTextView2.setText(dao.querySpecial(nameid));
                break;
        }
    }
}
