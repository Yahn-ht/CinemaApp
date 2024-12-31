package com.example.cinemaapp.ui.movie;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.cinemaapp.R;
import com.example.cinemaapp.data.api.BaseUrl;
import com.example.cinemaapp.data.api.ReservationRequest;
import com.example.cinemaapp.data.api.TokenManager;
import com.example.cinemaapp.data.model.Movie;
import com.example.cinemaapp.data.model.Session;
import com.example.cinemaapp.data.repository.MovieRepository;
import com.example.cinemaapp.injection.MovieModelFactory;
import com.example.cinemaapp.ui.salle.SalleActivity;
import com.example.cinemaapp.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;
import java.time.ZonedDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MovieFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ImageView favoriteIcon;
    //private boolean isFavorite = false; // Initialement non favori
    private MovieViewModel movieViewModel;

    public MovieFragment() {
        // Required empty public constructor
    }

    public static MovieFragment newInstance(String param1, String param2) {
        MovieFragment fragment = new MovieFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageView = view.findViewById(R.id.imagePoster);
        TextView textView = view.findViewById(R.id.textTitle);
        TextView duree = view.findViewById(R.id.duree);
        TextView categorie = view.findViewById(R.id.categorie);
        TextView auteur = view.findViewById(R.id.auteur);
        TextView description = view.findViewById(R.id.description);
        Button button = view.findViewById(R.id.connect_button);

        favoriteIcon = view.findViewById(R.id.favorite);

        Spinner spinner = view.findViewById(R.id.spinner);
        List<SpinnerItem> spinnerItems = new ArrayList<>();

        TokenManager tokenManager = TokenManager.getInstance(requireContext());
        MovieRepository movieRepository = new MovieRepository(tokenManager);
        MovieModelFactory factory = new MovieModelFactory(movieRepository);
        movieViewModel = new ViewModelProvider(this, factory).get(MovieViewModel.class);

        Movie movie = (Movie) getArguments().getSerializable("movie_key");
        if (movie != null) {
            for (Session session : movie.getSession()) {
                int sessionId = session.getId();
                String date = session.getDate().substring(0, 10);
                int salleNumero = session.getSalle().getNumero();

                String displayText = movie.getName() + " - Date: " + date + ", Salle: " + salleNumero;
                spinnerItems.add(new SpinnerItem(movie.getId(), sessionId, displayText,session.getSalle().getId()));
            }

            ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<SpinnerItem>(getContext(), android.R.layout.simple_spinner_item, spinnerItems) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView textView = (TextView) super.getView(position, convertView, parent);
                    styliserTextView(textView);
                    return textView;
                }

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                    styliserDropDownTextView(textView);
                    return textView;
                }
            };

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            String baseUrl = BaseUrl.BASE_URL + movie.getImage();
            Glide.with(getContext())
                    .load(baseUrl)
                    .into(imageView);

            textView.setText(movie.getName());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                duree.setText(ZonedDateTime.parse(movie.getDuree(),DateTimeFormatter.ISO_DATE_TIME).toLocalTime().toString());
            }
            categorie.setText(movie.getCategorieMovie().getName());
            auteur.setText(movie.getAuthorName());
            description.setText(movie.getDescription());
            if (movie.isFavorite()) {
                favoriteIcon.setImageResource(R.drawable.heartfav);
            } else {
                favoriteIcon.setImageResource(R.drawable.notfav);
            }
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItem selectedItem = (SpinnerItem) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), "Film ID: " + selectedItem.getMovieId() + ", Session ID: " + selectedItem.getSessionId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        button.setOnClickListener(v -> {
            if(spinner.getSelectedItem() != null) {
                SpinnerItem selectedItem = (SpinnerItem) spinner.getSelectedItem();
                int movieId = selectedItem.getMovieId();
                int salleId = selectedItem.getSalleId();
                int sessionId = selectedItem.getSessionId();
                ReservationRequest reservationRequest = new ReservationRequest();
                reservationRequest.setMovie(movieId);
                reservationRequest.setSession(sessionId);
                //Bundle bundle = new Bundle();
                //bundle.putSerializable("reservationRequest", reservationRequest);
                //bundle.putInt("roomId", salleId);
                Intent intent = new Intent(getContext(), SalleActivity.class);
                intent.putExtra("reservationRequest", reservationRequest);
                intent.putExtra("roomId", salleId);
                startActivity(intent);
                //Navigation.findNavController(view).navigate(R.id.salleFragment, bundle);
            } else {
                Toast.makeText(getContext(), "Veuillez sélectionner une session", Toast.LENGTH_SHORT).show();
            }
        });

        favoriteIcon.setOnClickListener(v -> {
            if (movie.isFavorite()) {
                movieViewModel.removeFavoriteMovie(movie.getId());
                Toast.makeText(getContext(), "Supprimé des favoris", Toast.LENGTH_SHORT).show();
            } else {
                movieViewModel.addFavoriteMovie(movie.getId());
                Toast.makeText(getContext(), "Ajouté aux favoris", Toast.LENGTH_SHORT).show();
            }
            movie.setFavorite(!movie.isFavorite());
            updateFavoriteIcon(movie);
        });
    }

    private void styliserTextView(TextView textView) {
        textView.setTextSize(16);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextColor(Color.parseColor("#3F51B5"));
        textView.setPadding(16, 16, 16, 16);
    }

    private void styliserDropDownTextView(TextView textView) {
        textView.setTextSize(14);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextColor(Color.parseColor("#000000"));
        textView.setPadding(20, 20, 20, 20);
        textView.setBackgroundColor(Color.parseColor("#F5F5F5"));
    }

    private void updateFavoriteIcon(Movie movie) {
        if (movie.isFavorite()) {
            favoriteIcon.setImageResource(R.drawable.heartfav);
        } else {
            favoriteIcon.setImageResource(R.drawable.notfav);
        }
    }
}
