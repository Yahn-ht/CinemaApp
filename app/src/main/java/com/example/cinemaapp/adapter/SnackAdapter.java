package com.example.cinemaapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.cinemaapp.R;
import com.example.cinemaapp.data.model.Snack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SnackAdapter extends RecyclerView.Adapter<SnackAdapter.SnackViewHolder> {
    private List<Snack> snacks;
    private final Map<Integer, Integer> selectedSnacks = new HashMap<>();

    public SnackAdapter(List<Snack> snacks) {
        this.snacks = snacks;
    }

    // Méthode pour mettre à jour les données
    public void updateData(List<Snack> newSnackList) {
        this.snacks = newSnackList;
        notifyDataSetChanged();  // Notifie l'adaptateur pour rafraîchir les données affichées
    }

    public Map<Integer, Integer> getSelectedSnacks() {
        return selectedSnacks;
    }

    @NonNull
    @Override
    public SnackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new SnackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SnackViewHolder holder, int position) {
        Snack snack = snacks.get(position);
        holder.name.setText(snack.getName());
        holder.price.setText(snack.getPrix() + " dh");
        if(snack.getCategory().getName().equals("PopCorn")){
            holder.picture.setImageResource(R.drawable.popcorn_img2);
        }else if(snack.getCategory().getName().equals("Boisson")){
            holder.picture.setImageResource(R.drawable.coca_img);
        }else{
            holder.picture.setImageResource(R.drawable.chips_img2);
        }
        // Utiliser getOrDefault pour éviter les valeurs nulles
        int currentCount = selectedSnacks.getOrDefault(snack.getId(), 0);
        holder.counter.setText(String.valueOf(currentCount));

        // Gestion des boutons
        holder.increase.setOnClickListener(v -> {
            int newCount = selectedSnacks.getOrDefault(snack.getId(), 0) + 1;  // Utilisation de getOrDefault ici aussi
            selectedSnacks.put(snack.getId(), newCount);
            holder.counter.setText(String.valueOf(newCount));
        });

        holder.decrease.setOnClickListener(v -> {
            int newCount = selectedSnacks.getOrDefault(snack.getId(), 0) - 1;  // Utilisation de getOrDefault ici aussi
            if (newCount < 0) newCount = 0; // Empêcher des valeurs négatives
            selectedSnacks.put(snack.getId(), newCount);
            holder.counter.setText(String.valueOf(newCount));

            // Supprimer les snacks avec un compte de 0
            if (newCount == 0) {
                selectedSnacks.remove(snack.getId());
            }
        });
    }


    @Override
    public int getItemCount() {
        return snacks.size();
    }

    public static class SnackViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, counter;
        ImageView picture;
        Button increase, decrease;

        public SnackViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemTitle);
            price = itemView.findViewById(R.id.itemPrice);
            counter = itemView.findViewById(R.id.itemQuantity);
            picture = itemView.findViewById(R.id.itemImage);
            increase = itemView.findViewById(R.id.buttonPlus);
            decrease = itemView.findViewById(R.id.buttonMinus);
        }
    }
}
