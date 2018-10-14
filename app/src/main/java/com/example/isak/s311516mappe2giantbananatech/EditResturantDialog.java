package com.example.isak.s311516mappe2giantbananatech;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

public class EditResturantDialog extends Dialog {
    Activity activity;
    Restaurant restaurant;
    EditText name;
    EditText address;
    EditText phone;
    EditText type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_resturant_dialog);
        name = findViewById(R.id.edit_res_name);
        address = findViewById(R.id.edit_res_address);
        phone = findViewById(R.id.edit_res_phone);
        type = findViewById(R.id.edit_res_type);
        if(restaurant != null){
            name.setText(restaurant.getName());
            address.setText(restaurant.getAddress());
            phone.setText(restaurant.getPhone());
            type.setText(restaurant.getType());
            findViewById(R.id.edit_res_save).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remodelRestaurant();
                }
            });
        }else {
            findViewById(R.id.edit_res_save).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buildRestaurant();
                }
            });
        }
        findViewById(R.id.edit_res_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }



    EditResturantDialog( Activity activity, Restaurant restaurant) {
        super(activity);
        this.activity = activity;
        this.restaurant = restaurant;
    }

    EditResturantDialog( Activity activity) {
        super(activity);
        this.activity = activity;
        this.restaurant = null;
    }

    private void buildRestaurant(){
        restaurant = new Restaurant();
        restaurant.setName(name.getText().toString());
        restaurant.setAddress(address.getText().toString());
        restaurant.setPhone(phone.getText().toString());
        restaurant.setType(type.getText().toString());

        Context context = getContext();
        DatabaseHandler.DataProccessing.insertRestaurant(restaurant,context);
        activity.recreate();
        dismiss();
    }

    private void remodelRestaurant(){
        restaurant.setName(name.getText().toString());
        restaurant.setAddress(address.getText().toString());
        restaurant.setPhone(phone.getText().toString());
        restaurant.setType(type.getText().toString());

        Context context = getContext();
        DatabaseHandler.DataProccessing.updateRestaurant(restaurant,context);
        activity.recreate();
        dismiss();
    }

}
