package com.example.cinemaapp.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cinemaapp.data.api.MovieApi;
import com.example.cinemaapp.data.api.RetrofitClient;
import com.example.cinemaapp.data.api.SalleApi;
import com.example.cinemaapp.data.api.TokenManager;
import com.example.cinemaapp.data.api.salles.SalleResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalleRepository {
    private final SalleApi salleApi;
    private TokenManager tokenManager;

    public SalleRepository(TokenManager tokenManager) {
        salleApi = RetrofitClient.getInstanceWithToken(tokenManager).create(SalleApi.class);
    }

    public LiveData<SalleResponse> getRoomById(int roomId) {
        MutableLiveData<SalleResponse> roomLiveData = new MutableLiveData<>();

        salleApi.getRoomById(roomId).enqueue(new Callback<SalleResponse>() {
            @Override
            public void onResponse(Call<SalleResponse> call, Response<SalleResponse> response) {
                if (response.isSuccessful()) {
                    roomLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SalleResponse> call, Throwable t) {
                // Gérer l'erreur
                roomLiveData.postValue(null);
            }
        });

        return roomLiveData;
    }
}

