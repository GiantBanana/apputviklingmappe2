package com.example.isak.s311516mappe2giantbananatech;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ViewBookingDialog extends Dialog {

    Activity activity;
    Booking booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking_dialog);
    }

    ViewBookingDialog(Activity activity, Booking booking){
        super(activity);
        this.activity = activity;
        this.booking = booking;
    }

    ViewBookingDialog(Activity activity){
        super(activity);
        this.activity = activity;
    }

}
