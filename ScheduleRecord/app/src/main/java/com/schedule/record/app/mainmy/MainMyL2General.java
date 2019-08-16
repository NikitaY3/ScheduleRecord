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
import com.schedule.record.app.sqlite.SpecialUserSQLite;
import com.schedule.record.app.sqlite.dao.SpecialSQLiteUserDao;
import com.schedule.record.app.sqlite.user.SpecialSQLiteUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("Registered")
public class MainMyL2General extends AppCompatActivity {


    @BindView(R.id.generalEditText)
    EditText generalEditText;
    @BindView(R.id.generalButton1)
    Button generalButton1;
    @BindView(R.id.generalButton2)
    Button generalButton2;
    @BindView(R.id.generalTextView2)
    TextView generalTextView2;

    private SpecialSQLiteUser user;
    private SpecialUserSQLite helper;
    private String DBName = "special";
    private int version = 1;
    private SpecialSQLiteUserDao dao;
    private String nameid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_l2general);
        ButterKnife.bind(this);

        //取得登录用户的ID
        SharedPreferences sharedPreferences;
        sharedPreferences = this.getSharedPreferences("myuser",MODE_PRIVATE);
        nameid = sharedPreferences.getString("nameid","");
        user = new SpecialSQLiteUser(null, nameid);
//        user.setSnameid(nameid);

        //特殊用户是我，普通用户是编辑框里面的
        helper = new SpecialUserSQLite(MainMyL2General.this, DBName, null, version);
        dao = new SpecialSQLiteUserDao(helper);
        generalTextView2.setText(dao.queryGeneral(nameid));
    }

    @OnClick({R.id.generalButton1, R.id.generalButton2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.generalButton1:
                user.setGnameid(generalEditText.getText().toString());
                dao.insert(user);
                generalTextView2.setText(dao.queryGeneral(nameid));
                break;
            case R.id.generalButton2:
                dao.deleteBySNameid(nameid,generalEditText.getText().toString());
                generalTextView2.setText(dao.queryGeneral(nameid));
                break;
        }
    }
}
