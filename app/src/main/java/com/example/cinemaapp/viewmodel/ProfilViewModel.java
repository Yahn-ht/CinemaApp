package com.example.cinemaapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemaapp.data.api.TokenManager;
import com.example.cinemaapp.data.model.User;
import com.example.cinemaapp.data.repository.ProfilRepository;
import com.example.cinemaapp.data.repository.UserRepository;

public class ProfilViewModel extends ViewModel {

    private final ProfilRepository profilRepository;

    private final MutableLiveData<String> statusMessage = new MutableLiveData<>();

    MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public ProfilViewModel(ProfilRepository profilRepository) {
        this.profilRepository = profilRepository;
    }

    public LiveData<User> getUser() {
        return userLiveData;
    }


    public void userInfo() {
        profilRepository.getUser().observeForever(userLiveData::setValue);
    }

}
