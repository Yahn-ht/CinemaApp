package com.example.cinemaapp.ui.reservation;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaapp.MainActivity;
import com.example.cinemaapp.R;
import com.example.cinemaapp.adapter.PlaceAdapter;
import com.example.cinemaapp.adapter.SnackAdapter2;
import com.example.cinemaapp.data.api.ReservationResponse;

public class ReservationRecapActivity extends AppCompatActivity {
    private TextView tvTotalAmount, tvMovieName;
    private RecyclerView rvPlaces, rvSnacks;
    private LinearLayout sectionSnacks;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reservation_recap);

        // Liaison des vues
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvMovieName = findViewById(R.id.tvMovieName);
        rvPlaces = findViewById(R.id.rvPlaces);
        rvSnacks = findViewById(R.id.rvSnacks);
        sectionSnacks = findViewById(R.id.sectionSnacks);
        backButton = findViewById(R.id.backtohome_button);

        // Récupération de l'objet ReservationResponse depuis l'intent
        ReservationResponse reservation = (ReservationResponse) getIntent().getSerializableExtra("reservationResponse");

        // Afficher les données
        tvTotalAmount.setText("Montant total : " + reservation.getMontant() + "Dh");
        tvMovieName.setText(reservation.getMovieReserve().getName());

        // Configurer RecyclerView des places
        rvPlaces.setLayoutManager(new LinearLayoutManager(this));
        rvPlaces.setAdapter(new PlaceAdapter(reservation.getPlaces()));

        // Configurer RecyclerView des snacks si disponibles
        if (reservation.getSnackReservations() != null && !reservation.getSnackReservations().isEmpty()) {
            sectionSnacks.setVisibility(View.VISIBLE); // Affiche la section des snacks
            rvSnacks.setLayoutManager(new LinearLayoutManager(this));
            rvSnacks.setAdapter(new SnackAdapter2(reservation.getSnackReservations()));
        } else {
            sectionSnacks.setVisibility(View.GONE); // Cache la section des snacks
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReservationRecapActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Bloquer ou personnaliser le retour en arrière
                Toast.makeText(getApplicationContext(), "Retour désactivé", Toast.LENGTH_SHORT).show();
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }





}
