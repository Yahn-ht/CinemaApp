package com.example.cinemaapp.data.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class RetrofitClient {
    public static final String BASE_URL = BaseUrl.BASE_URL;
    private static Retrofit retrofitWithoutToken;
    private static Retrofit retrofitWithToken;

    // Instance sans token
    public static Retrofit getInstanceWithoutToken() {
        if (retrofitWithoutToken == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS) // Timeout pour établir la connexion
                    .readTimeout(60, TimeUnit.SECONDS)   // Timeout pour lire la réponse
                    .writeTimeout(60, TimeUnit.SECONDS)  // Timeout pour écrire la requête
                    .build();

            retrofitWithoutToken = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client) // Ajout du client avec les timeouts
                    .build();
        }
        return retrofitWithoutToken;
    }

    // Instance avec token
    public static Retrofit getInstanceWithToken(TokenManager tokenManager) {
        if (tokenManager == null) {
            throw new IllegalArgumentException("TokenManager cannot be null");
        }

        if (retrofitWithToken == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS) // Timeout pour établir la connexion
                    .readTimeout(60, TimeUnit.SECONDS)   // Timeout pour lire la réponse
                    .writeTimeout(60, TimeUnit.SECONDS)  // Timeout pour écrire la requête
                    .addInterceptor(chain -> {
                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder();

                        // Récupération du token
                        String token = tokenManager.getToken();
                        if (token != null) {
                            requestBuilder.addHeader("Authorization", "Bearer " + token);
                        } else {
                            // Ajout d'un log pour le débogage
                            System.out.println("Token is null. Authorization header not added.");
                        }

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    })
                    .build();

            retrofitWithToken = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofitWithToken;
    }
}
