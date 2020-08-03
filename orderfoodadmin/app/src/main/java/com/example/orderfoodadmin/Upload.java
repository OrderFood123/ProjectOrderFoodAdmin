package com.example.orderfoodadmin;

public class Upload {

    private String Image;
    private String Name;

    public Upload() {
    }

    public Upload(String mImage, String mName) {
        if (mName.trim().equals("")) {
            mName = "No name";
        }
        Name = mImage;
        Image = mName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}
