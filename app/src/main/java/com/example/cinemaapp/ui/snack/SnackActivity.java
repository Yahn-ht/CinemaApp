package com.example.cinemaapp.ui.snack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaapp.R;
import com.example.cinemaapp.adapter.SnackAdapter;
import com.example.cinemaapp.adapter.SnackAdapter2;
import com.example.cinemaapp.data.api.ReservationRequest;
import com.example.cinemaapp.data.api.TokenManager;
import com.example.cinemaapp.data.repository.ReservationRepository;
import com.example.cinemaapp.data.repository.SnackRepository;
import com.example.cinemaapp.injection.ReservationViewModelFactory;
import com.example.cinemaapp.injection.SnackViewModelFactory;
import com.example.cinemaapp.ui.reservation.ReservationRecapActivity;
import com.example.cinemaapp.viewmodel.ReservationViewModel;
import com.example.cinemaapp.viewmodel.SnackViewModel;

import java.util.ArrayList;
import java.util.Map;

public class SnackActivity extends AppCompatActivity {

    private SnackViewModel snackViewModel;
    private SnackAdapter snackAdapter;
    private Button boisson;
    private Button chips;
    private Button popCorn;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_snack);

        Intent intent = getIntent();

        ReservationRequest reservationRequest = (ReservationRequest) intent.getSerializableExtra("reservationRequest");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Button confirmButton = findViewById(R.id.confirmButton);
        boisson = findViewById(R.id.boisson_button);
        chips = findViewById(R.id.chips_button);
        popCorn = findViewById(R.id.pop_button);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        snackAdapter = new SnackAdapter(new ArrayList<>());
        recyclerView.setAdapter(snackAdapter);


        TokenManager tokenManager = TokenManager.getInstance(getApplicationContext());
        SnackRepository snackRepository = new SnackRepository(tokenManager);
        SnackViewModelFactory factory = new SnackViewModelFactory(snackRepository);

        snackViewModel = new ViewModelProvider(this,factory).get(SnackViewModel.class);

        //snackViewModel = new ViewModelProvider(this.getViewModelStore(), factory).get(SnackViewModel.class);


        snackViewModel.getSnacks().observe((LifecycleOwner) this, snacks -> {
            if (snacks != null) {
                snackAdapter.updateData(snacks);
                snackAdapter = new SnackAdapter(snacks);
                recyclerView.setAdapter(snackAdapter);
            }
        });

        // Observer des snacks filtrés
        snackViewModel.getFilteredSnacks().observe((LifecycleOwner) this, filteredSnacks -> {
            if (filteredSnacks != null) {
                snackAdapter.updateData(filteredSnacks);  // Mets à jour l'adaptateur avec les snacks filtrés
            }
        });

        snackViewModel.getIsLoading().observe((LifecycleOwner) this, isLoading -> {
            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
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



        ReservationViewModel reservationViewModel = new ViewModelProvider(this, new ReservationViewModelFactory(new ReservationRepository(tokenManager))).get(ReservationViewModel.class);
        confirmButton.setOnClickListener(v -> {
            Map<Integer, Integer> selectedSnacks = snackAdapter.getSelectedSnacks();
            assert reservationRequest != null;
            reservationRequest.setSnacks(selectedSnacks);
            //System.out.println(reservationRequest);
            reservationViewModel.getIsLoading().observe((LifecycleOwner) this, isLoading -> {
                        if (isLoading) {
                            progressBar.setVisibility(View.VISIBLE);
                        } else {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
            reservationViewModel.createReservation(reservationRequest);
            reservationViewModel.getReservationResponse().observe((LifecycleOwner) this, reservationResponse -> {
                if (reservationResponse != null) {
                    Intent intent1 = new Intent(this, ReservationRecapActivity.class);
                    intent1.putExtra("reservationResponse", reservationResponse);
                    startActivity(intent1);
                    finish();
                }else {
                    System.out.println("Erreur lors de la création de la réservation.");
                }
            });

        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}