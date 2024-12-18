package com.example.cinemaapp.data.api;

import com.example.cinemaapp.data.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieApi {
    // Récupérer tous les films
    @GET("/api/movies")
    Call<List<Movie>> getAllMovies();

    // Récupérer les détails d’un film par ID
    @GET("/api/movies/{id}")
    Call<Movie> getMovieDetails(@Path("id") int movieId);
}
