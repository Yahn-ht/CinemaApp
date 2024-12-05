package com.example.cinemaapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cinemaapp.adapter.MovieAdapter2;
import com.example.cinemaapp.data.model.Movie;

import java.util.ArrayList;
import java.util.List;

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

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        MovieAdapter2 adapter = new MovieAdapter2(getMovieList());
        recyclerView.setAdapter(adapter);
    }

    private List<Movie> getMovieList() {
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie(
                "Inception",
                "A thief who steals corporate secrets through the use of dream-sharing technology.",
                R.drawable.img));
        movieList.add(new Movie(
                "The Dark Knight",
                "When the menace known as the Joker emerges from his mysterious past, he wreaks havoc.",
                R.drawable.img));
        movieList.add(new Movie(
                "Interstellar",
                "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
                R.drawable.img));
        movieList.add(new Movie(
                "The Matrix",
                "A computer hacker learns about the true nature of his reality and his role in the war.",
                R.drawable.img));
        movieList.add(new Movie(
                "Avatar",
                "A paraplegic Marine is dispatched to the moon Pandora to establish connections with the Na'vi.",
                R.drawable.img));
        return movieList;
    }
}