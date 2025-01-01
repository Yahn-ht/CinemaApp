package com.example.cinemaapp.ui.salle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.cinemaapp.R;
import com.example.cinemaapp.adapter.SeatAdapter;
import com.example.cinemaapp.data.api.ReservationRequest;
import com.example.cinemaapp.data.model.Movie;
import com.example.cinemaapp.data.model.Place;
import com.example.cinemaapp.viewmodel.SalleViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SalleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SalleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SalleViewModel roomViewModel;
    private RecyclerView recyclerView;
    private SeatAdapter seatAdapter;
    private List<Place> seats = new ArrayList<>();
    private List<Integer> placesSelected = new ArrayList<>();
    private Button nextButton;

    public SalleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SalleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SalleFragment newInstance(String param1, String param2) {
        SalleFragment fragment = new SalleFragment();
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
        return inflater.inflate(R.layout.fragment_salle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ReservationRequest reservationRequest = (ReservationRequest) getArguments().getSerializable("reservationRequest");

        nextButton = view.findViewById(R.id.buttonNext);
        // Configuration du RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewSeats);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5)); // 5 colonnes par exemple
        //assert reservationRequest != null;
        seatAdapter = new SeatAdapter(getContext(), seats , seat -> {
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
        int roomId = getArguments().getInt("roomId");

        // Initialisation du ViewModel
        roomViewModel = new ViewModelProvider(this).get(SalleViewModel.class);
        roomViewModel.getRoomById(roomId).observe(getViewLifecycleOwner(), room -> {
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
                bundle.putSerializable("reservationRequest", reservationRequest);
                Navigation.findNavController(view).navigate(R.id.snackFragment, bundle);

            }else{
                Toast.makeText(getContext(), "Veuillez sélectionner au moins une place", Toast.LENGTH_SHORT).show();
            }
        });
    }
}