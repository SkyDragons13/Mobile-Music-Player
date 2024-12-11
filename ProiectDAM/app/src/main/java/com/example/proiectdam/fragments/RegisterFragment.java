package com.example.proiectdam.fragments;

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

import com.example.proiectdam.database.AppDatabase;
import com.example.proiectdam.database.UserEntity;

import com.example.proiectdam.R;
public class RegisterFragment extends Fragment {

    private EditText nameEditText, emailEditText, emailRepeatEditText, passwordEditText, passwordRepeatEditText;
    private Button signUpButton;

    private AppDatabase appDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // Initialize the database
        appDatabase = AppDatabase.getInstance(requireContext());

        // Initialize views
        nameEditText = view.findViewById(R.id.signUpName);
        emailEditText = view.findViewById(R.id.signUpEmail);
        emailRepeatEditText = view.findViewById(R.id.signUpEmailRepeat);
        passwordEditText = view.findViewById(R.id.signUpPassword);
        passwordRepeatEditText = view.findViewById(R.id.signUpPasswordRepeat);
        signUpButton = view.findViewById(R.id.signUpButton);

        // Set up the sign-up button click listener
        signUpButton.setOnClickListener(v -> handleRegister());

        return view;
    }

    private void handleRegister() {
        // Get input values
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String emailRepeat = emailRepeatEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String passwordRepeat = passwordRepeatEditText.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(emailRepeat)
                || TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordRepeat)) {
            Toast.makeText(requireContext(), "All fields are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!email.equals(emailRepeat)) {
            Toast.makeText(requireContext(), "Emails do not match.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(passwordRepeat)) {
            Toast.makeText(requireContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save user to database
        new Thread(() -> {
            // Check if email already exists
            UserEntity existingUser = appDatabase.userDao().getUserByEmail(email);
            if (existingUser != null) {
                requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(),
                        "Email already registered.", Toast.LENGTH_SHORT).show());
                return;
            }

            // Create new user and insert it into the database
            UserEntity newUser = new UserEntity(name, email, password);
            appDatabase.userDao().insert(newUser);

            requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), "Registration successful!", Toast.LENGTH_SHORT).show();
                // Optionally, navigate to another fragment or clear input fields
                clearFields();
            });
        }).start();
    }

    private void clearFields() {
        nameEditText.setText("");
        emailEditText.setText("");
        emailRepeatEditText.setText("");
        passwordEditText.setText("");
        passwordRepeatEditText.setText("");
    }
}
