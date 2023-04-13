package com.example.app_system_management_visitors_for_old_aparment;

import androidx.annotation.NonNull;

public class Apartment {
    String owner_name,owner_phone,room_id;

    public String getOwner_name() {
        return owner_name;
    }

    public String getOwner_phone() {
        return owner_phone;
    }

    public String getRoom_id() {
        return room_id;
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
