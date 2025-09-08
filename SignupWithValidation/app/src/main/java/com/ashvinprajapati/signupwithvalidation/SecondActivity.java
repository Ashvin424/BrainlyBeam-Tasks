package com.ashvinprajapati.signupwithvalidation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {
    private TextView nameTextView, passwordTextView, emailTextView, countryTextView, numberTextView, genderTextView, appliedTextView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nameTextView = findViewById(R.id.nameTextView);
        passwordTextView = findViewById(R.id.passwordTextView);
        emailTextView = findViewById(R.id.emailTextView);
        countryTextView = findViewById(R.id.countryTextView);
        numberTextView = findViewById(R.id.numberTextView);
        genderTextView = findViewById(R.id.genderTextView);
        appliedTextView = findViewById(R.id.appliedTextView);

        Intent intent = getIntent();

        nameTextView.setText("Name: " + intent.getStringExtra("name"));
        passwordTextView.setText("Password: " + intent.getStringExtra("password"));
        emailTextView.setText("Email: " + intent.getStringExtra("email"));
        countryTextView.setText("Country: " + intent.getStringExtra("country"));
        numberTextView.setText("Number: " + intent.getStringExtra("number"));
        genderTextView.setText("Gender: " + intent.getStringExtra("gender"));
        appliedTextView.setText("Applied: " + intent.getStringExtra("applied"));

    }
}