package com.schedule.record.app.function.GetFunctions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.schedule.record.app.utils.HttpGetUtils;

import org.json.JSONException;

public class AuthorityQueryTask extends AsyncTask<String,Void,String>{

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private Handler uiHandler;

    public AuthorityQueryTask(Context context, Handler uiHandler) {
        this.context = context;
        this.uiHandler = uiHandler;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String res= HttpGetUtils.getJson(params[0]);
            return  res;
        } catch (Exception e) {
        }
        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        try {
            //查询权限表并在函数内存好数据库
            String res = HttpGetUtils.parseAuthorityQueryJson(s, context);

            Message msg = new Message();
            msg.what = 2;
            uiHandler.sendMessage(msg);

        } catch (JSONException e) {
        }
    }
}