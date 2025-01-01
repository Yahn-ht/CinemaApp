package com.example.cinemaapp.ui.movie;

public class SpinnerItem {
    private int movieId;
    private int sessionId;
    private String displayText;
    private int salleId;

    public SpinnerItem(int movieId, int sessionId, String displayText, int salleId) {
        this.movieId = movieId;
        this.sessionId = sessionId;
        this.displayText = displayText;
        this.salleId = salleId;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public String getDisplayText() {
        return displayText;
    }

    public int getSalleId() {
        return salleId;
    }

    @Override
    public String toString() {
        return displayText; // Ce qui sera affiché dans le Spinner
    }
}
