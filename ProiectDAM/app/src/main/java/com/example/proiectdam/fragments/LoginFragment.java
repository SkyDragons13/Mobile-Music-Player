package com.example.proiectdam.fragments;

import com.example.proiectdam.DashboardActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.proiectdam.R;
import com.example.proiectdam.database.*;

public class LoginFragment extends Fragment {
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private AppDatabase appDatabase;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize views
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        loginButton = view.findViewById(R.id.loginButton);

        // Initialize database
        appDatabase = AppDatabase.getInstance(requireContext());

        // Set up login button listener
        loginButton.setOnClickListener(v -> handleLogin());

        return view;
    }

    private void handleLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(requireContext(), "Email and password are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check user in database
        new Thread(() -> {
            UserEntity user = appDatabase.userDao().getUserByEmail(email);

            if (user == null) {
                requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(),
                        "User not found. Please register first.", Toast.LENGTH_SHORT).show());
            } else if (!user.getPassword().equals(password)) {
                requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(),
                        "Incorrect password. Please try again.", Toast.LENGTH_SHORT).show());
            } else {
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(requireContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), DashboardActivity.class);
                    intent.putExtra("username", user.getName()); // Pass the username
                    startActivity(intent);
                    requireActivity().finish(); // Close the LoginFragment
                });
            }
        }).start();
    }
}
