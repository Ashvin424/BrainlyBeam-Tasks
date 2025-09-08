package com.ashvinprajapati.signupwithvalidation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private EditText nameEditText, passwordEditText, emailEditText, numberEditText;
    private AutoCompleteTextView countryEditText;
    private Spinner genderSpinner;
    private RadioGroup appliedRadioGroup;
    private MaterialButton signUpButton;
    private RadioButton yesRadioButton, noRadioButton;
    String[] countries={"India","Australia","West indies","indonesia","Indiana",
            "South Africa","England","Bangladesh","Srilanka","singapore"};
    String[] genders={"Select Gender","Male","Female","Other"};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nameEditText = findViewById(R.id.nameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        emailEditText = findViewById(R.id.emailEditText);
        countryEditText = findViewById(R.id.countryEditText);
        genderSpinner = findViewById(R.id.genderSpinner);
        appliedRadioGroup = findViewById(R.id.appliedRadioGroup);
        signUpButton = findViewById(R.id.signupBtn);
        yesRadioButton = findViewById(R.id.yesRadioButton);
        noRadioButton = findViewById(R.id.noRadioButton);
        numberEditText = findViewById(R.id.numberEditText);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,countries);
        countryEditText.setThreshold(3);
        countryEditText.setAdapter(adapter);

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genders);
        genderSpinner.setAdapter(genderAdapter);

        signUpButton.setOnClickListener(v -> signUp());

    }

    private void signUp() {
        String name = nameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String country = countryEditText.getText().toString();
        String gender = genderSpinner.getSelectedItem().toString();
        String applied = appliedRadioGroup.getCheckedRadioButtonId() == R.id.yesRadioButton ? "Yes" : "No";
        String number = numberEditText.getText().toString();

        String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";


        if (name.isEmpty()){
            nameEditText.setError("Please enter your name");
        }
        if (password.isEmpty()){
            passwordEditText.setError("Please enter your password");
        }
        if (email.isEmpty()){
            emailEditText.setError("Please enter your email");
        }
        if (!email.matches(regex)){
            emailEditText.setError("Please enter valid email");
        }
        if (country.isEmpty()){
            countryEditText.setError("Please enter your country");
        }
        if (gender.equals("Select Gender")){
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
        }
        if (number.isEmpty()){
            numberEditText.setError("Please enter your number");
        } else if (number.length() > 10 || number.length() < 10){
            numberEditText.setError("Please enter valid number");
        }

        if (!name.isEmpty() && !password.isEmpty() && !email.isEmpty() && !country.isEmpty() && !gender.equals("Select Gender") && number.length() == 10 && email.matches(regex)){
            Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("password", password);
            intent.putExtra("email", email);
            intent.putExtra("country", country);
            intent.putExtra("gender", gender);
            intent.putExtra("applied", applied);
            intent.putExtra("number", number);
            startActivity(intent);
            finish();
        }

    }
}