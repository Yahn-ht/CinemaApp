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
import com.example.cinemaapp.data.model.Movie;

import java.util.List;

public class MovieAdapter2 extends RecyclerView.Adapter<MovieAdapter2.MovieViewHolder> {

    private List<Movie> movieList;

    public MovieAdapter2(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie2, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.title.setText(movie.getName());
        holder.description.setText(movie.getDescription());
        // Charger l'image avec Glide ou Picasso
        Glide.with(holder.image.getContext()).load(movie.getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView image;

        public MovieViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movieTitle);
            description = itemView.findViewById(R.id.movieDescription);
            image = itemView.findViewById(R.id.movieImage);
        }
    }
}

