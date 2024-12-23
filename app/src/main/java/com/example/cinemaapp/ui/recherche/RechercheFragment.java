package com.example.cinemaapp.ui.recherche;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.cinemaapp.R;
import com.example.cinemaapp.adapter.RechercheAdapter;
import com.example.cinemaapp.data.api.TokenManager;
import com.example.cinemaapp.data.model.Movie;
import com.example.cinemaapp.data.repository.MovieRepository;
import com.example.cinemaapp.injection.MovieModelFactory;
import com.example.cinemaapp.viewmodel.MovieViewModel;

import java.util.List;

public class RechercheFragment extends Fragment {

    private EditText searchEditText;
    private ImageButton searchButton;
    private ListView listView;
    private RechercheAdapter adapter;
    private MovieViewModel rechercheViewModel;

    public RechercheFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recherche, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchEditText = view.findViewById(R.id.searchEditText);
        searchButton = view.findViewById(R.id.searchButton);
        listView = view.findViewById(R.id.listView);

        // Initialisation de TokenManager et MovieRepository
        TokenManager tokenManager = TokenManager.getInstance(requireContext());
        if (tokenManager == null) {
            Toast.makeText(requireContext(), "Erreur d'authentification", Toast.LENGTH_SHORT).show();
            return;
        }
        MovieRepository movieRepository = new MovieRepository(tokenManager);

        // Initialiser ViewModel
      //  rechercheViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        MovieModelFactory factory = new MovieModelFactory(movieRepository);
        rechercheViewModel = new ViewModelProvider(this, factory).get(MovieViewModel.class);


        // Observer des résultats de recherche
        rechercheViewModel.getSearchResults().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                // Mettre à jour l'adapter avec les résultats
                if (movies != null && !movies.isEmpty()) {
                    adapter = new RechercheAdapter(requireContext(), movies, RechercheFragment.this::onMovieItemClick);
                    listView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "No movies found", Toast.LENGTH_SHORT).show();
                }
            }


        });

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


        // Observer les erreurs de recherche
        rechercheViewModel.getSearchError().observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
        });
/*
        // Observer l'état de chargement
        rechercheViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            // Vous pouvez ajouter une ProgressBar ici si nécessaire
        });*/

        // Ajouter un écouteur pour le champ de recherche
        searchEditText.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String query = charSequence.toString();
                if (!query.isEmpty()) {
                    rechercheViewModel.searchMoviesWithDelay(query, 1, 5); // Appel API pour rechercher des films
                }
            }


            @Override
            public void afterTextChanged(android.text.Editable editable) {}
        });

        // Gérer l'événement du bouton de recherche
        searchButton.setOnClickListener(v -> {
            String query = searchEditText.getText().toString();
            if (!query.isEmpty()) {
                rechercheViewModel.searchMoviesWithDelay(query,1,5);
            }

        });
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

