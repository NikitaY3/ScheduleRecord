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
public class MainMyL3Special extends AppCompatActivity {

    @BindView(R.id.specialEditText)
    EditText specialEditText;
    @BindView(R.id.specialButton1)
    Button specialButton1;
    @BindView(R.id.specialButton2)
    Button specialButton2;
    @BindView(R.id.specialTextView2)
    TextView specialTextView2;

    private List<SpecialSQLiteUser> dataList;
    private SpecialUserSQLite helper;
    String DBName = "special";
    int version = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_l3special);
        ButterKnife.bind(this);

        //取得登录用户的ID
        SharedPreferences sharedPreferences;
        sharedPreferences = this.getSharedPreferences("myuser",MODE_PRIVATE);
        String nameid = sharedPreferences.getString("nameid","");

        helper = new SpecialUserSQLite(MainMyL3Special.this, DBName, null, version);
        SpecialSQLiteUserDao dao = new SpecialSQLiteUserDao(helper);
        specialTextView2.setText(dao.querySpecial(nameid));
    }

    @OnClick({R.id.specialButton1, R.id.specialButton2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.specialButton1:
                break;
            case R.id.specialButton2:
                break;
        }
    }
}
