package com.example.cinemaapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemaapp.data.repository.MovieRepository;
import com.example.cinemaapp.data.api.ApiCallback;
import com.example.cinemaapp.data.model.Movie;

import java.util.List;

public class MovieViewModel extends ViewModel {

    private final MovieRepository movieRepository;
    private final MutableLiveData<List<Movie>> moviesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Movie> movieDetailsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Movie> firstMovieLiveData = new MutableLiveData<>();

    public MovieViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

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

    public void loadMovies() {
        movieRepository.getMovies(new ApiCallback<List<Movie>>() {
            @Override
            public void onSuccess(List<Movie> movies) {
                firstMovieLiveData.setValue(movies.get(movies.size()-1));
                moviesLiveData.setValue(movies);
            }

            @Override
            public void onError(String errorMessage) {
                errorLiveData.setValue(errorMessage);
            }
        });
    }

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
}
