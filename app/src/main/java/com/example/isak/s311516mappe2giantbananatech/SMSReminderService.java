package com.example.isak.s311516mappe2giantbananatech;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SMSReminderService extends Service {
    ArrayList<Booking> bookings;
    SharedPreferences preferences;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("SSSSSSSSSSMS","started");
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        bookings = DatabaseHandler.DataProccessing.getBookings(getApplicationContext());
        String date;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,00);
        calendar.set(Calendar.MINUTE,00);
        calendar.set(Calendar.SECOND,00);
        calendar.set(Calendar.MILLISECOND,00);


        long currentDate = calendar.getTimeInMillis();
        long bookingDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        for(Booking booking: bookings){
            date = booking.getDate();
            try {
                calendar.setTime(simpleDateFormat.parse(date));
                bookingDate = calendar.getTimeInMillis();
                Log.d("Tiiiiiiime",bookingDate+"");
                Log.d("Tiiiiiiime",currentDate+"");
                if(currentDate == bookingDate){
                    sendSMSReminder(booking);
                }
            }catch (ParseException e){
                Log.d("Error",e.toString());
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void sendSMSReminder(Booking booking) {
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<Friend> friends = booking.getFriends();

        for(Friend friend: friends){
            smsManager.sendTextMessage(friend.getPhone(),null,preferences.getString("set_text","Remember our plans today"),null,null);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
