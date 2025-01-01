package com.example.cinemaapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cinemaapp.R;
import com.example.cinemaapp.data.api.BaseUrl;
import com.example.cinemaapp.data.api.ReservationResponse;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {

    private final List<ReservationResponse> reservations;

    // Constructor
    public ReservationAdapter(List<ReservationResponse> reservations) {
        this.reservations = reservations;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reservation, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        ReservationResponse reservation = reservations.get(position);

        // Bind data to views
        holder.textMovieName.setText(reservation.getMovieReserve().getName());
        holder.textReservationDate.setText("Réservé le : " + reservation.getCreatedAt());
        holder.textTotalPrice.setText("Total : " + reservation.getMontant() + " €");

        // Display snacks
        StringBuilder snacks = new StringBuilder();
        if (reservation.getSnackReservations() != null && !reservation.getSnackReservations().isEmpty()) {
            for (ReservationResponse.SnackReservation snack : reservation.getSnackReservations()) {
                snacks.append(snack.getSnack().getName())
                        .append(" x")
                        .append(snack.getQuantity())
                        .append(", ");
            }
            // Remove last comma and space
            if (snacks.length() > 0) snacks.setLength(snacks.length() - 2);
        }
        holder.textSnacksReserved.setText("Snacks : " + snacks.toString());

        String baseUrl = BaseUrl.BASE_URL+reservation.getMovieReserve().getImage();
        // Charger l'image avec Glide
        Glide.with(holder.imageMoviePoster.getContext())
                .load(baseUrl) // Charger l'image depuis l'URL ou le chemin
                .into(holder.imageMoviePoster);
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public void updateReservations(List<ReservationResponse> reservations) {
        this.reservations.clear();
        this.reservations.addAll(reservations);
        notifyDataSetChanged();
    }

    // ViewHolder class
    public static class ReservationViewHolder extends RecyclerView.ViewHolder {
        ImageView imageMoviePoster;
        TextView textMovieName, textReservationDate, textTotalPrice, textSnacksReserved;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            imageMoviePoster = itemView.findViewById(R.id.imageMoviePoster);
            textMovieName = itemView.findViewById(R.id.textMovieName);
            textReservationDate = itemView.findViewById(R.id.textReservationDate);
            textTotalPrice = itemView.findViewById(R.id.textTotalPrice);
            textSnacksReserved = itemView.findViewById(R.id.textSnacksReserved);
        }
    }
}
