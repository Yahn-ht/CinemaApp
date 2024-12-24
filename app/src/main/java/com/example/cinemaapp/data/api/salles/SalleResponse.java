package com.example.cinemaapp.data.api.salles;

import com.example.cinemaapp.data.model.Place;

import java.util.List;

public class SalleResponse {
    private int id;
    private int numero;
    private int nbrePlace;
    private List<SessionResponse> sessions;
    private List<Place> places;

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

    public List<SessionResponse> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionResponse> sessions) {
        this.sessions = sessions;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }
}
