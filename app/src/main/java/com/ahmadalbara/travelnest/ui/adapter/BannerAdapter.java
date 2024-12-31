package com.ahmadalbara.travelnest.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.ahmadalbara.travelnest.R;
import com.ahmadalbara.travelnest.data.model.Banner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {
    private ArrayList<Banner> banners;
    private ViewPager2 viewPager2;
    private Context context;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            banners.addAll(banners);
            notifyDataSetChanged();
        }
    };

    public BannerAdapter(ArrayList<Banner> banners, ViewPager2 viewPager2) {
        this.banners = banners;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public BannerAdapter.BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        return new BannerViewHolder(LayoutInflater.from(context).inflate(R.layout.item_banner,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BannerAdapter.BannerViewHolder holder, int position) {
        holder.setImage(banners.get(position));

        if (position == banners.size() - 2) {
            viewPager2.post(new Runnable() {
                @Override
                public void run() {
                    banners.addAll(banners);
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return banners.size();
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageBanner);
        }
            void setImage (Banner banners){
                imageView.setImageResource(banners.getImageResId());
            }

    }
}
