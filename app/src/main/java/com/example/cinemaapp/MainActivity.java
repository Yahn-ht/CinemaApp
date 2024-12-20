package com.example.cinemaapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.cinemaapp.data.api.TokenManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialisation des composants
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //FloatingActionButton fabCentral = findViewById(R.id.fab_central);
        TokenManager tokenManager = TokenManager.getInstance(this);

        // Récupérer NavController à partir du NavHostFragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainer);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        } else {
            throw new IllegalStateException("NavHostFragment is null");
        }

        // Associer le BottomNavigationView avec le NavController
        //NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // Masquer le BottomNavigationView pour les écrans Login et Register
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.loginFragment || destination.getId() == R.id.registerFragment) {
                bottomNavigationView.setVisibility(View.GONE);
            } else {
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });

        // Vérifier si un token existe pour rediriger vers HomeFragment
        if (tokenManager.getToken() != null) {
            navController.navigate(R.id.homeFragment);
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_films) {
                navController.navigate(R.id.homeFragment);
                Toast.makeText(this, "Films", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.nav_search) {
                Toast.makeText(this, "Recherche", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.searchFragment);
            } else if (item.getItemId() == R.id.nav_history) {
                Toast.makeText(this, "Historique", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.historique_fragment);
            } else if (item.getItemId() == R.id.nav_profile) {
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.profileFragment);
            } else if (item.getItemId() == R.id.nav_favorite) {
                navController.navigate(R.id.favoriteFragment);
            }
            return true;

        });

        // Gestion des insets pour le layout principal
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ViewCompat.setOnApplyWindowInsetsListener(bottomNavigationView, (v, insets) -> {
            v.setPadding(0, 0, 0, 0);
            return insets;
        });
    }
}
