package com.example.isak.s311516mappe2giantbananatech;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.List;

public class CustomAdapter extends ArrayAdapter {


    public CustomAdapter(Context context, int resource, Object[] objects) {
        super(context, resource, objects);
    }

    public CustomAdapter( Context context, int resource, int textViewResourceId,  Object[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public CustomAdapter(Context context, int resource,  List objects) {
        super(context, resource, objects);
    }

    public CustomAdapter(Context context, int resource, int textViewResourceId,  List objects) {
        super(context, resource, textViewResourceId, objects);
    }


    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }
}
