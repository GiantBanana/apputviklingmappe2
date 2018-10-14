package com.example.isak.s311516mappe2giantbananatech;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MainActivity extends ListActivity {
    ArrayList<Restaurant> restaurants;
    ArrayList<String> restaurantName;
    ArrayList<Friend> friends;
    ArrayList<String> friendName;
    ArrayList<Booking> bookings;
    CustomAdapter arrayAdapter;

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
        arrayAdapter = new CustomAdapter(context,android.R.layout.simple_list_item_1,restaurantName);
        getListView().setAdapter(arrayAdapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                getListView().setAdapter(arrayAdapter);
                getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                getListView().setAdapter(arrayAdapter);
                getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Friend friend = getFriend((String) arrayAdapter.getItem(position));
                            showDialog(friend);
                    }
                });
            }
        });

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
                startActivity(intent);
        }
    }

    private Restaurant getRestaurant(String name){
        for(Restaurant restaurant: restaurants){
            Log.d("Names",restaurant.getName());

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

}
