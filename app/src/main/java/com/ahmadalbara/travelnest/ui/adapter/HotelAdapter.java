package com.ahmadalbara.travelnest.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadalbara.travelnest.BuildConfig;
import com.ahmadalbara.travelnest.R;
import com.ahmadalbara.travelnest.data.model.Hotel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {
    private List<Hotel> hotelList;
    public HotelAdapter(List<Hotel> hotelList) {
        this.hotelList = hotelList;
    }
    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotel, parent, false);
        return new HotelViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.textViewName.setText(hotel.getName());
        holder.textViewAddress.setText(hotel.getAddress() + ", " + hotel.getCity() + ", " + hotel.getCountry());

        if (!hotel.getPhotos().isEmpty() && hotel.getPhotos().get(0).getPhotoPath() != null) {
            String photoUrl = BuildConfig.BASE_URL + "storage/" + hotel.getPhotos().get(0).getPhotoPath();
            Picasso.get()
                    .load(photoUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.imageViewPhoto);
        } else {
            holder.imageViewPhoto.setImageResource(R.drawable.placeholder);
        }
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }
    public static class HotelViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewAddress;
        ImageView imageViewPhoto;
        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewHotelName);
            textViewAddress = itemView.findViewById(R.id.textViewHotelAddress);
            imageViewPhoto = itemView.findViewById(R.id.imageViewHotelPhoto);
        }
    }
}
