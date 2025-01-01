package com.example.cinemaapp.data.model;

import androidx.annotation.NonNull;

import com.example.cinemaapp.data.api.salles.MovieResponse;

import java.io.Serializable;
import java.util.List;

public class Place{
    private int id;
    private int numero;
    private List<Session> reserver;
    private PlaceCategory category;
    private boolean isSupp;
    private boolean isSelected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public PlaceCategory getCategory() {
        return category;
    }

    public void setCategory(PlaceCategory category) {
        this.category = category;
    }

    public boolean isSupp() {
        return isSupp;
    }

    public void setSupp(boolean supp) {
        isSupp = supp;
    }

    public boolean isSelected() {
        return isSelected;
    }

    // Setter pour isSelected
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public List<Session> getSessions() {
        return reserver;
    }

    public void setSessions(List<Session> reserver) {
        this.reserver = reserver;
    }

    public static class Session{
        private int id;
        private String date;
        private MovieResponse movie;
        private boolean isSupp;

        public int getId() {
            return id;
        }

        @NonNull
        @Override
        public String toString() {
            return  "id=" + id ;
        }
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", numero=" + numero +
                ", reserver=" + (reserver != null ? reserver.toString() : "null")+
                ", isSupp=" + isSupp +
                ", isSelected=" + isSelected +
                '}';
    }

}
