package com.example.cinemaapp.data.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ReservationApi {
    @POST("api/reservation/create")
    Call<ReservationResponse> createReservation(@Body ReservationRequest reservationRequest);

    @GET("api/reservations/users")
    Call<List<ReservationResponse>> getUserReservations();
}
