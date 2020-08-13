package com.example.orderfoodadmin.model;

import com.google.firebase.database.Exclude;

public class Category {
        private String Name;
        private String Image;

    public Category(String key) {
        Key = key;
    }

    private String Key;
        public Category() {
        }

        public Category(String name, String image) {
            Name = name;
            Image = image;
        }

    public String getKey() {
        return Key;
    }
    
    public void setKey(String key) {
        Key = key;
    }

    public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getImage() {
            return Image;
        }

        public void setImage(String image) {
            Image = image;
        }
}