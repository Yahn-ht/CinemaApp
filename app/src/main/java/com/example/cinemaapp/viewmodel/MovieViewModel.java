package com.example.cinemaapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemaapp.data.api.ApiCallback;
import com.example.cinemaapp.data.api.FavResponse;
import com.example.cinemaapp.data.model.Movie;
import com.example.cinemaapp.data.repository.MovieRepository;

import java.util.List;

public class MovieViewModel extends ViewModel {

    private final MovieRepository movieRepository;

    // LiveData pour les films
    private final MutableLiveData<List<Movie>> moviesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Movie> movieDetailsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Movie> firstMovieLiveData = new MutableLiveData<>();

    // LiveData pour les favoris
    private final MutableLiveData<List<Movie>> favoriteMoviesLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> successMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public MovieViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Getter pour les films
    public LiveData<List<Movie>> getMovies() {
        return moviesLiveData;
    }

    public LiveData<Movie> getFirstMovie() {
        return firstMovieLiveData;
    }

    public LiveData<Movie> getMovieDetails() {
        return movieDetailsLiveData;
    }

    public LiveData<String> getError() {
        return errorLiveData;
    }

    // Getter pour les favoris
    public LiveData<List<Movie>> getFavoriteMovies() {
        return favoriteMoviesLiveData;
    }

    public LiveData<String> getSuccessMessage() {
        return successMessageLiveData;
    }

    // Charger tous les films
    public void loadMovies() {
        movieRepository.getMovies(new ApiCallback<List<Movie>>() {
            @Override
            public void onSuccess(List<Movie> movies) {
                firstMovieLiveData.setValue(movies.get(movies.size() - 1));
                moviesLiveData.setValue(movies);
            }

            @Override
            public void onError(String errorMessage) {
                errorLiveData.setValue(errorMessage);
            }
        });
    }

    // Charger les détails d'un film
    public void loadMovieDetails(int movieId) {
        movieRepository.getMovieDetails(movieId, new ApiCallback<Movie>() {
            @Override
            public void onSuccess(Movie movie) {
                movieDetailsLiveData.setValue(movie);
            }

            @Override
            public void onError(String errorMessage) {
                errorLiveData.setValue(errorMessage);
            }
        });
    }

    // Charger les films favoris
    public void loadFavoriteMovies() {
        movieRepository.getFavMovies(new ApiCallback<List<Movie>>() {
            @Override
            public void onSuccess(List<Movie> movies) {
                favoriteMoviesLiveData.setValue(movies);
            }

            @Override
            public void onError(String errorMessage) {
                errorLiveData.setValue(errorMessage);
            }
        });
    }

    // Ajouter un film aux favoris
    public void addFavoriteMovie(int movieId) {
        movieRepository.addFavMovie(movieId, new ApiCallback<FavResponse>() {
            @Override
            public void onSuccess(FavResponse response) {
                successMessageLiveData.setValue(response.getMessage());
                loadFavoriteMovies(); // Met à jour la liste des favoris
            }

            @Override
            public void onError(String errorMessage) {
                errorLiveData.setValue(errorMessage);
            }
        });
    }

    // Supprimer un film des favoris
    public void removeFavoriteMovie(int movieId) {
        movieRepository.deleteFavMovie(movieId, new ApiCallback<FavResponse>() {
            @Override
            public void onSuccess(FavResponse response) {
                successMessageLiveData.setValue(response.getMessage());
                loadFavoriteMovies(); // Met à jour la liste des favoris
            }

            @Override
            public void onError(String errorMessage) {
                errorLiveData.setValue(errorMessage);
            }
        });
    }

    public boolean isFavorite(Movie movie) {
        List<Movie> currentFavorites = favoriteMoviesLiveData.getValue();
        return currentFavorites != null && currentFavorites.contains(movie);
    }
}
