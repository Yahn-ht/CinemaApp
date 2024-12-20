package com.example.cinemaapp.data.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String BASE_URL = BaseUrl.BASE_URL;
    private static Retrofit retrofitWithoutToken;
    private static Retrofit retrofitWithToken;

    // Instance sans token
    public static Retrofit getInstanceWithoutToken() {
        if (retrofitWithoutToken == null) {
            retrofitWithoutToken = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitWithoutToken;
    }

    // Instance avec token
    public static Retrofit getInstanceWithToken(TokenManager tokenManager) {
        if (retrofitWithToken == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder();

                        String token = tokenManager.getToken();
                        if (token != null) {
                            requestBuilder.addHeader("Authorization", "Bearer " + token);
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
