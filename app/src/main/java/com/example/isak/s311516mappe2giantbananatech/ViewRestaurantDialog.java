package com.example.isak.s311516mappe2giantbananatech;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ViewRestaurantDialog extends Dialog {
    public Activity activity;
    public TextView name;
    public TextView address;
    public TextView phone;
    public TextView type;
    Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_restaurant_dialog);
        name = findViewById(R.id.view_res_name);
        address = findViewById(R.id.view_res_address);
        phone = findViewById(R.id.view_res_phone);
        type = findViewById(R.id.view_res_type);
        name.setText(restaurant.getName());
        address.setText(restaurant.getAddress());
        phone.setText(restaurant.getPhone());
        type.setText(restaurant.getType());

        findViewById(R.id.view_res_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditResturantDialog settingsDialog =  new EditResturantDialog(activity, restaurant);
                settingsDialog.show();
                dismiss();
            }
        });

        findViewById(R.id.view_res_x_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        findViewById(R.id.view_res_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();
                DatabaseHandler.DataProccessing.deleteRestaurant(restaurant,context);
                activity.recreate();
                dismiss();
            }
        });
        findViewById(R.id.view_res_new_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,EditBookingDialog.class);
                activity.startActivity(intent);
            }
        });
    }

    public ViewRestaurantDialog(Activity activity, Restaurant restaurant) {
        super(activity);
        this.activity = activity;
        this.restaurant = restaurant;
    }
}
