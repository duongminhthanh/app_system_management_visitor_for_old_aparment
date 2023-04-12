package com.example.app_system_management_visitors_for_old_aparment;

import androidx.annotation.NonNull;

public class Account {
    String username,password,pin_code;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPin_code() {
        return pin_code;
    }
    public Account(){}

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account(String pin_code) {
        this.pin_code = pin_code;
    }

    @NonNull
    @Override
    public String toString() {
        return "Apartment{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", pin_code='" + pin_code + '\'' +
                '}';
    }
}
