package com.example.cinemaapp.ui.movie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.example.cinemaapp.data.model.Movie;
import com.example.cinemaapp.data.model.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieFragment.
     */
    // TODO: Rename and change types and number of parameters
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = view.findViewById(R.id.imagePoster);
        TextView  textView = view.findViewById(R.id.textTitle);
        TextView  duree = view.findViewById(R.id.duree);
        TextView  categorie = view.findViewById(R.id.categorie);
        TextView  auteur = view.findViewById(R.id.auteur);
        TextView  description = view.findViewById(R.id.description);
        Button button = view.findViewById(R.id.connect_button);

        Spinner spinner = view.findViewById(R.id.spinner);

        List<SpinnerItem> spinnerItems = new ArrayList<>();
        List<Session> sessions= new ArrayList<>();

        if (getArguments() != null) {
            Movie movie = (Movie) getArguments().getSerializable("movie_key");
            if (movie != null) {
                for(Session session : movie.getSession()){
                    int sessionId = session.getId();
                    String date = session.getDate().substring(0, 10);
                    int salleNumero = session.getSalle().getNumero();

                    // Créer un nouvel élément pour le Spinner
                    String displayText = movie.getName() + " - Date: " + date + ", Salle: " + salleNumero;
                    spinnerItems.add(new SpinnerItem(movie.getId(), sessionId, displayText));
                }

                ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinnerItems);
                spinner.setAdapter(adapter);

                String baseUrl = BaseUrl.BASE_URL +movie.getImage();
                Glide.with(getContext())
                        .load(baseUrl)
                        //.placeholder(R.drawable.placeholder_image) // Image temporaire en attendant le chargement
                        //.error(R.drawable.error_image)
                        .into(imageView);
                textView.setText(movie.getName());

                // Autres éléments : description, image avec Glide/Picasso, etc.
                duree.setText(movie.getDuree());
                categorie.setText(movie.getCategorieMovie().getName());
                auteur.setText(movie.getAuthorName());
                description.setText(movie.getDescription());
            }
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItem selectedItem = (SpinnerItem) parent.getItemAtPosition(position);
                int selectedMovieId = selectedItem.getMovieId();
                int selectedSessionId = selectedItem.getSessionId();

                // Afficher un message ou traiter les IDs
                Toast.makeText(getContext(),
                        "Film ID: " + selectedMovieId + ", Session ID: " + selectedSessionId,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}