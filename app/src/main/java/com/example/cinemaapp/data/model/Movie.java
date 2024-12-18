package com.example.cinemaapp.data.model;

import java.util.List;

public class Movie {
    private int id;
    private String name;
    private String duree;
    private String description;
    private String image;
    private String authorName;
    private List<Session> session;
    private List<Actor> actors;
    private CategorieMovie categorieMovie;
    private boolean isSupp;

    // Getters et setters


    public Movie() {
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<Session> getSession() {
        return session;
    }

    public void setSession(List<Session> session) {
        this.session = session;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public CategorieMovie getCategorieMovie() {
        return categorieMovie;
    }

    public void setCategorieMovie(CategorieMovie categorieMovie) {
        this.categorieMovie = categorieMovie;
    }

    public boolean isSupp() {
        return isSupp;
    }

    public void setSupp(boolean supp) {
        isSupp = supp;
    }
}