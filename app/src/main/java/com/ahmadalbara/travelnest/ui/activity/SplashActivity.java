package com.ahmadalbara.travelnest.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.ahmadalbara.travelnest.R;
import com.ahmadalbara.travelnest.data.model.User;
import com.ahmadalbara.travelnest.data.remote.ApiClient;
import com.ahmadalbara.travelnest.data.remote.ApiService;
import com.ahmadalbara.travelnest.utils.PrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private ApiService apiService;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        apiService = ApiClient.getInstance(this).create(ApiService.class);
        prefManager = new PrefManager(this);

        new Handler().postDelayed(() -> {
            String token = prefManager.getToken();
            if (token != null) {
                validateToken(token);
            } else {
                navigateToLogin();
            }
        }, 2000);
    }
    private void validateToken(String token) {
        Call<User> call = apiService.getUser("Bearer " + token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    navigateToMain();
                } else {
                    Log.d("SplashActivity", "Token expired or invalid: " + response.message());
                    prefManager.clear();
                    navigateToLogin();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("SplashActivity", "Error: " + t.getMessage());
                prefManager.clear();
                navigateToLogin();
            }
        });
    }

    private void navigateToMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
