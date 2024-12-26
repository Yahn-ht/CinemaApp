package com.example.cinemaapp.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cinemaapp.data.api.ReservationApi;
import com.example.cinemaapp.data.api.ReservationRequest;
import com.example.cinemaapp.data.api.ReservationResponse;
import com.example.cinemaapp.data.api.RetrofitClient;
import com.example.cinemaapp.data.api.SalleApi;
import com.example.cinemaapp.data.api.TokenManager;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationRepository {
    private  ReservationApi apiService;
    private TokenManager tokenManager;

    public ReservationRepository() {
        this.tokenManager = tokenManager;
        this.apiService = RetrofitClient.getInstanceWithToken(tokenManager).create(ReservationApi.class);
    }

    public LiveData<ReservationResponse> createReservation(ReservationRequest request) {
        MutableLiveData<ReservationResponse> data = new MutableLiveData<>();
        apiService.createReservation(request).enqueue(new Callback<ReservationResponse>() {
            @Override
            public void onResponse(Call<ReservationResponse> call, Response<ReservationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else {
                    data.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ReservationResponse> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }
}

