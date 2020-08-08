package com.example.orderfoodadmin.model;

public class Admin {

    private String PhoneNumber;
    private String username_admin;
    private String password_admin;

    public Admin() {
    }

    public Admin(String phoneNumber, String username_admin, String password_admin) {
        PhoneNumber = phoneNumber;
        this.username_admin = username_admin;
        this.password_admin = password_admin;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getUsername_admin() {
        return username_admin;
    }

    public void setUsername_admin(String username_admin) {
        this.username_admin = username_admin;
    }

    public String getPassword_admin() {
        return password_admin;
    }

    public void setPassword_admin(String password_admin) {
        this.password_admin = password_admin;
    }
}
