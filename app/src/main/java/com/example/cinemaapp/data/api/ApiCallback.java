package com.example.cinemaapp.data.api;

public interface ApiCallback<T> {
    void onSuccess(T response);
    void onError(String error);

}
