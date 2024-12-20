package com.example.cinemaapp.ui.movie;

public class SpinnerItem {
    private int movieId;
    private int sessionId;
    private String displayText;

    public SpinnerItem(int movieId, int sessionId, String displayText) {
        this.movieId = movieId;
        this.sessionId = sessionId;
        this.displayText = displayText;
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

    @Override
    public String toString() {
        return displayText; // Ce qui sera affiché dans le Spinner
    }
}
