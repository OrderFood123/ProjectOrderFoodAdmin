package com.example.orderfoodadmin.model;

import androidx.annotation.Keep;
@Keep  // when you want to publish your apk to Google play store, because proguard not add some java files
public class User {
    String id, phone,name,password;
    public User() {
    }
    //Shortcut is alt+insert
    public User(String id, String phone, String name, String password) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
