package com.example.isak.s311516mappe2giantbananatech;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    ArrayList<Restaurant> restaurants;
    ArrayList<String> restaurantName;
    ArrayList<Friend> friends;
    ArrayList<String> friendName;
    ArrayList<Booking> bookings;
    ArrayList<String> bookingDescription;
    CustomAdapter arrayAdapter;
    ListView mainList;

    String activeList;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        Context context = getApplicationContext();
        activeList = "restaurants";
        restaurants = DatabaseHandler.DataProccessing.getRestaurants(context);
        restaurantName = DatabaseHandler.DataProccessing.getRestaurantsNames(context);
        friends = DatabaseHandler.DataProccessing.getFriends(context);
        friendName = DatabaseHandler.DataProccessing.getFriendsNames(context);
        bookings = DatabaseHandler.DataProccessing.getBookings(context);
        bookingDescription = DatabaseHandler.DataProccessing.getBookingDescriptions(context);
        arrayAdapter = new CustomAdapter(context,android.R.layout.simple_list_item_1,restaurantName);
        mainList = findViewById(R.id.main_list);
        mainList.setAdapter(arrayAdapter);
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant restaurant = getRestaurant((String) arrayAdapter.getItem(position));
                showDialog(restaurant);
            }
        });

        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(activeList);
            }
        });

        findViewById(R.id.restaurants).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeList = "restaurants";
                Context context = getApplicationContext();
                arrayAdapter = new CustomAdapter(context,android.R.layout.simple_list_item_1,restaurantName);
                mainList.setAdapter(arrayAdapter);
                mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Restaurant restaurant = getRestaurant((String) arrayAdapter.getItem(position));
                        showDialog(restaurant);
                    }
                });
            }
        });

        findViewById(R.id.friends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeList = "friends";
                Context context = getApplicationContext();
                arrayAdapter = new CustomAdapter(context,android.R.layout.simple_list_item_1,friendName);
                mainList.setAdapter(arrayAdapter);
                mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Friend friend = getFriend((String) arrayAdapter.getItem(position));
                            showDialog(friend);
                    }
                });
            }
        });

        findViewById(R.id.bookings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeList = "bookings";
                Context context = getApplicationContext();
                arrayAdapter = new CustomAdapter(context,android.R.layout.simple_list_item_1,bookingDescription);
                mainList.setAdapter(arrayAdapter);
                mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Booking booking = getBooking((String) arrayAdapter.getItem(position));
                        showDialog(booking);
                    }
                });
            }
        });

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Intent intent = new Intent(this,TaskScheduler.class);
        this.startService(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        Context context = getApplicationContext();
        restaurants = DatabaseHandler.DataProccessing.getRestaurants(context);
        restaurantName = DatabaseHandler.DataProccessing.getRestaurantsNames(context);
        friends = DatabaseHandler.DataProccessing.getFriends(context);
        friendName = DatabaseHandler.DataProccessing.getFriendsNames(context);
        bookings = DatabaseHandler.DataProccessing.getBookings(context);
        bookingDescription = DatabaseHandler.DataProccessing.getBookingDescriptions(context);
        switch (activeList){
            case "restaurants":
                activeList = "restaurants";
                arrayAdapter = new CustomAdapter(context,android.R.layout.simple_list_item_1,restaurantName);
                mainList.setAdapter(arrayAdapter);
                mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Restaurant restaurant = getRestaurant((String) arrayAdapter.getItem(position));
                        showDialog(restaurant);
                    }
                });
                break;
            case "friends":
                activeList = "friends";
                arrayAdapter = new CustomAdapter(context,android.R.layout.simple_list_item_1,friendName);
                mainList.setAdapter(arrayAdapter);
                mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Friend friend = getFriend((String) arrayAdapter.getItem(position));
                        showDialog(friend);
                    }
                });
                break;
            case "bookings":
                activeList = "bookings";
                context = getApplicationContext();
                arrayAdapter = new CustomAdapter(context,android.R.layout.simple_list_item_1,bookingDescription);
                mainList.setAdapter(arrayAdapter);
                mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Booking booking = getBooking((String) arrayAdapter.getItem(position));
                        showDialog(booking);
                    }
                });
                break;
        }

    }

    private void showDialog(Restaurant restaurant){
        ViewRestaurantDialog viewRestaurantDialog = new ViewRestaurantDialog(this,restaurant);
        viewRestaurantDialog.show();
    }

    private void showDialog(Friend friend){
        ViewFriendDialog viewFriendDialog = new ViewFriendDialog(this,friend);
        viewFriendDialog.show();
    }

    private void showDialog(Booking booking){
        ViewBookingDialog viewBookingDialog = new ViewBookingDialog(this,booking);
        viewBookingDialog.show();
    }

    private void showDialog(String activeList){
        switch (activeList){
            case "restaurants":
                EditResturantDialog editResturantDialog = new EditResturantDialog(this);
                editResturantDialog.show();
                break;
            case "friends":
                EditFriendDialog editFriendDialog = new EditFriendDialog(this);
                editFriendDialog.show();
                break;
            case "bookings":
                Intent intent = new Intent(this,EditBookingDialog.class);
                Booking booking = null;
                intent.putExtra("booking",booking);
                startActivity(intent);
                break;
        }
    }

    private Restaurant getRestaurant(String name){
        for(Restaurant restaurant: restaurants){
            if(restaurant.getName().equals(name)){
                return restaurant;
            }
        }
        return null;
    }

    private Friend getFriend(String name){
        for(Friend friend: friends){
            if(friend.getName().equals(name)){
                return friend;
            }
        }
        return null;
    }

    private Booking getBooking(String description){
        for(Booking booking: bookings){
            if(booking.getDescription().equals(description));
            return booking;
        }
        return null;
    }

    public void goToSettings(MenuItem menuItem){
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }

}
