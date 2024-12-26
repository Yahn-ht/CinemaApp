package com.example.cinemaapp.ui.snack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaapp.R;
import com.example.cinemaapp.adapter.SnackAdapter;
import com.example.cinemaapp.data.api.ReservationRequest;
import com.example.cinemaapp.data.repository.ReservationRepository;
import com.example.cinemaapp.viewmodel.ReservationViewModel;
import com.example.cinemaapp.viewmodel.SalleViewModel;
import com.example.cinemaapp.viewmodel.SnackViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SnackFragment extends Fragment {
    private SnackViewModel snackViewModel;
    private SnackAdapter snackAdapter;
    private Button boisson;
    private Button chips;
    private Button popCorn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_snack, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ReservationRequest reservationRequest = (ReservationRequest) getArguments().getSerializable("reservationRequest");

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        Button confirmButton = view.findViewById(R.id.confirmButton);
        boisson = view.findViewById(R.id.boisson_button);
        chips = view.findViewById(R.id.chips_button);
        popCorn = view.findViewById(R.id.pop_button);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        snackAdapter = new SnackAdapter(new ArrayList<>());
        recyclerView.setAdapter(snackAdapter);


        snackViewModel = new ViewModelProvider(this).get(SnackViewModel.class);
        snackViewModel.getSnacks().observe(getViewLifecycleOwner(), snacks -> {
            if (snacks != null) {
                snackAdapter.updateData(snacks);
                snackAdapter = new SnackAdapter(snacks);
                recyclerView.setAdapter(snackAdapter);
            }
        });

        // Observer des snacks filtrés
        snackViewModel.getFilteredSnacks().observe(getViewLifecycleOwner(), filteredSnacks -> {
            if (filteredSnacks != null) {
                snackAdapter.updateData(filteredSnacks);  // Mets à jour l'adaptateur avec les snacks filtrés
            }
        });

        boisson.setOnClickListener(v -> {
            snackViewModel.filterSnacksByCategory("Boisson");
        });

        chips.setOnClickListener(v -> {
            snackViewModel.filterSnacksByCategory("Chips");
        });

        popCorn.setOnClickListener(v -> {
            snackViewModel.filterSnacksByCategory("PopCorn");
        });



        confirmButton.setOnClickListener(v -> {
            Map<Integer, Integer> selectedSnacks = snackAdapter.getSelectedSnacks();
            reservationRequest.setSnacks(selectedSnacks);
            //System.out.println(reservationRequest);
            ReservationViewModel reservationViewModel = new ViewModelProvider(this).get(ReservationViewModel.class);
            reservationViewModel.createReservation(reservationRequest);
            reservationViewModel.getReservationResponse().observe(getViewLifecycleOwner(), reservationResponse -> {
                if (reservationResponse != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("reservationResponse", reservationResponse);
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.reservationRecapFragment, bundle);
                }
            });

        });

    }
}
