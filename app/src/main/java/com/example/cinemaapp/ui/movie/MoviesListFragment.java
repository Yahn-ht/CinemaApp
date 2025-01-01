package com.example.cinemaapp.ui.movie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cinemaapp.R;
import com.example.cinemaapp.adapter.MovieAdapter;
import com.example.cinemaapp.adapter.MovieAdapter2;
import com.example.cinemaapp.data.api.TokenManager;
import com.example.cinemaapp.data.repository.MovieRepository;
import com.example.cinemaapp.injection.MovieModelFactory;
import com.example.cinemaapp.viewmodel.MovieViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoviesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MovieViewModel movieViewModel;
    private ProgressBar progressBar;

    public MoviesListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoviesListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoviesListFragment newInstance(String param1, String param2) {
        MoviesListFragment fragment = new MoviesListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TokenManager tokenManager = TokenManager.getInstance(requireContext());
        MovieRepository movieRepository = new MovieRepository(tokenManager);
        progressBar = view.findViewById(R.id.progressBar);
        //userViewModel = new ViewModelProvider(this, UserModelFactory.getInstance(new UserRepository(), tokenManager)).get(UserViewModel.class);
        MovieModelFactory factory = new MovieModelFactory(movieRepository);
        movieViewModel = new ViewModelProvider(this, factory).get(MovieViewModel.class);

        movieViewModel.loadMovies();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        MovieAdapter2 adapter = new MovieAdapter2(movie -> {
            //Toast.makeText(requireContext(), "Movie clicked:", Toast.LENGTH_SHORT).show();
            NavController navController = Navigation.findNavController(view);
            Bundle bundle = new Bundle();
            bundle.putSerializable("movie_key", movie);
            navController.navigate(R.id.movieFragment,bundle);
        });
        recyclerView.setAdapter(adapter);

        movieViewModel.getMovies().observe(getViewLifecycleOwner(), movies -> {
            adapter.setMovies(movies);
        });

        movieViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });

        movieViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            // Afficher un message d'erreur
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
        });
    }

}