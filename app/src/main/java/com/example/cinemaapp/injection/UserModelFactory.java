package com.example.cinemaapp.injection;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.cinemaapp.data.api.TokenManager;
import com.example.cinemaapp.data.repository.UserRepository;
import com.example.cinemaapp.viewmodel.UserViewModel;

import org.jetbrains.annotations.NotNull;

public class UserModelFactory implements ViewModelProvider.Factory {
    private final UserRepository userRepository;
    private static volatile UserModelFactory factory;
    private final TokenManager tokenManager;

    // Singleton to ensure a single instance of the factory
    public static UserModelFactory getInstance(UserRepository userRepository, TokenManager tokenManager) {
        if (factory == null) {
            synchronized (UserModelFactory.class) {
                if (factory == null) {
                    factory = new UserModelFactory(userRepository,tokenManager);
                }
            }
        }
        return factory;
    }

    // Private constructor to enforce singleton pattern
    private UserModelFactory(UserRepository userRepository , TokenManager tokenManager) {
        this.userRepository = userRepository;
        this.tokenManager = tokenManager; // Initialisation du TokenManager
    }

    @Override
    @NotNull
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)) {

            return (T) new UserViewModel(userRepository,tokenManager);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
