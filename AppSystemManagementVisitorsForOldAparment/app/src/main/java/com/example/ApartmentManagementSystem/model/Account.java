package com.example.ApartmentManagementSystem.model;

import androidx.annotation.NonNull;

public class Account {
    String acc_id, username, password, pin_code;

    public String getAcc_id() {
        return acc_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPin_code() {
        return pin_code;
    }

    public void setAcc_id(String acc_id) {
        this.acc_id = acc_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPin_code(String pin_code) {
        this.pin_code = pin_code;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account(String pin_code) {
        this.pin_code = pin_code;
    }

    public Account() {
    }

    public Account(String acc_id, String username, String password, String pin_code) {
        this.acc_id = acc_id;
        this.username = username;
        this.password = password;
        this.pin_code = pin_code;
    }

    @NonNull
    @Override
    public String toString() {
        return "Account{" +
                "acc_id='" + acc_id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", pin_code='" + pin_code + '\'' +
                '}';
    }
}
