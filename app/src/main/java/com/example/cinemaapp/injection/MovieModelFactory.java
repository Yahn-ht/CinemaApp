package com.example.cinemaapp.injection;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.cinemaapp.data.repository.MovieRepository;
import com.example.cinemaapp.viewmodel.MovieViewModel;

public class MovieModelFactory implements ViewModelProvider.Factory {
    private final MovieRepository movieRepository;

    public MovieModelFactory(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieViewModel.class)) {
            return (T) new MovieViewModel(movieRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
