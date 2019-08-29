package com.schedule.record.app.function.GetFunctions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.schedule.record.app.utils.HttpGetUtils;

import org.json.JSONException;

public class TodayFindAllTask extends AsyncTask<String,Void,String>{

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private Handler uiHandler;

    public TodayFindAllTask(Context context, Handler uiHandler) {
        this.context = context;
        this.uiHandler = uiHandler;
    }

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
            //查询权限表并在函数内存好数据库
            String res = HttpGetUtils.parseTodayFindAllJson(s, context);

        } catch (JSONException ignored) {
        }

        Message msg = new Message();
        msg.what = 4;
        uiHandler.sendMessage(msg);
    }
}