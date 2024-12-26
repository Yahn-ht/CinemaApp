package com.example.cinemaapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cinemaapp.data.api.ReservationApi;
import com.example.cinemaapp.data.api.ReservationRequest;
import com.example.cinemaapp.data.api.ReservationResponse;
import com.example.cinemaapp.data.api.RetrofitClient;
import com.example.cinemaapp.data.api.SalleApi;
import com.example.cinemaapp.data.api.TokenManager;
import com.google.gson.Gson;


import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationRepository {
    private  ReservationApi apiService;
    private TokenManager tokenManager;

    public ReservationRepository(TokenManager tokenManager) {
        this.apiService = RetrofitClient.getInstanceWithToken(tokenManager).create(ReservationApi.class);
    }

    public LiveData<ReservationResponse> createReservation(ReservationRequest request) {
        MutableLiveData<ReservationResponse> data = new MutableLiveData<>();
        apiService.createReservation(request).enqueue(new Callback<ReservationResponse>() {
            @Override
            public void onResponse(Call<ReservationResponse> call, Response<ReservationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    System.out.println("Réponse réussie : " + new Gson().toJson(response.body()));
                    data.postValue(response.body());
                } else {
                    System.out.println("Erreur API : Code " + response.code() + " Message : " + response.message());
                    try {
                        if (response.errorBody() != null) {
                            System.out.println("Erreur API corps : " + response.errorBody().string());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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

