package com.example.cinemaapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cinemaapp.data.api.RetrofitClient;
import com.example.cinemaapp.data.api.SnackApi;
import com.example.cinemaapp.data.api.TokenManager;
import com.example.cinemaapp.data.model.Snack;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class SnackRepository {
    private TokenManager tokenManager;
    private final SnackApi snackApi;

    public SnackRepository() {
        snackApi = RetrofitClient.getInstanceWithToken(tokenManager).create(SnackApi.class);
    }

    public LiveData<List<Snack>> getSnacks() {
        MutableLiveData<List<Snack>> snacksLiveData = new MutableLiveData<>();
        snackApi.getSnacks().enqueue(new Callback<List<Snack>>() {
            @Override
            public void onResponse(@NonNull Call<List<Snack>> call, @NonNull Response<List<Snack>> response) {
                if (response.isSuccessful()) {
                    snacksLiveData.setValue(response.body());
                } else {
                    snacksLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Snack>> call, @NonNull Throwable t) {
                snacksLiveData.setValue(null);
            }
        });
        return snacksLiveData;
    }
}
