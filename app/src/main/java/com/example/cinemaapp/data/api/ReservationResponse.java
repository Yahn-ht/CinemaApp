package com.example.cinemaapp.data.api;

import com.example.cinemaapp.data.model.Place;

import java.io.Serializable;
import java.util.List;

public class ReservationResponse implements Serializable {
    private int id;
    private String montant;
    private List<Place> places;
    private User user;
    private String createdAt;
    private List<SnackReservation> snackReservations;
    private Movie movieReserve;

    // Getters and setters...

    public static class User {
        private int id;
        private String name;

        // Getters and setters...
    }

    public static class SnackReservation {
        private int id;
        private Snack snack;
        private int quantity;

        // Getters and setters...
    }

    public static class Snack {
        private int id;
        private String prix;
        private String picture;
        private String name;
        private Category category;

        // Getters and setters...
    }

    public static class Movie {
        private int id;
        private String name;

        // Getters and setters...
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
