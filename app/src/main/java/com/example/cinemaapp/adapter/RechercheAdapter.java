package com.example.cinemaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cinemaapp.R;
import com.example.cinemaapp.data.api.BaseUrl;
import com.example.cinemaapp.data.model.Movie;

import java.util.List;

public class RechercheAdapter extends android.widget.ArrayAdapter<Movie> {

    private final Context context;
    private final List<Movie> movies;
    private final OnMovieClickListener listener;

    public RechercheAdapter(Context context, List<Movie> movies, OnMovieClickListener listener) {
        super(context, R.layout.recherche_item, movies);
        this.context = context;
        this.movies = movies;
        this.listener = listener;
    }

    public interface OnMovieClickListener {
        void onMovieItemClick(Movie selectedMovie);  // Méthode pour gérer le clic sur un film
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.recherche_item, parent, false);
        }

        Movie currentMovie = movies.get(position);

        ImageView movieImage = convertView.findViewById(R.id.itemImage);
        TextView title = convertView.findViewById(R.id.title);
        TextView description = convertView.findViewById(R.id.movieDescription);

        String baseUrl = BaseUrl.BASE_URL +currentMovie.getImage();
        //itemImage.setImageResource(R.drawable.img);
        // Charger l'image avec Glide
        Glide.with(context)
                .load(baseUrl) // Charger l'image depuis l'URL ou le chemin
                .into(movieImage);

        title.setText(currentMovie.getName());
        description.setText(currentMovie.getDescription());
        // Gérer le clic sur l'élément pour ouvrir les détails du film
        convertView.setOnClickListener(v -> listener.onMovieItemClick(currentMovie));

        return convertView;
    }
}
