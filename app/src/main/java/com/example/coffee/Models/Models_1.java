package com.example.coffee.Models;

public class Models_1 {

    private String title;
    private String description;
    private int id;

    private String image;
    private double price;

    public Models_1(String title, String image, double price,int id,String description) {
        this.title = title;
        this.image = image;
        this.price = price;
        this.id = id;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public double getPrice() {
        return price;
    }
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
