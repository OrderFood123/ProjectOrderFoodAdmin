package com.example.orderfoodadmin.model;

public class Upload {

    private String Image;
    private String Name;

    public Upload() {
    }

    public Upload(String name, String image) {
        Name = name;
        Image = image;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
