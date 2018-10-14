package com.example.isak.s311516mappe2giantbananatech;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ViewFriendDialog extends Dialog {
    Activity activity;
    Friend friend;
    TextView name;
    TextView phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_friend_dialog);
        name = findViewById(R.id.view_friend_name);
        phone = findViewById(R.id.view_friend_phone);
        name.setText(friend.getName());
        phone.setText(friend.getPhone());

        findViewById(R.id.view_friend_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditFriendDialog editFriendDialog = new EditFriendDialog(activity,friend);
                editFriendDialog.show();
                dismiss();
            }
        });

        findViewById(R.id.view_friend_x_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        findViewById(R.id.view_friend_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();
                DatabaseHandler.DataProccessing.deleteFriend(friend,context);
                activity.recreate();
                dismiss();
            }
        });
    }

    public ViewFriendDialog(Activity activity, Friend friend) {
        super(activity);
        this.activity = activity;
        this.friend = friend;
    }
}
