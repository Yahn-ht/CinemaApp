package com.example.cinemaapp.ui.recherche;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cinemaapp.R;
import com.example.cinemaapp.adapter.RechercheAdapter;
import com.example.cinemaapp.ui.favorie.FavorieItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RechercheFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RechercheFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<RechercheItem> RechercheItem;
    private List<RechercheItem> itemsList;
    private List<RechercheItem> filteredList;
    private EditText searchEditText;
    private ImageButton searchButton;
    private ListView listView;
    private RechercheAdapter adapter;

    public RechercheFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RechercheFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RechercheFragment newInstance(String param1, String param2) {
        RechercheFragment fragment = new RechercheFragment();
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
        return inflater.inflate(R.layout.fragment_recherche, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchEditText = view.findViewById(R.id.searchEditText);
        searchButton = view.findViewById(R.id.searchButton);
        listView = view.findViewById(R.id.listView);

        // Créer une liste d'éléments à afficher
        itemsList = new ArrayList<>();
        itemsList.add(new RechercheItem(R.drawable.img, "L'ombre et le Trône", "Un film captivant sur le pouvoir."));
        itemsList.add(new RechercheItem(R.drawable.img, "Le Voyageur", "Un road-trip fascinant."));
        itemsList.add(new RechercheItem(R.drawable.img, "Les Étoiles", "Une exploration cosmique."));
        itemsList.add(new RechercheItem(R.drawable.img, "L'Odyssée", "Un chef-d'œuvre épique."));
        itemsList.add(new RechercheItem(R.drawable.img, "Aventures sous-marines", "Une plongée profonde."));
        itemsList.add(new RechercheItem(R.drawable.img, "Horizons lointains", "Un récit d'aventure."));

        // Initialiser la liste filtrée (qui sera mise à jour)
        filteredList = new ArrayList<>(itemsList);

        // Initialiser l'adaptateur et lier au ListView
        adapter = new RechercheAdapter(getContext(), filteredList);
        listView.setAdapter(adapter);

        // Ajouter un écouteur pour l'autocomplétion
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Rien à faire ici
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Rien à faire ici
            }
        });

        // Ajouter un écouteur pour le bouton de recherche
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch(searchEditText.getText().toString());
            }
        });

        //return view;
    }

    /**
     * Filtrer la liste en fonction de la saisie dans le champ de recherche.
     *
     * @param query La chaîne de caractères saisie.
     */
    private void filterList(String query) {
        filteredList.clear();
        if (!query.isEmpty()) {
            for (RechercheItem item : itemsList) {
                if (item.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(item);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * Effectuer une recherche précise et afficher un seul élément correspondant.
     *
     * @param query Le mot exact à rechercher.
     */
    private void performSearch(String query) {
        filteredList.clear();
        for (RechercheItem item : itemsList) {
            if (item.getTitle().equalsIgnoreCase(query)) {
                filteredList.add(item);
                adapter.notifyDataSetChanged();
                return;
            }
        }
        // Si aucun résultat trouvé
        Toast.makeText(getContext(), "Not Found", Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }
}