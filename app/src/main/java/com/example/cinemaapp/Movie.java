package com.example.cinemaapp;

public class Movie {
    private String title;
    private String description;
    private int imageResId;


    public Movie(String title,String description, int imageResId) {
        this.description = description;
        this.title = title;
        this.imageResId = imageResId;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResId() {
        return imageResId;
    }

}
