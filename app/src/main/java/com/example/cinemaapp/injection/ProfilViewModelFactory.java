package com.example.cinemaapp.injection;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.cinemaapp.data.repository.ProfilRepository;
import com.example.cinemaapp.viewmodel.ProfilViewModel;

public class ProfilViewModelFactory implements ViewModelProvider.Factory {

    private final ProfilRepository profilRepository;

    public ProfilViewModelFactory(ProfilRepository profilRepository) {
        this.profilRepository = profilRepository;
    }

    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProfilViewModel.class)) {
            return (T) new ProfilViewModel(profilRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
