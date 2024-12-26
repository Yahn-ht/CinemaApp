package com.example.cinemaapp.injection;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.cinemaapp.data.repository.ReservationRepository;
import com.example.cinemaapp.data.repository.SnackRepository;
import com.example.cinemaapp.viewmodel.ReservationViewModel;
import com.example.cinemaapp.viewmodel.SnackViewModel;

public class ReservationViewModelFactory implements ViewModelProvider.Factory{
    private final ReservationRepository reservationRepository;

    public ReservationViewModelFactory(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ReservationViewModel.class)) {
            return (T) new ReservationViewModel(reservationRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
