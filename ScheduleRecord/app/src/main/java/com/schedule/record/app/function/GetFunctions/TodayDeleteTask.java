package com.schedule.record.app.function.GetFunctions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.schedule.record.app.utils.HttpGetUtils;

import org.json.JSONException;

public class TodayDeleteTask extends AsyncTask<String,Void,String>{

    @SuppressLint("StaticFieldLeak")
    private Context context;

    public TodayDeleteTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String res = HttpGetUtils.getJson(params[0]);
            return  res;
        } catch (Exception e) {
        }
        return null;
    }
}