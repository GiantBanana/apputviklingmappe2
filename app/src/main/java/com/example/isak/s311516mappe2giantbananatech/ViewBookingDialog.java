package com.example.isak.s311516mappe2giantbananatech;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewBookingDialog extends Dialog {

    Activity activity;
    Booking booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking_dialog);
        TextView description = findViewById(R.id.view_booking_description);
        TextView restaurant = findViewById(R.id.view_booking_restaurant);
        TextView datetime = findViewById(R.id.view_booking_date_time);
        ListView friends = findViewById(R.id.view_booking_friend_list);


        description.setText(booking.getDescription());
        restaurant.setText(booking.getRestaurant());
        datetime.setText(booking.getDate() + " " + booking.getTime());
        ArrayList<String> arrayList = getFriends(booking.getFriends());
        Context context = getContext();
        ArrayAdapter arrayAdapter = new ArrayAdapter(context,android.R.layout.simple_list_item_1,arrayList);
        friends.setAdapter(arrayAdapter);

        findViewById(R.id.view_booking_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,EditBookingDialog.class);
                intent.putExtra("booking",booking);
                activity.startActivity(intent);

            }
        });

        findViewById(R.id.view_booking_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();
                DatabaseHandler.DataProccessing.deleteBooking(booking,context);
                activity.recreate();
                dismiss();
            }
        });

        findViewById(R.id.view_booking_x_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    ViewBookingDialog(Activity activity, Booking booking){
        super(activity);
        this.activity = activity;
        this.booking = booking;
    }

    private static ArrayList<String> getFriends(ArrayList<Friend> friends){
        ArrayList<String> arrayList =  new ArrayList<String>();
        for(Friend friend: friends){
            arrayList.add(friend.getName());
        }
        return arrayList;
    }

}
