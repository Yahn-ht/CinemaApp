package com.example.cinemaapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemaapp.data.api.ApiCallback;
import com.example.cinemaapp.data.api.LoginRequest;
import com.example.cinemaapp.data.api.LoginResponse;
import com.example.cinemaapp.data.api.RegisterRequest;
import com.example.cinemaapp.data.api.RegisterResponse;
import com.example.cinemaapp.data.repository.UserRepository;
import com.example.cinemaapp.ui.user.LoginFragment;

public class UserViewModel extends ViewModel {

    private final UserRepository userRepository;

    private final MutableLiveData<String> statusMessage = new MutableLiveData<>();
    public LiveData<String> getStatusMessage() {
        return statusMessage;
    }

    public UserViewModel(LoginFragment loginFragment) {
        userRepository = new UserRepository();
    }

    public void register(RegisterRequest request) {
        userRepository.register(request, new ApiCallback<RegisterResponse>() {
            @Override
            public void onSuccess(RegisterResponse response) {
                statusMessage.postValue("Registration successful: " + response.getMessage());
            }

            @Override
            public void onError(String error) {
                statusMessage.postValue("Error: " + error);
            }
        });
    }

    public void login(LoginRequest request) {
        userRepository.login(request, new ApiCallback<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse response) {
                statusMessage.postValue("Login successful, token: " + response.getToken());
            }

            @Override
            public void onError(String error) {
                statusMessage.postValue("Error: " + error);
            }
        });
    }
}

