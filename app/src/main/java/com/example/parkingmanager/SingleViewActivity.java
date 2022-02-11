package com.example.parkingmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SingleViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_view);

        Intent i = getIntent();

        int position = i.getExtras().getInt("id");
        ParkAdapter parkAdapter = new ParkAdapter(this);

        TextView textView = (TextView) findViewById(R.id.slot);
        textView.setText(parkAdapter.totalPark[position]);
    }
}
