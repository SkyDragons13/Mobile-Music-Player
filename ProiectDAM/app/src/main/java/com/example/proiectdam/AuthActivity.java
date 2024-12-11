package com.example.proiectdam;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.proiectdam.fragments.GuestFragment;
import com.example.proiectdam.fragments.LoginFragment;
import com.example.proiectdam.fragments.RegisterFragment;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.auth), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.btnLogin).setOnClickListener(view -> loadFragment(new LoginFragment()));
        findViewById(R.id.btnRegister).setOnClickListener(view -> loadFragment(new RegisterFragment()));
        findViewById(R.id.btnGuest).setOnClickListener(view -> loadFragment(new GuestFragment()));

        // Default fragment
        loadFragment(new LoginFragment());
    }
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}