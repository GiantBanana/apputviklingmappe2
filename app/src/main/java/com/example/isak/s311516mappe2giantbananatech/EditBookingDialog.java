package com.example.isak.s311516mappe2giantbananatech;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditBookingDialog extends FragmentActivity {
    static Activity activity;
    static Booking booking;
    static Restaurant restaurant;
    static ArrayList<Restaurant> restaurants;
    static ArrayList<String> restaurantName;
    static ArrayList<Friend> friends;
    static ArrayList<String> friendName;
    static ArrayList<Booking> bookings;
    static ArrayList<String> addedFriends;
    static ArrayAdapter<String> arrayAdapter;
    static Spinner spinnerFriends;
    static Spinner spinnerRestaurants;
    static TextView dateField;
    static  TextView timeField;
    static EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_booking_dialog);

        Context context = getApplicationContext();
        booking = (Booking) getIntent().getSerializableExtra("booking");
        restaurants = DatabaseHandler.DataProccessing.getRestaurants(context);
        restaurantName = DatabaseHandler.DataProccessing.getRestaurantsNames(context);
        friends = DatabaseHandler.DataProccessing.getFriends(context);
        friendName = DatabaseHandler.DataProccessing.getFriendsNames(context);
        bookings = DatabaseHandler.DataProccessing.getBookings(context);
        addedFriends = new ArrayList<String>();
        dateField = findViewById(R.id.edit_booking_date);
        timeField = findViewById(R.id.edit_booking_time);
        spinnerFriends = findViewById(R.id.edit_booking_friend_spinner);
        spinnerRestaurants = findViewById(R.id.edit_booking_restaurant_spinner);
        description = findViewById(R.id.edit_booking_description);

        ListView addedFriendsList = findViewById(R.id.edit_booking_list_of_friends);

        arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,friendName);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFriends.setAdapter(arrayAdapter);

        findViewById(R.id.edit_booking_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.edit_booking_add_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAddedFriends();
            }
        });

        if (booking!=null){
            description.setText(booking.getDescription());
            dateField.setText(booking.getDate());
            timeField.setText(booking.getTime());
            arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,restaurantName);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerRestaurants.setAdapter(arrayAdapter);
            spinnerRestaurants.setSelection(arrayAdapter.getPosition(booking.getRestaurant()));
            findViewById(R.id.edit_booking_save).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remodelBooking();
                }
            });
            addedFriends = getFriends(booking.getFriends());
            arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,addedFriends);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            addedFriendsList.setAdapter(this.arrayAdapter);

        }else {
            arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,restaurantName);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerRestaurants.setAdapter(arrayAdapter);
            Date date = new Date(System.currentTimeMillis()+86400000);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            dateField.setText(simpleDateFormat.format(date));
            timeField.setText("18:00");
            findViewById(R.id.edit_booking_save).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buildBooking();
                }
            });
            arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,addedFriends);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            addedFriendsList.setAdapter(this.arrayAdapter);
        }

    }

    private static void updateAddedFriends(){
        if (!addedFriends.contains(spinnerFriends.getSelectedItem().toString())){
            addedFriends.add(spinnerFriends.getSelectedItem().toString());
            arrayAdapter.notifyDataSetChanged();
        }
    }


    private void buildBooking(){
        booking = new Booking();
        booking.setDescription(description.getText().toString());
        booking.setRestaurant(spinnerRestaurants.getSelectedItem().toString());
        booking.setDate(dateField.getText().toString());
        booking.setTime(timeField.getText().toString());
        Log.d("Time",booking.getTime());
        ArrayList<Friend> arrayList = new ArrayList<Friend>();
        for(String addedFriend: addedFriends){
            for(Friend friend: friends){
                if(friend.getName().equals(addedFriend)){
                    arrayList.add(friend);
                }
            }
        }
        booking.setFriends(arrayList);

        DatabaseHandler.DataProccessing.insertBooking(booking,this);
        finish();
    }

    private void remodelBooking(){
        booking.setDescription(description.getText().toString());
        booking.setRestaurant(spinnerRestaurants.getSelectedItem().toString());
        booking.setDate(dateField.getText().toString());
        booking.setTime(timeField.getText().toString());
        ArrayList<Friend> arrayList = new ArrayList<Friend>();
        for(String addedFriend: addedFriends){
            for(Friend friend: friends){
                if(friend.getName().equals(addedFriend)){
                    arrayList.add(friend);
                }
            }
        }
        booking.setFriends(arrayList);

        DatabaseHandler.DataProccessing.updateBooking(booking,this);
        finish();
    }

    private static ArrayList<String> getFriends(ArrayList<Friend> friends){
        ArrayList<String> arrayList =  new ArrayList<String>();
        for(Friend friend: friends){
            arrayList.add(friend.getName());
        }
        return arrayList;
    }


    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,true);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            TextView timeField = EditBookingDialog.timeField;
            Calendar calendar = Calendar.getInstance();
            calendar.set(0,0,0,hourOfDay,minute);
            Date time = calendar.getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            timeField.setText(simpleDateFormat.format(time));
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        TextView date;



        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            TextView dateField = EditBookingDialog.dateField;
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,day);
            Date date = calendar.getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            dateField.setText(simpleDateFormat.format(date));
        }
    }


}
