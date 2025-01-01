package com.example.cinemaapp.data.model;

import java.io.Serializable;

public class CategorieMovie implements Serializable {
    private int id;
    private String name;

    // Getters et setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

