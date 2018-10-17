package com.example.isak.s311516mappe2giantbananatech;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Calendar;

public class TaskScheduler extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences (getApplicationContext());

        if(preferences.getBoolean("on_off",false)){
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Calendar calendar = Calendar.getInstance();
            String[] time = preferences.getString("set_time","10:00").split(":");
            Intent service = new Intent(getApplicationContext(),SMSReminderService.class);
            PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(),0,service,PendingIntent.FLAG_ONE_SHOT);
            calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(time[0]));
            calendar.set(Calendar.MINUTE,Integer.parseInt(time[1]));
            calendar.set(Calendar.SECOND,00);
            Log.d("CAAAAAALENDAR",calendar.toString());

            long startTime = calendar.getTimeInMillis();
            if(System.currentTimeMillis() > startTime){
                startTime = startTime + 24*60*60*1000;
            }
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,startTime,AlarmManager.INTERVAL_DAY,pendingIntent);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
