package com.example.cinemaapp.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.cinemaapp.R;
import com.example.cinemaapp.data.api.RegisterRequest;
import com.example.cinemaapp.data.api.TokenManager;
import com.example.cinemaapp.data.repository.UserRepository;
import com.example.cinemaapp.injection.UserModelFactory;
import com.example.cinemaapp.ui.user.LoginActivity;
import com.example.cinemaapp.viewmodel.UserViewModel;

public class RegisterActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);


        TokenManager tokenManager = TokenManager.getInstance(this);
        //TokenManager tokenManager = (requireContext());
        userViewModel = new ViewModelProvider(this, UserModelFactory.getInstance(new UserRepository(), tokenManager)).get(UserViewModel.class);
        EditText nomEdit= findViewById(R.id.nom);
        EditText emailEdit= findViewById(R.id.email);
        EditText passwordEdit = findViewById(R.id.password);
        EditText conf_passwordEdit = findViewById(R.id.conf_password);
        Button register = findViewById(R.id.register);
        TextView login_text = findViewById(R.id.login_text);

        register.setOnClickListener(v -> {
            String nom= nomEdit.getText().toString().trim();
            String email = emailEdit.getText().toString().trim();
            String password= passwordEdit.getText().toString().trim();
            String conf_password= conf_passwordEdit.getText().toString().trim();
            if(!conf_password.equals(password)){
                Toast.makeText(this,"The password must be the same",Toast.LENGTH_SHORT).show();
            }
            else if (!email.isEmpty() && !password.isEmpty() && !nom.isEmpty()) {

                RegisterRequest request = new RegisterRequest();
                request.setName(nom);
                request.setEmail(email);
                request.setPassword(password);
                userViewModel.register(request);
            } else {
                Toast.makeText(this, "All fields must be filled in.", Toast.LENGTH_SHORT).show();
            }
        });

        login_text.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        userViewModel.getStatusMessage().observe((LifecycleOwner) this, message -> {
            if (message.startsWith("Registration successful")) {

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            } else if (message.startsWith("Error")) {
                // Afficher un message d'erreur
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}