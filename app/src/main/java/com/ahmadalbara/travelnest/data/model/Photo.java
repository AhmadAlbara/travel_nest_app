package com.ahmadalbara.travelnest.data.model;

public class Photo {
    private int id;
    private int hotel_id;
    private String photo_path;

    // Constructor
    public Photo(int id, int hotel_id, String photo_path) {
        this.id = id;
        this.hotel_id = hotel_id;
        this.photo_path = photo_path;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getHotelId() {
        return hotel_id;
    }

    public String getPhotoPath() {
        return photo_path;
    }
}
