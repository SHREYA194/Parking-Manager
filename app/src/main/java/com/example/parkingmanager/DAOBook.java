package com.example.parkingmanager;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOBook {
    private DatabaseReference databaseReference;

    public DAOBook() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(BookingObject.class.getSimpleName());
    }

    public Task<Void> add(BookingObject obj){
        return databaseReference.child(obj.getSlot()).setValue(obj);

    }
    public Task<Void> update(String key, HashMap<String,Object> hashMap){
        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> remove(String key){
        return databaseReference.child(key).removeValue();
    }
}
