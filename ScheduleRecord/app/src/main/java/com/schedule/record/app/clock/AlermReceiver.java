package com.schedule.record.app.clock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Calendar;

public class AlermReceiver  extends BroadcastReceiver {
    Context context;
    @Override
    public void onReceive(Context context, Intent intent) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int minute = calendar.get(Calendar.MINUTE);
        CharSequence text = String.valueOf(minute);
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();

        this.context = context;
        String mmm = intent.getStringExtra("music");

        Intent serviceIntent = new Intent(context,AlarmService.class);

        if(mmm.equals("stop")) {
            context.stopService(serviceIntent);
        }
        else {
            serviceIntent.putExtra("music",mmm);
            context.startService(serviceIntent);
        }
    }
}