package com.example.cinemaapp.data.model;

public class Snack {
    private int id;
    private String prix;
    private String picture;
    private String name;
    private Category category;

    // Getters et setters

    public int getId() {
        return id;
    }

    public String getPrix() {
        return prix;
    }

    public String getPicture() {
        return picture;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public static class Category {
        private int id;
        private String name;

        // Getters et setters

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
