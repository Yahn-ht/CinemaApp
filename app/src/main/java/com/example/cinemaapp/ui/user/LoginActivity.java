package com.example.cinemaapp.ui.user;

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

import com.example.cinemaapp.MainActivity;
import com.example.cinemaapp.R;
import com.example.cinemaapp.data.api.LoginRequest;
import com.example.cinemaapp.data.api.TokenManager;
import com.example.cinemaapp.data.repository.UserRepository;
import com.example.cinemaapp.injection.UserModelFactory;
import com.example.cinemaapp.ui.register.RegisterActivity;
import com.example.cinemaapp.viewmodel.UserViewModel;

public class LoginActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        TokenManager tokenManager = TokenManager.getInstance(this);
        //TokenManager tokenManager = (requireContext());
        userViewModel = new ViewModelProvider(this, UserModelFactory.getInstance(new UserRepository(), tokenManager)).get(UserViewModel.class);
        EditText emailEditText = findViewById(R.id.email_text);
        EditText passwordEditText = findViewById(R.id.password_text);
        Button button = findViewById(R.id.connect_button);
        TextView textView = findViewById(R.id.register_text);

        button.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                LoginRequest request = new LoginRequest();
                request.setUsername(email);
                request.setPassword(password);
                userViewModel.login(request);
            } else {
                Toast.makeText(this, "Email and password are required", Toast.LENGTH_SHORT).show();
            }
        });

        textView.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        userViewModel.getStatusMessage().observe((LifecycleOwner) this, message -> {
            if (message.startsWith("Login successful")) {
                // Navigation vers HomeFragment
                Intent intent = new Intent(this, MainActivity.class);
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