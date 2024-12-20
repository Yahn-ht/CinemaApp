package com.example.cinemaapp.ui.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinemaapp.R;
import com.example.cinemaapp.data.api.LoginRequest;
import com.example.cinemaapp.data.api.TokenManager;
import com.example.cinemaapp.data.repository.UserRepository;
import com.example.cinemaapp.injection.UserModelFactory;
import com.example.cinemaapp.viewmodel.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private UserViewModel userViewModel;

    public LoginFragment() {
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
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TokenManager tokenManager = TokenManager.getInstance(requireContext());
        //TokenManager tokenManager = (requireContext());
        userViewModel = new ViewModelProvider(this, UserModelFactory.getInstance(new UserRepository(), tokenManager)).get(UserViewModel.class);
        EditText emailEditText = view.findViewById(R.id.email_text);
        EditText passwordEditText = view.findViewById(R.id.password_text);
        Button button = view.findViewById(R.id.connect_button);
        TextView textView = view.findViewById(R.id.register_text);

        button.setOnClickListener(v -> {
           String email = emailEditText.getText().toString().trim();
           String password = passwordEditText.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                LoginRequest request = new LoginRequest();
                request.setUsername(email);
                request.setPassword(password);
                userViewModel.login(request);
            } else {
                Toast.makeText(requireContext(), "Email and password are required", Toast.LENGTH_SHORT).show();
            }
        });

        textView.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_login_to_register);
        });

        userViewModel.getStatusMessage().observe(getViewLifecycleOwner(), message -> {
            if (message.startsWith("Login successful")) {
                // Navigation vers HomeFragment
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_login_to_home);
            } else if (message.startsWith("Error")) {
                // Afficher un message d'erreur
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

       // return view;
    }
}