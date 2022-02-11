package com.example.parkingmanager;

public class BookingObject {
    String slot;
    String email;
    String password;

    public BookingObject() {
    }

    public BookingObject(String slot, String email, String password) {
        this.slot = slot;
        this.email = email;
        this.password = password;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
