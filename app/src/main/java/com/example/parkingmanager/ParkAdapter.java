package com.example.parkingmanager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ParkAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public ParkAdapter(Context c) {
        context = c;
    }

    public ParkAdapter(Context c, List<String> list) {
        context = c;
        this.list = list;
    }

    @Override
    public int getCount() {
        return totalPark.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView;

        if (convertView == null) {
            textView = new TextView(context);
            textView.setLayoutParams(new ViewGroup.LayoutParams(240, 245));
            textView.setPadding(8, 8, 8, 8);
            textView.setGravity(Gravity.CENTER);

            if (list.contains(totalPark[position])) {
                textView.setBackgroundColor(Color.RED);
                textView.setText("BOOKED");
                textView.setTextColor(Color.WHITE);
            }
            else {
                textView.setBackgroundColor(Color.LTGRAY);
                textView.setText(totalPark[position]);
                textView.setTextColor(Color.BLACK);
            }
        }
        else {
            textView = (TextView) convertView;
        }
        //textView.setText(totalPark[position]);
        return textView;
    }

    public String[] totalPark = {
        "slot 1", "slot 2","slot 3","slot 4", "slot 5","slot 6","slot 7", "slot 8","slot 9","slot 10", "slot 11","slot 12","slot 13", "slot 14","slot 15"
    };
}
