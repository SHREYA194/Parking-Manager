package com.example.parkingmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = db.getReference("BookingObject");
        List<String> list=new ArrayList<String>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()) {
                    BookingObject key = ds.getValue(BookingObject.class);
                    list.add(key.getSlot());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        DAOBook daoBook = new DAOBook();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_pm_logo_round);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Bundle extras = getIntent().getExtras();
        String email = extras.getString("email");
        String password = extras.getString("password");

        //Toast.makeText(getApplicationContext(),email+"\n"+password, Toast.LENGTH_SHORT).show();

        builder = new AlertDialog.Builder(this);

        GridView gridview = (GridView) findViewById(R.id.gridViewPark);
        gridview.setAdapter(new ParkAdapter(this, list));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id){

                String[] totalPark = {
                        "slot 1", "slot 2","slot 3","slot 4", "slot 5","slot 6","slot 7", "slot 8","slot 9","slot 10", "slot 11","slot 12","slot 13", "slot 14","slot 15"
                };


                builder.setMessage("Do you want to book " + totalPark[position] + " for parking?")
                        .setCancelable(true)
                        .setPositiveButton("BOOK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                                BookingObject obj = new BookingObject(totalPark[position], email, password);
                                daoBook.add(obj).addOnSuccessListener(suc->{
                                    //Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_LONG).show();
                                }).addOnFailureListener(er->{
                                    //Toast.makeText(getApplicationContext(),""+er.getMessage(),Toast.LENGTH_SHORT).show();;
                                });

                                list.add(totalPark[position]);

                                TextView tv = (TextView) v;
                                tv.setBackgroundColor(Color.RED);
                                tv.setText("BOOKED");
                                tv.setTextColor(Color.WHITE);
                                Toast.makeText(getApplicationContext(),"You booked parking " + totalPark[position] + ".", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("UNBOOK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                                //String obj = "-MmNbNM-pQRI0HXTjU50";
                                String obj = databaseReference.child((totalPark[position])).getKey();
                                //Toast.makeText(getApplicationContext(), obj, Toast.LENGTH_SHORT).show();
                                daoBook.remove(obj).addOnSuccessListener(suc->{
                                    //Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_LONG).show();
                                }).addOnFailureListener(er->{
                                    //Toast.makeText(getApplicationContext(),""+er.getMessage(),Toast.LENGTH_SHORT).show();;
                                });

                                list.remove(totalPark[position]);

                                TextView tv = (TextView) v;
                                tv.setBackgroundColor(Color.LTGRAY);
                                tv.setText(totalPark[position]);
                                tv.setTextColor(Color.BLACK);
                                Toast.makeText(getApplicationContext(), "You Unbooked parking " + totalPark[position] + ".", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(), "You do not booked parking slot " + totalPark[position] + ".", Toast.LENGTH_SHORT).show();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.setTitle(totalPark[position]);
                alert.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuLogout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

}