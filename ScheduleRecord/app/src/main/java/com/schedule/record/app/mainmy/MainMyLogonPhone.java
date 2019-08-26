package com.schedule.record.app.mainmy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.schedule.record.app.MainActivity;
import com.schedule.record.app.R;
import com.schedule.record.app.sqlite.GeneralUserSQLite;
import com.schedule.record.app.utils.HttpGetUtils;

import org.json.JSONException;

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
    @BindView(R.id.phoneTest)
    TextView phoneTest;
    @BindView(R.id.phoneButton3)
    Button phoneButton3;

    private GeneralUserSQLite helper;
    private String DBName = "general_user";
    private int version = 1;

    private String password;
    private String nameid;

    private SharedPreferences sharedPreferences;

    String baseUrl = "http://120.77.222.242:10024/user/findbyid?nameId=";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_logon_phone);
        ButterKnife.bind(this);

        sharedPreferences = this.getSharedPreferences("myuser", MODE_PRIVATE);
        phoneEditText1.setText(sharedPreferences.getString("nameid", ""));
        phoneEditText2.setText(sharedPreferences.getString("password", ""));
    }

    @OnClick({R.id.phoneButton3, R.id.phoneButton2, R.id.phoneButton1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.phoneButton3:

                Intent intent1 = new Intent(this, MyLogonRegister.class);
                startActivity(intent1);
                MainMyLogonPhone.this.finish();

                break;
            case R.id.phoneButton2:

                Intent intent2 = new Intent(this, MyLogonFoget.class);
                startActivity(intent2);

                break;
            case R.id.phoneButton1:
                nameid = phoneEditText1.getText().toString();
                password = phoneEditText2.getText().toString();
                if (!(nameid.length() == 11)) {
                    Toast.makeText(this, "您输入的手机号有误", Toast.LENGTH_SHORT).show();
                    phoneEditText1.setText("");
                } else {

                    //1、向云端发送账号查询请求
                    //2、如果返回正确账号，保存账号信息到本地数据库和SharedPreferences
                    //3、如果返回“账号密码不匹配”，提醒用户

                    String params = baseUrl + nameid + "&password=" + password;
                    new MyTask().execute(params);

                }
                break;
        }
    }


//    未连接服务端的操作
    private void before(String name) {

        SharedPreferences.Editor myuser = sharedPreferences.edit();
        myuser.putString("nameid", nameid);
        myuser.putString("password", password);
        myuser.putString("name", name);
        myuser.apply();

        SharedPreferences sharedPreferences1 = null;
        sharedPreferences1 = this.getSharedPreferences("delaytime", MODE_PRIVATE);
        SharedPreferences.Editor delaytime = sharedPreferences1.edit();
        delaytime.putString("time", "30分钟");
        delaytime.putString("boxcolock", "true");
        delaytime.putString("mobilecolock", "true");
        delaytime.apply();

        Intent intent2 = new Intent(MainMyLogonPhone.this, MainActivity.class);
        startActivity(intent2);
//                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent2.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                    intent2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
    }


    class MyTask extends AsyncTask<String,Void,String>{
        //根据URL获取json数据
        @Override
        protected String doInBackground(String... params) {
            try {
                String res=HttpGetUtils.getJson(params[0]);
                return  res;
            } catch (Exception e) {
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            try {
                String res = HttpGetUtils.parseUserJson(s,MainMyLogonPhone.this);
                if (!res.equals("账号密码不匹配")) {
                    before(res);
                }else {
                    Toast.makeText(MainMyLogonPhone.this,"账号或密码输入错误",Toast.LENGTH_SHORT).show();
                    phoneEditText1.setText("");
                    phoneEditText2.setText("");
                }

                phoneTest.setText(res);

            } catch (JSONException e) {
            }
        }
    }
}
