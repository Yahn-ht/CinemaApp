package com.example.cinemaapp.ui.place
;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemaapp.R;

public class PlaceFragment extends Fragment {

    private static final int ROWS = 5; // Nombre de rangées
    private static final int COLUMNS = 4; // Nombre de colonnes

    private enum SeatState {
        AVAILABLE,
        RESERVED,
        UNAVAILABLE
    }

    public PlaceFragment() {
        // Constructeur public vide requis pour les fragments
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflater le layout pour ce fragment
        View rootView = inflater.inflate(R.layout.fragment_place, container, false);

        GridLayout gridLayout = rootView.findViewById(R.id.GridLayout);

        // Images associées aux états des sièges
        int availableSeatImage = R.drawable.chair_dispo; // Image pour disponible
        int reservedSeatImage = R.drawable.chair_checked;   // Image pour réservé
        int unavailableSeatImage = R.drawable.img; // Image pour non disponible

        // Matrice pour représenter les états initiaux des sièges
        SeatState[][] seatStates = new SeatState[ROWS][COLUMNS];

        // Initialiser les états (modifiable en fonction de vos données)
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (row == 0 && col < 2) {
                    seatStates[row][col] = SeatState.UNAVAILABLE; // Non disponible
                } else if (row == 1 && col == 3) {
                    seatStates[row][col] = SeatState.RESERVED; // Réservé
                } else {
                    seatStates[row][col] = SeatState.AVAILABLE; // Disponible
                }
            }
        }

        // Ajouter les sièges au GridLayout
        gridLayout.setColumnCount(COLUMNS); // Assurez-vous que le nombre de colonnes est correct
        gridLayout.setRowCount(ROWS); // Assurez-vous que le nombre de rangées est correct

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                ImageView seatImageView = new ImageView(requireContext());

                // Définir l'image selon l'état initial
                switch (seatStates[row][col]) {
                    case AVAILABLE:
                        seatImageView.setImageResource(availableSeatImage);
                        break;
                    case RESERVED:
                        seatImageView.setImageResource(reservedSeatImage);
                        break;
                    case UNAVAILABLE:
                        seatImageView.setImageResource(unavailableSeatImage);
                        break;
                }

                // Configurer le clic pour les sièges disponibles
                if (seatStates[row][col] == SeatState.AVAILABLE) {
                    int finalRow = row;
                    int finalCol = col;
                    seatImageView.setOnClickListener(new View.OnClickListener() {
                        private boolean isSelected = false;

                        @Override
                        public void onClick(View v) {
                            if (isSelected) {
                                seatImageView.setImageResource(availableSeatImage); // Désélectionner
                                isSelected = false;
                            } else {
                                seatImageView.setImageResource(reservedSeatImage); // Sélectionner
                                isSelected = true;
                            }
                            Toast.makeText(requireContext(),
                                    "Seat Selected: Row " + (finalRow + 1) + ", Column " + (finalCol + 1),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                // Ajouter l'image au GridLayout
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(row);
                params.columnSpec = GridLayout.spec(col);
                params.width = 100;
                params.height = 100;
                params.setMargins(120, 50, 10, 10);

                gridLayout.addView(seatImageView, params);
            }
        }

        return rootView;
    }
}
