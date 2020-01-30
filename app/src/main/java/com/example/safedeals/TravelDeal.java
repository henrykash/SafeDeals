package com.example.safedeals;

import java.io.Serializable;

public class TravelDeal implements Serializable {
    private String id;
    private  String title;
    private String price;
    private  String description;
    private String imageUrl;

 //add this empty constructor after changing the list activity as the laucher activity

     public TravelDeal(){}

    public TravelDeal ( String title, String price, String description, String imageUrl) {
        this.setId(id);
        this.setTitle(title);
        this.setPrice(price);
        this.setDescription(description);
        this.setImageUrl(imageUrl);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
