package com.ahmadalbara.travelnest.data.remote;

import com.ahmadalbara.travelnest.data.model.AuthResponse;
import com.ahmadalbara.travelnest.data.model.Hotel;
import com.ahmadalbara.travelnest.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @POST("register")
    Call<AuthResponse> register(@Body User user);

    @POST("login")
    Call<AuthResponse> login(@Body User user);
    @GET("user")
    Call<User> getUser(@Header("Authorization") String token);
    @POST("logout")
    Call<Void> logout(@Header("Authorization") String token);

    @GET("hotels")
    Call<List<Hotel>> getHotels();
}
