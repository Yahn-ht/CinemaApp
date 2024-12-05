package com.example.cinemaapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class SnackFragment extends Fragment {

    private GridView gridView;
    private Button boissonButton;
    private Button popButton;
    private Button chipsButton;
    private ArrayList<SnackItem> snackItems;
    private ArrayList<SnackItem> snackItemsBoisson;
    private ArrayList<SnackItem> snackItemsChips;
    private ArrayList<SnackItem> snackItemsPop;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private  final String ARG_PARAM1 = "param1";
    private final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SnackFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public  SnackFragment newInstance(String param1, String param2) {
        SnackFragment fragment = new SnackFragment();
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
        return inflater.inflate(R.layout.fragment_snack, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridView = view.findViewById(R.id.myGridView);
        boissonButton = view.findViewById(R.id.boisson_button);
        popButton = view.findViewById(R.id.pop_button);
        chipsButton = view.findViewById(R.id.chips_button);
        ;
        snackItems = new ArrayList<>();
        snackItemsBoisson = new ArrayList<>();
        snackItemsChips = new ArrayList<>();
        snackItemsPop = new ArrayList<>();
        loadSnackData();

        SnackAdapter adapter = new SnackAdapter(getContext(), snackItems, null);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((AdapterView<?> parent, View view1, int position, long id) -> {
            SnackItem clickedItem = snackItems.get(position);
            Toast.makeText(getContext(), "Vous avez sélectionné : " + clickedItem.getTitle(), Toast.LENGTH_SHORT).show();
        });



    }

        private void loadSnackData () {

            snackItems.clear();
            snackItems.add(new SnackItem(R.drawable.coca_img1, "Coca", "14.5dh", "Boisson"));
            snackItems.add(new SnackItem(R.drawable.chips_img, "Chips", "10.0dh", "Chips"));
            snackItems.add(new SnackItem(R.drawable.popcorn_img, "Popcorn", "12.0dh", "Popcorn"));
            snackItems.add(new SnackItem(R.drawable.coca_img1, "Coca", "14.5dh", "Boisson"));
            snackItems.add(new SnackItem(R.drawable.chips_img, "Chips", "10.0dh", "Chips"));
            snackItems.add(new SnackItem(R.drawable.popcorn_img, "Popcorn", "12.0dh", "Popcorn"));
            snackItems.add(new SnackItem(R.drawable.coca_img1, "Coca", "14.5dh", "Boisson"));
            snackItems.add(new SnackItem(R.drawable.chips_img, "Chips", "10.0dh", "Chips"));
            snackItems.add(new SnackItem(R.drawable.popcorn_img, "Popcorn", "12.0dh", "Popcorn"));
            snackItems.add(new SnackItem(R.drawable.coca_img1, "Coca", "14.5dh", "Boisson"));
            snackItems.add(new SnackItem(R.drawable.chips_img, "Chips", "10.0dh", "Chips"));
            snackItems.add(new SnackItem(R.drawable.popcorn_img, "Popcorn", "12.0dh", "Popcorn"));
            boissonButton.setOnClickListener (new View.OnClickListener() {
                @Override
                public void onClick(View v2) {
                    loadSnackDataBoisson();
                    SnackAdapter Boissonadapter = new SnackAdapter(getContext(), snackItemsBoisson, null);
                    gridView.setAdapter(Boissonadapter);
                }
            });

            chipsButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v3) {
                    loadSnackDataChips();
                    SnackAdapter Chipsadapter = new SnackAdapter(getContext(), snackItemsChips, null);
                    gridView.setAdapter(Chipsadapter);
                }
            });

            popButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v4) {
                    loadSnackDataPop();
                    SnackAdapter Popadapter = new SnackAdapter(getContext(), snackItemsPop, null);
                    gridView.setAdapter(Popadapter);
                }
            });
        }
    private void loadSnackDataBoisson () {
        snackItemsBoisson.clear();
        snackItemsBoisson.add(new SnackItem(R.drawable.coca_img1, "Coca", "14.5dh", "Boisson"));
        snackItemsBoisson.add(new SnackItem(R.drawable.coca_img1, "Popcorn", "14.5dh", "Boisson"));
        snackItemsBoisson.add(new SnackItem(R.drawable.coca_img1, "Coca", "14.5dh", "Boisson"));
    }
    public void loadSnackDataChips () {
        snackItemsChips.clear();
        snackItemsChips.add(new SnackItem(R.drawable.chips_img, "Coca", "10.0dh", "Chips"));
        snackItemsChips.add(new SnackItem(R.drawable.chips_img, "Chips", "10.0dh", "Chips"));
        snackItemsChips.add(new SnackItem(R.drawable.chips_img, "Popcorn", "10.0dh", "Chips"));
        snackItemsChips.add(new SnackItem(R.drawable.chips_img, "Coca", "10.0dh", "Chips"));
    }
    private void loadSnackDataPop () {
        snackItemsPop.clear();
        snackItemsPop.add(new SnackItem(R.drawable.popcorn_img, "Coca", "12.0dh", "Popcorn"));
        snackItemsPop.add(new SnackItem(R.drawable.popcorn_img, "Chips", "12.0dh", "Popcorn"));
        snackItemsPop.add(new SnackItem(R.drawable.popcorn_img, "Popcorn", "12.0dh", "Popcorn"));
        snackItemsPop.add(new SnackItem(R.drawable.popcorn_img, "Coca", "12.0dh", "Porpcorn"));
        snackItemsPop.add(new SnackItem(R.drawable.popcorn_img, "Chips", "12.0dh", "Popcorn"));
    }

}