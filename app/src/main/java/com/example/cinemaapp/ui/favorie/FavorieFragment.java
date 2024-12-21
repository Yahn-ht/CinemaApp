package com.example.cinemaapp.ui.favorie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.cinemaapp.R;
import com.example.cinemaapp.adapter.FavoriteMoviesAdapter;
import com.example.cinemaapp.data.api.TokenManager;
import com.example.cinemaapp.data.model.Movie;
import com.example.cinemaapp.data.repository.MovieRepository;
import com.example.cinemaapp.injection.MovieModelFactory;
import com.example.cinemaapp.viewmodel.MovieViewModel;

import java.util.List;

public class FavorieFragment extends Fragment {

    private MovieViewModel movieViewModel;
    private ListView listView;
    private TextView emptyView; // Vue pour afficher un message si la liste est vide
    private FavoriteMoviesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.myListView);
        emptyView = view.findViewById(R.id.isEmpty); // Assurez-vous que cette vue est présente dans le layout XML

        // Initialisation de TokenManager et MovieRepository
        TokenManager tokenManager = TokenManager.getInstance(requireContext());
        if (tokenManager == null) {
            Toast.makeText(requireContext(), "Erreur d'authentification", Toast.LENGTH_SHORT).show();
            return;
        }
        MovieRepository movieRepository = new MovieRepository(tokenManager);

        // Configuration de ViewModel
        MovieModelFactory factory = new MovieModelFactory(movieRepository);
        movieViewModel = new ViewModelProvider(this, factory).get(MovieViewModel.class);

        // Observateurs pour les favoris
        movieViewModel.getFavoriteMovies().observe(getViewLifecycleOwner(), this::updateFavorites);

        // Observateurs pour les messages
        movieViewModel.getSuccessMessage().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        });

        movieViewModel.getError().observe(getViewLifecycleOwner(), errorMessage -> {
            Toast.makeText(requireContext(), "Erreur : " + errorMessage, Toast.LENGTH_SHORT).show();
        });

        // Charger les favoris
        movieViewModel.loadFavoriteMovies();

        // Gestion des clics
        listView.setOnItemClickListener((parent, clickedView, position, id) -> {
            Movie selectedMovie = adapter.getItem(position);
            if (selectedMovie != null) {
//                Toast.makeText(requireContext(), "Film sélectionné : " + selectedMovie.getName(), Toast.LENGTH_SHORT).show();
                NavController navController = Navigation.findNavController(view);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movie_key", selectedMovie);
                navController.navigate(R.id.movieFragment,bundle);
            }
        });

        listView.setOnItemLongClickListener((parent, clickedView, position, id) -> {
            Movie selectedMovie = adapter.getItem(position);
            if (selectedMovie != null) {
                movieViewModel.removeFavoriteMovie(selectedMovie.getId());
            }
            return true;
        });
    }

    private void updateFavorites(List<Movie> favoriteMovies) {
        if (favoriteMovies != null && !favoriteMovies.isEmpty()) {
            emptyView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            if (adapter == null) {
                adapter = new FavoriteMoviesAdapter(requireContext(),favoriteMovies,this::onMovieItemClick);
                listView.setAdapter(adapter);
            } else {
                adapter.clear();
                adapter.addAll(favoriteMovies);
                adapter.notifyDataSetChanged();
            }
        } else {
            emptyView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }

    public void onMovieItemClick(Movie selectedMovie) {
        // Gérer l'action lors du clic sur un élément de la liste
        Toast.makeText(requireContext(), "Film sélectionné : " + selectedMovie.getName(), Toast.LENGTH_SHORT).show();
        NavController navController = Navigation.findNavController(requireView());
        Bundle bundle = new Bundle();
        bundle.putSerializable("movie_key", selectedMovie);
        navController.navigate(R.id.movieFragment, bundle);
    }

}
