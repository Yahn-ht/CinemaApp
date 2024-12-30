package com.example.cinemaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.cinemaapp.R;
import com.example.cinemaapp.data.api.BaseUrl;
import com.example.cinemaapp.data.model.Movie;
import com.example.cinemaapp.ui.favorie.FavorieFragment;

import java.util.List;

public class FavoriteMoviesAdapter extends android.widget.ArrayAdapter<Movie> {

    private final List<Movie> movies;
    private final Context context;
    private final OnMovieClickListener listener;

    public FavoriteMoviesAdapter(@NonNull Context context, @NonNull List<Movie> movies, OnMovieClickListener listener) {
        super(context, R.layout.favorie_item, movies);
        this.movies = movies;
        this.context = context;
        this.listener = listener;
    }

    public interface OnMovieClickListener {
        void onMovieItemClick(Movie selectedMovie);  // Méthode pour gérer le clic sur un film
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.favorie_item, parent, false);
        }

        // Obtenir l'élément actuel
        Movie movie = movies.get(position);

        // Initialiser les vues
        ImageView itemImage = convertView.findViewById(R.id.itemImage);
        TextView title = convertView.findViewById(R.id.title);
        TextView movieDescription = convertView.findViewById(R.id.movieDescription);

        // Remplir les données
        title.setText(movie.getName());
        movieDescription.setText(movie.getDescription());

        // Gérer le clic sur l'élément pour ouvrir les détails du film
        convertView.setOnClickListener(v -> listener.onMovieItemClick(movie));  // Appel à l'interface


        // Image placeholder (personnalisez selon votre implémentation)

        String baseUrl = BaseUrl.BASE_URL +movie.getImage();
        //itemImage.setImageResource(R.drawable.img);
        // Charger l'image avec Glide
        Glide.with(context)
                .load(baseUrl) // Charger l'image depuis l'URL ou le chemin
                .into(itemImage);



        return convertView;
    }

}
