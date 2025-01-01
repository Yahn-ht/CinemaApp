package com.example.cinemaapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaapp.R;
import com.example.cinemaapp.data.api.ReservationResponse;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {
    private final List<ReservationResponse.Place> places;

    public PlaceAdapter(List<ReservationResponse.Place> places) {
        this.places = places;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        ReservationResponse.Place place = places.get(position);
        holder.tvPlaceNumber.setText("Place " + place.getNumero());
        holder.tvPlaceCategorie.setText(place.getCategory().getName());

    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlaceNumber;
        ImageView ivPlaceStatus;
        TextView tvPlaceCategorie;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlaceNumber = itemView.findViewById(R.id.tvPlaceNumber);
            ivPlaceStatus = itemView.findViewById(R.id.ivPlaceStatus);
            tvPlaceCategorie = itemView.findViewById(R.id.tvPlaceCategorie);
        }
    }
}


