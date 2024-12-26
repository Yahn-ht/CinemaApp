package com.example.cinemaapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemaapp.data.api.ReservationRequest;
import com.example.cinemaapp.data.api.ReservationResponse;
import com.example.cinemaapp.data.repository.ReservationRepository;

public class ReservationViewModel extends ViewModel {
    private final ReservationRepository repository;
    private final MutableLiveData<ReservationResponse> reservationResponse = new MutableLiveData<>();

    public ReservationViewModel() {
        this.repository = new ReservationRepository();
    }

    public LiveData<ReservationResponse> getReservationResponse() {
        return reservationResponse;
    }

    public void createReservation(ReservationRequest request) {
        repository.createReservation(request).observeForever(reservationResponse::postValue);
    }
}

