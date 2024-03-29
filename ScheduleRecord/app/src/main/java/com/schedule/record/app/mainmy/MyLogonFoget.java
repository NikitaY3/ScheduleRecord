package com.schedule.record.app.mainmy;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.schedule.record.app.R;
import com.schedule.record.app.utils.HttpGetUtils;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyLogonFoget extends AppCompatActivity {
    @BindView(R.id.forTestButton)
    Button forTestButton;
    private EditText cityCode;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_logon_forget);
        ButterKnife.bind(this);


        cityCode = findViewById(R.id.forTestEdit1);
        tv = findViewById(R.id.forTestText1);

    }


    @OnClick(R.id.forTestButton)
    public void onViewClicked() {
        String baseUrl = "http://120.77.222.242:10024/user/findbyid?nameId=13348445363&password=11.html";
        String params = baseUrl + cityCode.getText().toString() + ".html";
        new MyTask().execute(params);
    }

    @SuppressLint("StaticFieldLeak")
    class MyTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                return HttpGetUtils.getJson(params[0]);
            } catch (Exception ignored) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                String res = HttpGetUtils.parseUserJson(s,MyLogonFoget.this);
                tv.setText(res);
            } catch (JSONException ignored) {

            }
        }
    }
}