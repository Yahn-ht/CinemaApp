package com.example.cinemaapp.data.api;

import com.example.cinemaapp.data.model.Snack;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SnackApi {
    @GET("api/snacks")
    Call<List<Snack>> getSnacks();
}


