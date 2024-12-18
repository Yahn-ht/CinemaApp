package com.example.cinemaapp.data.model;

public class Session {
    private int id;
    private String date;
    private Salle salle;
    private boolean isSupp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public boolean isSupp() {
        return isSupp;
    }

    public void setSupp(boolean supp) {
        isSupp = supp;
    }
}
