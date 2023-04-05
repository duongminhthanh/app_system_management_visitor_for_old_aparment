package com.example.app_system_management_visitors_for_old_aparment;

import androidx.annotation.NonNull;

public class Visitor {
    String name,room_id,visit_time;
    String id_card;
    /*getter*/

    public String getId_card() {
        return id_card;
    }

    public String getName() {
        return name;
    }

    public String getRoom_id() {
        return room_id;
    }

    public String getVisit_time() {
        return visit_time;
    }

    @NonNull
    @Override
    public String toString() {
        return "Visitor{" +
                "id_card='" + id_card + '\'' +
                ", name='" + name + '\'' +
                ", room_id='" + room_id + '\'' +
                ", visit_time='" + visit_time + '\'' +
                '}';
    }
}
