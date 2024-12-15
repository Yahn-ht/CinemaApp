package com.example.cinemaapp.data.repository;

import com.example.cinemaapp.data.api.ApiCallback;
import com.example.cinemaapp.data.api.AuthApi;
import com.example.cinemaapp.data.api.LoginRequest;
import com.example.cinemaapp.data.api.LoginResponse;
import com.example.cinemaapp.data.api.RegisterRequest;
import com.example.cinemaapp.data.api.RegisterResponse;
import com.example.cinemaapp.data.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private final AuthApi authApi;

    public UserRepository() {
        authApi = RetrofitClient.getInstance().create(AuthApi.class);
    }

    public void register(RegisterRequest request , ApiCallback<RegisterResponse> callback){
        authApi.register(request).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to register: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void login(LoginRequest request, ApiCallback<LoginResponse> callback){
        authApi.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to login: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
