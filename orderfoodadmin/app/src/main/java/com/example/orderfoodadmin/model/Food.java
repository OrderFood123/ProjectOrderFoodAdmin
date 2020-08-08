package com.example.orderfoodadmin.model;

//import com.google.firebase.database.IgnoreExtraProperties;

public class Food {

    String key;
    String description;
    String discount;
    String image;
    String menuID;
    String name;
    String price;

    public Food() {
    }

    public Food(
            String key,
            String description,
            String discount,
            String image,
            String menuID,
            String name,
            String price
    ) {
        this.key = key;
        this.description = description;
        this.discount = discount;
        this.image = image;
        this.menuID = menuID;
        this.name = name;
        this.price = price;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMenuID() {
        return menuID;
    }

    public void setMenuID(String menuID) {
        this.menuID = menuID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
