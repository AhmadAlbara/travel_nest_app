package com.ahmadalbara.travelnest.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.ahmadalbara.travelnest.R;
import com.ahmadalbara.travelnest.data.model.Banner;
import com.ahmadalbara.travelnest.data.model.Hotel;
import com.ahmadalbara.travelnest.data.model.User;
import com.ahmadalbara.travelnest.data.remote.ApiClient;
import com.ahmadalbara.travelnest.data.remote.ApiService;
import com.ahmadalbara.travelnest.ui.adapter.BannerAdapter;
import com.ahmadalbara.travelnest.ui.adapter.HotelAdapter;
import com.ahmadalbara.travelnest.utils.PrefManager;
import com.google.gson.Gson;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ApiService apiService;
    private PrefManager prefManager;
    private TextView userTextView;

    private RecyclerView recyclerView;
    private HotelAdapter hotelAdapter;
    private List<Hotel> hotelList;
    private MapView mapView;

    private ViewPager2 viewPagerSlider;

    private BannerAdapter bannerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_main);
        apiService = ApiClient.getInstance(this).create(ApiService.class);
        prefManager = new PrefManager(this);

        String token = prefManager.getToken();
        if (token != null) {
            getUserProfile(token);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        recyclerView = findViewById(R.id.recyclerViewHotels);
        hotelList = new ArrayList<>();
        hotelAdapter = new HotelAdapter(hotelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(hotelAdapter);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        fetchHotels();

        viewPagerSlider = findViewById(R.id.viewPageSlider);
        userTextView = findViewById(R.id.usertextView);

        setupViewPager();
    }

    private void fetchHotels() {
        Call<List<Hotel>> call = apiService.getHotels();
        call.enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    hotelList.clear();
                    hotelList.addAll(response.body());
                    hotelAdapter.notifyDataSetChanged();
                    renderMapbox();
                } else {
                    Log.d("MainActivity", "Failed to fetch hotels: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Hotel>> call, Throwable t) {
                Log.d("MainActivity", "Error: " + t.getMessage());
            }
        });
    }

    private void getUserProfile(String token) {
        Call<User> call = apiService.getUser("Bearer " + token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    userTextView.setText(user.getName());
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("MainActivity", "Error: " + t.getMessage());
            }
        });
    }

    private void renderMapbox() {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        for (Hotel hotel : hotelList) {
                            mapboxMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(hotel.getLatitude(), hotel.getLongitude()))
                                    .title(hotel.getName())
                                    .snippet(hotel.getAddress())
                            );
                        }
                    }
                });
            }
        });
    }
    private void setupViewPager() {
        ArrayList<Banner> banners = new ArrayList<>();
        banners.add(new Banner(R.drawable.banner1));
        banners.add(new Banner(R.drawable.banner2));
        bannerAdapter = new BannerAdapter(banners, viewPagerSlider);

        viewPagerSlider.setClipToPadding(false);
        viewPagerSlider.setClipChildren(false);
        viewPagerSlider.setOffscreenPageLimit(3);
        viewPagerSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new  CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        viewPagerSlider.setPageTransformer(compositePageTransformer);

        viewPagerSlider.setAdapter(bannerAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
