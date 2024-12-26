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
import com.example.cinemaapp.data.api.ReservationResponse;

import java.util.List;

public class SnackAdapter2 extends RecyclerView.Adapter<SnackAdapter2.SnackViewHolder> {
    private final List<ReservationResponse.SnackReservation> snackReservations;

    public SnackAdapter2(List<ReservationResponse.SnackReservation> snackReservations) {
        this.snackReservations = snackReservations;
    }

    @NonNull
    @Override
    public SnackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_snack, parent, false);
        return new SnackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SnackViewHolder holder, int position) {
        ReservationResponse.SnackReservation snackReservation = snackReservations.get(position);
        holder.tvSnackName.setText(snackReservation.getSnack().getName());
        holder.tvSnackQuantity.setText("x" + snackReservation.getQuantity());
        holder.tvSnackPrice.setText(snackReservation.getSnack().getPrix() + "Dh");

        // Chargement de l'image du snack
        if (snackReservation.getSnack().getCategory().getName().equals("Boisson") ) {
            holder.ivSnackImage.setImageResource(R.drawable.coca_img1);
        } else if (snackReservation.getSnack().getCategory().getName().equals("Chips")) {
            holder.ivSnackImage.setImageResource(R.drawable.chips_img);
        }else{
            holder.ivSnackImage.setImageResource(R.drawable.popcorn_img);
        }
    }

    @Override
    public int getItemCount() {
        return snackReservations.size();
    }

    public static class SnackViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSnackImage;
        TextView tvSnackName, tvSnackQuantity, tvSnackPrice;

        public SnackViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSnackImage = itemView.findViewById(R.id.ivSnackImage);
            tvSnackName = itemView.findViewById(R.id.tvSnackName);
            tvSnackQuantity = itemView.findViewById(R.id.tvSnackQuantity);
            tvSnackPrice = itemView.findViewById(R.id.tvSnackPrice);
        }
    }
}

