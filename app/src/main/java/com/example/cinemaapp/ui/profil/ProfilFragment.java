package com.example.cinemaapp.ui.profil;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cinemaapp.R;
import com.example.cinemaapp.data.api.TokenManager;
import com.example.cinemaapp.data.repository.ProfilRepository;
import com.example.cinemaapp.data.repository.UserRepository;
import com.example.cinemaapp.injection.ProfilViewModelFactory;
import com.example.cinemaapp.injection.UserModelFactory;
import com.example.cinemaapp.ui.user.LoginActivity;
import com.example.cinemaapp.viewmodel.ProfilViewModel;
import com.example.cinemaapp.viewmodel.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
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
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView nom = view.findViewById(R.id.nom);
        EditText name = view.findViewById(R.id.name);
        EditText email = view.findViewById(R.id.email);
        TextView change_button = view.findViewById(R.id.change_button);
        TextView deconnexion = view.findViewById(R.id.deconnexion);
        TokenManager tokenManager = TokenManager.getInstance(getContext());
        ProfilRepository profilRepository = new ProfilRepository(tokenManager);
        ProfilViewModelFactory factory = new ProfilViewModelFactory(profilRepository);
        ProfilViewModel profilViewModel = new ViewModelProvider(this,factory).get(ProfilViewModel.class);

        profilViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            System.out.println("user: "+user);
            if (user != null) {
                nom.setText(user.getName());
                name.setText(user.getName());
                email.setText(user.getEmail());
            }

            name.setEnabled(false);
            email.setEnabled(false);
        });

        profilViewModel.userInfo();

        deconnexion.setOnClickListener(v -> {
            tokenManager.clearToken();
            // Rediriger vers la page de connexion
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

    }
}