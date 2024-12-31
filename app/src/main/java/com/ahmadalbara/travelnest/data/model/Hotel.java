package com.ahmadalbara.travelnest.data.model;

import java.util.List;

public class Hotel {
    private int id;
    private String name;
    private String address;
    private String city;
    private String country;
    private double latitude; // Menggunakan double untuk presisi tinggi
    private double longitude; // Menggunakan double untuk presisi tinggi
    private List<Photo> photos;

    // Constructor
    public Hotel(int id, String name, String address, String city, String country, double latitude, double longitude, List<Photo> photos) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photos = photos;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public double getLatitude() { // Getter untuk double
        return latitude;
    }

    public double getLongitude() { // Getter untuk double
        return longitude;
    }

    public List<Photo> getPhotos() {
        return photos;
    }
}
