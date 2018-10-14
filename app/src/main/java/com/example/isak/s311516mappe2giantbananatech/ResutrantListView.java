package com.example.isak.s311516mappe2giantbananatech;

import android.app.Activity;
import android.app.ListActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ResutrantListView extends ListActivity {
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        Resources resources = getResources();
        String[] resturants = resources.getStringArray(R.array.resturants);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getListView().getContext(),android.R.layout.simple_expandable_list_item_1,resturants);
        getListView().setAdapter(arrayAdapter);

    }
}
