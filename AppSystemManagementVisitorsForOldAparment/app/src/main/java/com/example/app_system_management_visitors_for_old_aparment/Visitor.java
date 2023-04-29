package com.example.app_system_management_visitors_for_old_aparment;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Visitor {
    String name, room_id, visit_time, id_card,date;



    /*getter*/
    public Visitor() {
    }

    public Visitor(String name, String room_id, String visit_time, String id_card) {
        this.name = name;
        this.room_id = room_id;
        this.visit_time = visit_time;
        this.id_card = id_card;
    }

    public Visitor(String name, String room_id, String visit_time, String id_card, String date) {
        this.name = name;
        this.room_id = room_id;
        this.visit_time = visit_time;
        this.id_card = id_card;
        this.date = date;
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public void setVisit_time(String visit_time) {
        this.visit_time = visit_time;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }
    public String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date d=  Calendar.getInstance().getTime();
        //format date to date string
        date=formatter.format(d);
        return date;
    }

    public String getDateValue(){
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    @NonNull
    @Override
    public String toString() {
        return "Visitor{" +
                "name='" + name + '\'' +
                ", room_id='" + room_id + '\'' +
                ", visit_time='" + visit_time + '\'' +
                ", id_card='" + id_card + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
