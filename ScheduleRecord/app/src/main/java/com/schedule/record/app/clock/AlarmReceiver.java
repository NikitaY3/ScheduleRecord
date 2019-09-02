package com.schedule.record.app.clock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    private Context context;
    private Intent intent;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;

        ControlService();
    }

    private void ControlService() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int minute = calendar.get(Calendar.MINUTE);
        CharSequence text = String.valueOf(minute);
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();

        String mmm = intent.getStringExtra("music");

        Intent serviceIntent = new Intent(context, AlarmService.class);

        if(mmm.equals("stop")) {
            context.stopService(serviceIntent);
        }
        else {
            serviceIntent.putExtra("music",mmm);
            context.startService(serviceIntent);
        }
    }
}