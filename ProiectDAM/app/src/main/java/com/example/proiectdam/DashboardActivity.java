package com.example.proiectdam;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proiectdam.database.AppDatabase;
import com.example.proiectdam.database.UserEntity;
import com.example.proiectdam.database.UserAdapter;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DashboardActivity extends AppCompatActivity {

    private TextView welcomeMessage;
    private RecyclerView userTable;

    private UserAdapter userAdapter;
    private Button musicPlayerButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize UI components
        welcomeMessage = findViewById(R.id.welcomeMessage);
        userTable = findViewById(R.id.userTable);
        musicPlayerButton = findViewById(R.id.musicPlayerButton);


        // Get the intent and display the welcome message
        Intent intent = getIntent();
        String userName = intent.getStringExtra("username");
        welcomeMessage.setText("Welcome, " + userName + "!");

        // Setup RecyclerView for the user table
        userTable.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter();
        userTable.setAdapter(userAdapter);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                AppDatabase database = AppDatabase.getInstance(this);
                List<UserEntity> users = database.userDao().getAllUsers();

                // Update UI on the main thread
                runOnUiThread(() -> userAdapter.setUsers(users));
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(DashboardActivity.this, "Failed to load users", Toast.LENGTH_SHORT).show());
            }
        });
        musicPlayerButton.setOnClickListener(view -> {
            //Intent musicIntent = new Intent(DashboardActivity.this, MusicPlayerActivity.class);
            //startActivity(musicIntent);
        });
    }
}
