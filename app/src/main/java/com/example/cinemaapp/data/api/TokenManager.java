package com.example.cinemaapp.data.api;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static final String PREF_NAME = "UserTokenPref";
    private static final String TOKEN_KEY = "authToken";
    private final SharedPreferences sharedPreferences;

    // Variable statique pour stocker l'instance unique
    private static TokenManager instance;

    // Constructeur privé pour empêcher l'instanciation externe
    private TokenManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Méthode publique pour récupérer l'instance du singleton
    public static TokenManager getInstance(Context context) {
        if (instance == null) {
            // Si l'instance est nulle, la créer avec le contexte
            instance = new TokenManager(context.getApplicationContext());
        }
        return instance;
    }

    public void saveToken(String token) {
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply();
    }

    public String getToken() {
        return sharedPreferences.getString(TOKEN_KEY, null);
    }

    public void clearToken() {
        sharedPreferences.edit().remove(TOKEN_KEY).apply();
    }
}
