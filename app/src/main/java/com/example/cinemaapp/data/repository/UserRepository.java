package com.example.cinemaapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cinemaapp.data.api.ApiCallback;
import com.example.cinemaapp.data.api.AuthApi;
import com.example.cinemaapp.data.api.LoginRequest;
import com.example.cinemaapp.data.api.LoginResponse;
import com.example.cinemaapp.data.api.RegisterRequest;
import com.example.cinemaapp.data.api.RegisterResponse;
import com.example.cinemaapp.data.api.RetrofitClient;
import com.example.cinemaapp.data.api.TokenManager;
import com.example.cinemaapp.data.model.Snack;
import com.example.cinemaapp.data.model.User;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private final AuthApi authApi;


    public UserRepository() {
        authApi = RetrofitClient.getInstanceWithoutToken().create(AuthApi.class);
    }

    public void register(RegisterRequest request , ApiCallback<RegisterResponse> callback){
        authApi.register(request).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    try {
                        // Lire et afficher le corps de la réponse d'erreur
                        String errorResponse = response.errorBody() != null ? response.errorBody().string() : "No error body";
                        System.out.println("Response body: " + errorResponse);
                        callback.onError("Failed to registre: " + errorResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onError("Error reading error response body: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void login(LoginRequest request, ApiCallback<LoginResponse> callback){
        //System.out.println("Login request: " + request.getEmail() + ", " + request.getPassword() + ",");
        authApi.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    try {
                        // Lire et afficher le corps de la réponse d'erreur
                        String errorResponse = response.errorBody() != null ? response.errorBody().string() : "No error body";
                        System.out.println("Response body: " + errorResponse);
                        callback.onError("Failed to login: " + errorResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onError("Error reading error response body: " + e.getMessage());
                    }
                }
            }


            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

}
