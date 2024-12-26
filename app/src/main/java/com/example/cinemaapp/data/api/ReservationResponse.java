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

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<SnackReservation> getSnackReservations() {
        return snackReservations;
    }

    public void setSnackReservations(List<SnackReservation> snackReservations) {
        this.snackReservations = snackReservations;
    }

    public Movie getMovieReserve() {
        return movieReserve;
    }

    public void setMovieReserve(Movie movieReserve) {
        this.movieReserve = movieReserve;
    }

    // Classe interne User
    public static class User implements Serializable {
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

    // Classe interne SnackReservation
    public static class SnackReservation implements Serializable {
        private int id;
        private Snack snack;
        private int quantity;

        // Getters et setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Snack getSnack() {
            return snack;
        }

        public void setSnack(Snack snack) {
            this.snack = snack;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    // Classe interne Snack
    public static class Snack implements Serializable {
        private int id;
        private String prix;
        private String picture;
        private String name;
        private Category category;

        // Getters et setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPrix() {
            return prix;
        }

        public void setPrix(String prix) {
            this.prix = prix;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }
    }

    // Classe interne Movie
    public static class Movie implements Serializable {
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

    // Classe interne Category
    public static class Category implements Serializable {
        private int id;
        private String name;
        private String price; // Ajout de cette propriété pour correspondre au JSON

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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

    public static class Place implements Serializable{
        private int id;
        private int numero;
        private Category category;

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

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public static class Category implements Serializable{
            private int id;
            private String name;
            private String price;

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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
