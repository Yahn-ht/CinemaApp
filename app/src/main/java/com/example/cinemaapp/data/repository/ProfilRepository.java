package com.example.cinemaapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cinemaapp.data.api.AuthApi;
import com.example.cinemaapp.data.api.RetrofitClient;
import com.example.cinemaapp.data.api.TokenManager;
import com.example.cinemaapp.data.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilRepository {
    private final AuthApi authApi2;
    private TokenManager tokenManager;

    public ProfilRepository(TokenManager tokenManager) {
        authApi2 = RetrofitClient.getInstanceWithToken(tokenManager).create(AuthApi.class);
    }

    public LiveData<User> getUser() {
        MutableLiveData<User> userLiveData = new MutableLiveData<>();
        authApi2.userInfo().enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    System.out.println("Usere: "+response.body());
                    userLiveData.setValue(response.body());
                } else {
                    System.out.println("usere: "+response.body());
                    userLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                userLiveData.setValue(null);
            }
        });
        return userLiveData;
    }
}
