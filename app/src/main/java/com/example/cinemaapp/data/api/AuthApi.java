package com.example.cinemaapp.data.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthApi {
    @Headers("Content-Type: application/json")
    @POST("/api/register")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

    @Headers("Content-Type: application/json")
    @POST("/api/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
