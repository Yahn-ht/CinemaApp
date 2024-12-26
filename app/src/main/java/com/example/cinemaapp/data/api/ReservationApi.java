package com.example.cinemaapp.data.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReservationApi {
    @POST("api/reservation/create")
    Call<ReservationResponse> createReservation(@Body ReservationRequest reservationRequest);
}
