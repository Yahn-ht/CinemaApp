package com.example.cinemaapp.ui.salle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaapp.R;
import com.example.cinemaapp.adapter.SeatAdapter;
import com.example.cinemaapp.data.api.ReservationRequest;
import com.example.cinemaapp.data.api.TokenManager;
import com.example.cinemaapp.data.model.Place;
import com.example.cinemaapp.data.repository.MovieRepository;
import com.example.cinemaapp.data.repository.SalleRepository;
import com.example.cinemaapp.injection.MovieModelFactory;
import com.example.cinemaapp.injection.SalleViewModelFactory;
import com.example.cinemaapp.ui.snack.SnackActivity;
import com.example.cinemaapp.viewmodel.MovieViewModel;
import com.example.cinemaapp.viewmodel.SalleViewModel;

import java.util.ArrayList;
import java.util.List;

public class SalleActivity extends AppCompatActivity {

    private SalleViewModel roomViewModel;
    private RecyclerView recyclerView;
    private SeatAdapter seatAdapter;
    private List<Place> seats = new ArrayList<>();
    private List<Integer> placesSelected = new ArrayList<>();
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_salle);

        Intent intent = getIntent();
        ReservationRequest reservationRequest = (ReservationRequest) intent.getSerializableExtra("reservationRequest");

        nextButton = findViewById(R.id.buttonNext);
        // Configuration du RecyclerView
        recyclerView = findViewById(R.id.recyclerViewSeats);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5)); // 5 colonnes par exemple
        //assert reservationRequest != null;
        seatAdapter = new SeatAdapter(this, seats , seat -> {
            // Gestion de la sélection
            if (seat.isSelected()) {
                placesSelected.add(seat.getId());
            } else {
                placesSelected.remove(Integer.valueOf(seat.getId()));
            }
            System.out.println(seat);
        }, reservationRequest);
        recyclerView.setAdapter(seatAdapter);

        // Récupération de l'ID de la salle
        int roomId = intent.getIntExtra("roomId", -1);

        TokenManager tokenManager = TokenManager.getInstance(getApplicationContext());
        SalleRepository salleRepository = new SalleRepository(tokenManager);
        SalleViewModelFactory factory = new SalleViewModelFactory(salleRepository);
        roomViewModel = new ViewModelProvider(this, factory).get(SalleViewModel.class);

        roomViewModel.getRoomById(roomId).observe((LifecycleOwner) this, room -> {
            if (room != null && room.getPlaces() != null) {
                seats.clear();
                seats.addAll(room.getPlaces());
                seatAdapter.notifyDataSetChanged();
            }
        });

        nextButton.setOnClickListener(v -> {
            if(!placesSelected.isEmpty()){
                Bundle bundle = new Bundle();
                reservationRequest.setPlaces(placesSelected);
//                bundle.putSerializable("reservationRequest", reservationRequest);
//                Navigation.findNavController(navigate(R.id.snackFragment, bundle);

                Intent intent1 = new Intent(this, SnackActivity.class);
                intent1.putExtra("reservationRequest", reservationRequest);
                startActivity(intent1);

            }else{
                Toast.makeText(this, "Veuillez sélectionner au moins une place", Toast.LENGTH_SHORT).show();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}