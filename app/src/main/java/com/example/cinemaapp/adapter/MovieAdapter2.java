package com.example.cinemaapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaapp.R;
import com.example.cinemaapp.data.model.Movie;

import java.util.List;

public class MovieAdapter2 extends RecyclerView.Adapter<MovieAdapter2.MovieViewHolder> {
    private List<Movie> movieList;

    public MovieAdapter2(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieAdapter2.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie2, parent, false);
        return new MovieAdapter2.MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter2.MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.title.setText(movie.getTitle());
        holder.image.setImageResource(movie.getImageResId());
        holder.description.setText(movie.getDescription());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView description;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.movieImage);
            title = itemView.findViewById(R.id.movieTitle);
            description = itemView.findViewById(R.id.movieDescription);
        }
    }
}
