package com.example.cinemaapp.injection;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.cinemaapp.data.api.TokenManager;
import com.example.cinemaapp.data.repository.SnackRepository;
import com.example.cinemaapp.viewmodel.SnackViewModel;

public class SnackViewModelFactory implements ViewModelProvider.Factory {
    private final SnackRepository snackRepository;

    public SnackViewModelFactory(SnackRepository snackRepository) {
        this.snackRepository = snackRepository;
    }

    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SnackViewModel.class)) {
            return (T) new SnackViewModel(snackRepository);
            }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
