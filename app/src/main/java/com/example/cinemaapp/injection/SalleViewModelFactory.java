package com.example.cinemaapp.injection;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.cinemaapp.data.api.TokenManager;
import com.example.cinemaapp.data.repository.SalleRepository;
import com.example.cinemaapp.viewmodel.SalleViewModel;

public class SalleViewModelFactory implements ViewModelProvider.Factory {
    private final SalleRepository salleRepository;

    public SalleViewModelFactory(SalleRepository salleRepository) {
        this.salleRepository = salleRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SalleViewModel.class)) {
            return (T) new SalleViewModel(salleRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

