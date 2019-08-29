package com.schedule.record.app.function.GetFunctions;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.schedule.record.app.utils.HttpGetUtils;

public class FutureDeleteTask extends AsyncTask<String,Void,String>{

    private Handler uiHandler;

    public FutureDeleteTask(Handler uiHandler) {
        this.uiHandler = uiHandler;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            return HttpGetUtils.getJson(params[0]);
        } catch (Exception ignored) {
        }

        Message msg = new Message();
        msg.what = 33;
        uiHandler.sendMessage(msg);

        return null;
    }
}