package com.example.app_system_management_visitors_for_old_aparment;

import androidx.annotation.NonNull;

public class Apartment {
    String owner_name,owner_phone,room_id;

    public Apartment() {
        this.room_id=getRoom_id();
        this.owner_name=getOwner_name();
        this.owner_phone=getOwner_phone();
    }

    public Apartment(String owner_name, String owner_phone, String room_id) {
        this.owner_name = owner_name;
        this.owner_phone = owner_phone;
        this.room_id = room_id;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public String getOwner_phone() {
        return owner_phone;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public void setOwner_phone(String owner_phone) {
        this.owner_phone = owner_phone;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    @NonNull
    @Override
    public String toString() {
        return "Apartment{" +
                "owner_name='" + owner_name + '\'' +
                ", owner_phone='" + owner_phone + '\'' +
                ", room_id='" + room_id + '\'' +
                '}';
    }
}
