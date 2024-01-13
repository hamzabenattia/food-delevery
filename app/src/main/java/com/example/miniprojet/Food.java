package com.example.miniprojet;

import java.io.Serializable;

public class Food implements Serializable {

    private String id;
    private String imageUrl;

    private int imageResource;

    private String title;
    private double price;

    // Required empty constructor for Firebase
    public Food() {
    }

    public Food(String imageUrl, String title, double price) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.price = price;
    }


    // Constructor without id (for adding new products)
    public Food(int imageResource, String title, double price) {
        this.imageResource = imageResource;
        this.title = title;
        this.price = price;
    }




    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
