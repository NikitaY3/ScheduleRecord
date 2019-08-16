package com.schedule.record.app.mainmy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.schedule.record.app.MainActivity;
import com.schedule.record.app.R;
import com.schedule.record.app.sqlite.GeneralUserSQLite;
import com.schedule.record.app.sqlite.dao.GeneralSQLiteUserDao;
import com.schedule.record.app.sqlite.user.GeneralSQLiteUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyL5NameSet extends AppCompatActivity {

    @BindView(R.id.nameSetView1)
    TextView nameSetView1;
    @BindView(R.id.nameSetButton)
    Button nameSetButton;
    @BindView(R.id.nameSetEditText1)
    EditText nameSetEditText1;

    private GeneralUserSQLite helper;
    private String DBName = "general";
    private int version = 1;
    private String nameid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_zifset_name);
        ButterKnife.bind(this);

        //取得登录用户的ID
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("myuser", MODE_PRIVATE);
        nameid = sharedPreferences.getString("nameid","");

    }

    @OnClick(R.id.nameSetButton)
    public void onViewClicked() {

        //更改用户昵称，放入数据库
        helper=new GeneralUserSQLite(MyL5NameSet.this,DBName,null,version);
        GeneralSQLiteUserDao dao = new GeneralSQLiteUserDao(helper);
        GeneralSQLiteUser user = dao.queryByNameid(nameid);
        user.setName(nameSetEditText1.getText().toString());
        dao.updateAll(user);

//        Intent intent = new Intent(MyL5NameSet.this, MainMyL5Information.class);
//        startActivity(intent);
        finish();
    }
}
