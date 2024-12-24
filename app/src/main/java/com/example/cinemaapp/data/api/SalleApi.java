package com.example.cinemaapp.data.api;

import com.example.cinemaapp.data.api.salles.SalleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SalleApi {
    @GET("api/salles/{id}")
    Call<SalleResponse> getRoomById(@Path("id") int id);
}
