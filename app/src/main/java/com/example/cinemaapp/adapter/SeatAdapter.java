package com.example.cinemaapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaapp.R;
import com.example.cinemaapp.data.api.ReservationRequest;
import com.example.cinemaapp.data.model.Place;

import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {
    private final Context context;
    private final List<Place> seats;
    private final OnSeatClickListener listener;
    private ReservationRequest reservationRequest;

    public interface OnSeatClickListener {
        void onSeatClick(Place seat);
    }

    public SeatAdapter(Context context, List<Place> seats, OnSeatClickListener listener, ReservationRequest reservationRequest) {
        this.context = context;
        this.seats = seats;
        this.listener = listener;
        this.reservationRequest = reservationRequest;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_seat, parent, false);
        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        Place seat = seats.get(position);
        // Gestion des images
        if (seat.getSessions().stream().anyMatch(session -> session.getId() == reservationRequest.getSession())) {
            holder.imageView.setImageResource(R.drawable.ic_seat_unavailable); // Image indisponible
        } else {
            holder.imageView.setImageResource(seat.isSelected()
                    ? R.drawable.ic_seat_selected // Image sélectionnée
                    : R.drawable.ic_seat_available // Image disponible
            );
        }

        holder.itemView.setOnClickListener(v -> {
            if (seat.getSessions().stream().noneMatch(session -> session.getId() == reservationRequest.getSession())) {
                seat.setSelected(!seat.isSelected());
                notifyItemChanged(position); // Met à jour l'affichage
                listener.onSeatClick(seat);
            }
        });
    }

    @Override
    public int getItemCount() {
        return seats.size();
    }

    public static class SeatViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewSeat);
        }
    }
}
