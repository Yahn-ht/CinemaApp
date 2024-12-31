package com.example.cinemaapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cinemaapp.data.api.TokenManager;
import com.example.cinemaapp.ui.user.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 3000; // 3 secondes


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        TokenManager tokenManager = TokenManager.getInstance(getApplicationContext());

        // Animation de transition
        findViewById(R.id.logo).startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.fade_in));

        // Délai avant de lancer l'activité principale
        new Handler().postDelayed(() -> {
            // Lancer l'activité principale
            if (tokenManager.getToken() != null) {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out); // Transition
            // Terminer le SplashScreen pour ne pas revenir dessus
            finish();
        }, SPLASH_DELAY);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}