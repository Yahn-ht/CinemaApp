package com.example.cinemaapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemaapp.data.api.ReservationRequest;
import com.example.cinemaapp.data.api.ReservationResponse;
import com.example.cinemaapp.data.model.Snack;
import com.example.cinemaapp.data.repository.ReservationRepository;

import java.util.List;

public class ReservationViewModel extends ViewModel {
    private final ReservationRepository repository;
    private final MutableLiveData<ReservationResponse> reservationResponse = new MutableLiveData<>();
    private final MutableLiveData<List<ReservationResponse>> reservations = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();

    public ReservationViewModel(ReservationRepository repository) {
        this.repository = repository;
        fetchReservations();
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoadingLiveData;
    }

    public LiveData<List<ReservationResponse>> getReservations() {
        return reservations;
    }

    public void fetchReservations() {
        isLoadingLiveData.setValue(true);
        repository.getUserReservations().observeForever(
                responses -> {
                    isLoadingLiveData.setValue(false);
                    reservations.postValue(responses);
                });
    }

    public LiveData<ReservationResponse> getReservationResponse() {
        return reservationResponse;
    }

    public void createReservation(ReservationRequest request) {
        isLoadingLiveData.setValue(true);
        repository.createReservation(request).observeForever(
                response -> {
                    isLoadingLiveData.setValue(false);
                    reservationResponse.postValue(response);
                }
        );
    }

}

