package com.example.isak.s311516mappe2giantbananatech;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditFriendDialog extends Dialog {
    Activity activity;
    Friend friend;
    EditText name;
    EditText phone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friend_dialog);
        name = findViewById(R.id.edit_friend_name);
        phone = findViewById(R.id.edit_friend_phone);

        if (friend != null){
            name.setText(friend.getName());
            phone.setText(friend.getPhone());
            findViewById(R.id.edit_friend_save).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rebuildFriend();
                }
            });
        }else {
            findViewById(R.id.edit_friend_save).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buildFriend();
                }
            });
        }

        findViewById(R.id.edit_friend_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    EditFriendDialog(Activity activity, Friend friend) {
        super(activity);
        this.activity = activity;
        this.friend = friend;
    }

    EditFriendDialog(Activity activity) {
        super(activity);
        this.activity = activity;
        this.friend = null;
    }

    private void buildFriend(){
        friend = new Friend();
        friend.setName(name.getText().toString());
        friend.setPhone(phone.getText().toString());

        Context context = getContext();
        DatabaseHandler.DataProccessing.insertFriend(friend,context);
        activity.recreate();
        dismiss();
    }

    private void rebuildFriend(){
        friend.setName(name.getText().toString());
        friend.setPhone(phone.getText().toString());

        Context context = getContext();
        DatabaseHandler.DataProccessing.updateFriend(friend,context);
        activity.recreate();
        dismiss();
    }


}
