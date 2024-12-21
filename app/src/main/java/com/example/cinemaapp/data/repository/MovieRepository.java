package com.example.cinemaapp.data.repository;

import androidx.annotation.NonNull;

import com.example.cinemaapp.data.api.ApiCallback;
import com.example.cinemaapp.data.api.FavResponse;
import com.example.cinemaapp.data.api.MovieApi;
import com.example.cinemaapp.data.model.Movie;
import com.example.cinemaapp.data.api.RetrofitClient;
import com.example.cinemaapp.data.api.TokenManager;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private final MovieApi movieApi;
    private TokenManager tokenManager;

    public MovieRepository(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
        movieApi = RetrofitClient.getInstanceWithToken(tokenManager).create(MovieApi.class);
    }

    public void getMovies(ApiCallback<List<Movie>> callback) {
        movieApi.getAllMovies().enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(@NonNull Call<List<Movie>> call, @NonNull Response<List<Movie>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to load movies: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Movie>> call, @NonNull Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getMovieDetails(int movieId, ApiCallback<Movie> callback) {
        movieApi.getMovieDetails(movieId).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to load movie details: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getFavMovies(ApiCallback<List<Movie>> callback) {
        movieApi.getFavMovies().enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(@NonNull Call<List<Movie>> call, @NonNull Response<List<Movie>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to load movies: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Movie>> call, @NonNull Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void addFavMovie(int movieId, ApiCallback<FavResponse> callback) {
        movieApi.addFavMovie(movieId).enqueue(new Callback<FavResponse>() {
            @Override
            public void onResponse(@NonNull Call<FavResponse> call, @NonNull Response<FavResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    try {
                        // Lire et afficher le corps de la réponse d'erreur
                        String errorResponse = response.errorBody() != null ? response.errorBody().string() : "No error body";
                        System.out.println("Response body: " + errorResponse);
                        callback.onError("Failed to login: " + response.code() + ", Error: " + errorResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onError("Error reading error response body: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<FavResponse> call, @NonNull Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

        public void deleteFavMovie(int movieId, ApiCallback<FavResponse> callback) {
            movieApi.deleteFavMovie(movieId).enqueue(new Callback<FavResponse>() {
                @Override
                public void onResponse(@NonNull Call<FavResponse> call, @NonNull Response<FavResponse> response) {
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body());
                    } else {
                        try {
                            // Lire et afficher le corps de la réponse d'erreur
                            String errorResponse = response.errorBody() != null ? response.errorBody().string() : "No error body";
                            System.out.println("Response body: " + errorResponse);
                            callback.onError("Failed to login: " + response.code() + ", Error: " + errorResponse);
                        } catch (IOException e) {
                            e.printStackTrace();
                            callback.onError("Error reading error response body: " + e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FavResponse> call, @NonNull Throwable t) {
                    callback.onError(t.getMessage());
                }

            });

    }
}
