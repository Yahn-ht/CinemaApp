package com.example.cinemaapp.ui.home;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cinemaapp.R;
import com.example.cinemaapp.adapter.MovieAdapter;
import com.example.cinemaapp.data.api.BaseUrl;
import com.example.cinemaapp.data.api.TokenManager;
import com.example.cinemaapp.data.model.Movie;
import com.example.cinemaapp.data.repository.MovieRepository;
import com.example.cinemaapp.data.repository.UserRepository;
import com.example.cinemaapp.injection.MovieModelFactory;
import com.example.cinemaapp.injection.UserModelFactory;
import com.example.cinemaapp.viewmodel.MovieViewModel;
import com.example.cinemaapp.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MovieViewModel movieViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView voirTout = view.findViewById(R.id.voir_tout);
        TextView titre = view.findViewById(R.id.movieTitle);
        Button button = view.findViewById(R.id.button);
        ImageView image = view.findViewById(R.id.image);

        voirTout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.home_to_movies_list_fragment);
            }
        });

        TokenManager tokenManager = TokenManager.getInstance(requireContext());
        MovieRepository movieRepository = new MovieRepository(tokenManager);
        //userViewModel = new ViewModelProvider(this, UserModelFactory.getInstance(new UserRepository(), tokenManager)).get(UserViewModel.class);
        MovieModelFactory factory = new MovieModelFactory(movieRepository);
        movieViewModel = new ViewModelProvider(this, factory).get(MovieViewModel.class);

        movieViewModel.loadMovies();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        MovieAdapter adapter = new MovieAdapter(movie -> {
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

        movieViewModel.getFirstMovie().observe(getViewLifecycleOwner(), movie -> {
            titre.setText(movie.getName());
            String baseUrl = BaseUrl.BASE_URL +movie.getImage();
            Glide.with(getContext())
                    .load(baseUrl)
                    //.placeholder(R.drawable.placeholder_image) // Image temporaire en attendant le chargement
                    //.error(R.drawable.error_image)
                    .into(image);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavController navController = Navigation.findNavController(view);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("movie_key", movie);
                    navController.navigate(R.id.movieFragment,bundle);
                }
            });
        });


        movieViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            // Afficher un message d'erreur
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
        });
        //return view;
    }
}