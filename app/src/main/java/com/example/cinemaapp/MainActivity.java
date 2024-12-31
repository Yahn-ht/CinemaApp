package com.example.cinemaapp;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.cinemaapp.data.api.TokenManager;
import com.example.cinemaapp.ui.favorie.FavorieFragment;
import com.example.cinemaapp.ui.historique.HistoriqueFragment;
import com.example.cinemaapp.ui.home.HomeFragment;
import com.example.cinemaapp.ui.profil.ProfilFragment;
import com.example.cinemaapp.ui.recherche.RechercheFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private BottomNavigationView bottomNavigationView;
    private long backPressedTime = 0;
    private static final long BACK_PRESS_THRESHOLD = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialisation des composants
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        ColorStateList colorStateList = ContextCompat.getColorStateList(this, R.color.menu_item_color);
        TokenManager tokenManager = TokenManager.getInstance(getApplicationContext());
        bottomNavigationView.setItemIconTintList(colorStateList);
        bottomNavigationView.setItemTextColor(colorStateList);
        // Récupérer NavController à partir du NavHostFragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainer);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        } else {
            throw new IllegalStateException("NavHostFragment is null");
        }

         //Associer le BottomNavigationView avec le NavController
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        /*
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
        }*/


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
                Toast.makeText(this, "Favorie", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.favoriteFragment);
            }
            return true;

        });


        // Gestion du bouton retour
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Obtenez le fragment actuel
                Fragment currentFragment = getSupportFragmentManager()
                        .findFragmentById(R.id.fragmentContainer)
                        .getChildFragmentManager()
                        .getFragments()
                        .get(0);

                // Vérifiez si le fragment actuel est HomeFragment
                if (currentFragment instanceof HomeFragment) {
                    // Gérer le double clic pour quitter
                    handleBackPressForHomeFragment();
                } else {
                    // Permettre le retour par défaut
                    setEnabled(false);
                    onBackPressed();


                }
            }
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

    private void handleBackPressForHomeFragment() {
        if (System.currentTimeMillis() - backPressedTime < BACK_PRESS_THRESHOLD) {
            // Quittez l'application si le deuxième clic est dans le délai
            finishAffinity();
        } else {
            // Premier clic - Affichez un message à l'utilisateur
            backPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "Appuyez à nouveau pour quitter", Toast.LENGTH_SHORT).show();
        }
    }


}
