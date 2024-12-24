package com.example.cinemaapp.data.api;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ReservationRequest implements Serializable {

    private List<Integer> places; // IDs des places sélectionnées
    private Map<Integer, Integer> snacks; // IDs des snacks et leurs quantités
    private int movie; // ID du film


    // Getters et Setters
    public List<Integer> getPlaces() {
        return places;
    }

    public void setPlaces(List<Integer> places) {
        this.places = places;
    }

    public Map<Integer, Integer> getSnacks() {
        return snacks;
    }

    public void setSnacks(Map<Integer, Integer> snacks) {
        this.snacks = snacks;
    }

    public int getMovie() {
        return movie;
    }

    public void setMovie(int movie) {
        this.movie = movie;
    }

    // Méthode toString pour le debug
    @Override
    public String toString() {
        return "ReservationRequest{" +
                "places=" + places +
                ", snacks=" + snacks +
                ", movie=" + movie +
                '}';
    }
}

