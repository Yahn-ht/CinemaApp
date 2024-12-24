package com.example.cinemaapp.data.model;

public class Place {
    private int id;
    private int numero;
    private boolean isReserve;
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

    public boolean isReserve() {
        return isReserve;
    }

    public void setReserve(boolean reserve) {
        isReserve = reserve;
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
}
