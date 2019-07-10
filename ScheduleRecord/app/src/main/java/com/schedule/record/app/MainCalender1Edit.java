package com.schedule.record.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.schedule.record.app.function.DaySQLiteUserDao;
import com.schedule.record.app.sqlite.DaySQLite;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainCalender1Edit extends AppCompatActivity {
    @BindView(R.id.editCheckBox1)
    CheckBox editCheckBox1;
    @BindView(R.id.editEditText1)
    EditText editEditText1;
    @BindView(R.id.editButton21)
    Button editButton21;
    @BindView(R.id.editButton22)
    Button editButton22;
    @BindView(R.id.editButton23)
    Button editButton23;
    @BindView(R.id.editButton24)
    Button editButton24;
    @BindView(R.id.editRadioGroup)
    RadioGroup editRadioGroup;
    @BindView(R.id.editButton41)
    TextView editButton41;
    @BindView(R.id.editEditText2)
    EditText editEditText2;
    @BindView(R.id.editImageButton1)
    ImageButton editImageButton1;
    @BindView(R.id.editImageButton2)
    ImageButton editImageButton2;
    @BindView(R.id.editImageButton3)
    ImageButton editImageButton3;

    private DaySQLite helper;
    String DBName="day_1";
    int version=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_calendar_mode1_edit);
        ButterKnife.bind(this);


        helper=new DaySQLite(this,DBName,null,version);
        helper.getReadableDatabase();


    }

    @OnClick({R.id.editButton21, R.id.editButton22, R.id.editButton23, R.id.editButton24, R.id.editImageButton1, R.id.editImageButton2, R.id.editImageButton3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.editButton21:
                break;
            case R.id.editButton22:
                break;
            case R.id.editButton23:
                break;
            case R.id.editButton24:
                break;
            case R.id.editImageButton1:
                DaySQLiteUserDao dao=new DaySQLiteUserDao(helper);
                Toast.makeText(this,dao.queryAll(),Toast.LENGTH_SHORT).show();
                String data ;
//                data = dao.queryBydayid("11");
                data = dao.queryAll();
                editEditText2.setText(data);
                Toast.makeText(this,"添加图书信息成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.editImageButton2:
                break;
            case R.id.editImageButton3:
                break;
        }
    }
}
