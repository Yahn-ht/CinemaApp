package com.example.cinemaapp.data.api;


import com.example.cinemaapp.data.model.Movie;

import java.util.List;
import retrofit2.http.Query;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MovieApi {
    // Récupérer tous les films
    @GET("/api/movies")
    Call<List<Movie>> getAllMovies();

    // Récupérer les détails d’un film par ID
    @GET("/api/movies/{id}")
    Call<Movie> getMovieDetails(@Path("id") int movieId);

    @GET("/api/favMovie")
    Call<List<Movie>> getFavMovies();

    @POST("/api/favoryAdd/{id}")
    Call<FavResponse> addFavMovie(@Path("id") int movieId);

    @POST("/api/favoryRemove/{id}")
    Call<FavResponse> deleteFavMovie(@Path("id") int movieId);

    @GET("/api/movies/search")
    Call<List<Movie>> searchMovies(
                @Query("searchKey") String searchKey,
                @Query("page") int page,
                @Query("limit") int limit
    );
}
