package com.example.cinemaapp.data.model;

public class Salle {
    private int id;
    private int numero;
    private int nbrePlace;
    private boolean isSupp;

    // Getters et setters

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

    public int getNbrePlace() {
        return nbrePlace;
    }

    public void setNbrePlace(int nbrePlace) {
        this.nbrePlace = nbrePlace;
    }

    public boolean isSupp() {
        return isSupp;
    }

    public void setSupp(boolean supp) {
        isSupp = supp;
    }
}
