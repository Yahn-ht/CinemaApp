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

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movieList;
    private final OnMovieClickListener listener;

    // Interface pour gérer les clics
    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    // Constructeur avec la liste de films et le listener
    public MovieAdapter(OnMovieClickListener listener) {
        this.movieList = new ArrayList<>();
        this.listener = listener;
    }

    // Mettre à jour la liste des films
    public void setMovies(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.bind(movie, listener);
        String baseUrl = "https://2aca-105-73-97-232.ngrok-free.app/"+movie.getImage();
        // Charger l'image avec Glide
        Glide.with(holder.image.getContext())
                .load(baseUrl) // Charger l'image depuis l'URL ou le chemin
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return (movieList != null) ? movieList.size() : 0;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final ImageView image;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movieTitle);
            image = itemView.findViewById(R.id.movieImage);
        }

        public void bind(final Movie movie, final OnMovieClickListener listener) {
            title.setText(movie.getName());
            itemView.setOnClickListener(v -> listener.onMovieClick(movie));
        }
    }
}
