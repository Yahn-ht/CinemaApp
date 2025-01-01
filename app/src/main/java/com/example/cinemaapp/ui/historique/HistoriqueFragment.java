package com.example.cinemaapp.ui.historique;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cinemaapp.R;
import com.example.cinemaapp.adapter.ReservationAdapter;
import com.example.cinemaapp.adapter.SnackAdapter;
import com.example.cinemaapp.data.api.TokenManager;
import com.example.cinemaapp.data.repository.ReservationRepository;
import com.example.cinemaapp.injection.ReservationViewModelFactory;
import com.example.cinemaapp.viewmodel.ReservationViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoriqueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoriqueFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView rvHistorique;
    private ReservationAdapter adapter;
    private TextView emptyView;
    private ProgressBar progressBar;

    public HistoriqueFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoriqueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoriqueFragment newInstance(String param1, String param2) {
        HistoriqueFragment fragment = new HistoriqueFragment();
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
        return inflater.inflate(R.layout.fragment_historique, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialisation de TokenManager
        TokenManager tokenManager = TokenManager.getInstance(requireContext());
        emptyView = view.findViewById(R.id.isEmpty);



        // Initialisation du RecyclerView
        progressBar = view.findViewById(R.id.progressBar);
        rvHistorique = view.findViewById(R.id.rvHistorique);
        rvHistorique.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)); // Ajout du LayoutManager
        adapter = new ReservationAdapter(new ArrayList<>());
        rvHistorique.setAdapter(adapter); // Définir l'adaptateur ici pour éviter des erreurs

        // Configuration de ViewModel
        ReservationViewModel reservationViewModel = new ViewModelProvider(
                this,
                new ReservationViewModelFactory(new ReservationRepository(tokenManager))
        ).get(ReservationViewModel.class);

        // Observer les données des réservations
        reservationViewModel.fetchReservations();
        reservationViewModel.getReservations().observe(getViewLifecycleOwner(), reservations -> {
            if (!reservations.isEmpty()) {
                emptyView.setVisibility(View.GONE);
                adapter.updateReservations(reservations); // Mettre à jour l'adaptateur avec les nouvelles données
            }else {
                emptyView.setVisibility(View.VISIBLE);
            }
        });

        reservationViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}