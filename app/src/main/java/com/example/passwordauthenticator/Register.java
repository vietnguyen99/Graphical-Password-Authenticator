package com.example.passwordauthenticator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText mFullName, mEmail, mPassword;
    Button mRegisterBtn;
    Button mRed;
    Button mBlue;
    Button mGreen;
    Button mViolet;
    Button mOrange;
    Button mYellow;
    Button mRegisterBtn2;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String colorCode;
    TextView text1;
    ToggleButton btnToggleDark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.textPassword);
        mRegisterBtn = findViewById(R.id.regButton);
        mRegisterBtn2 = findViewById(R.id.regButton2);
        mRed = findViewById(R.id.red);
        mBlue = findViewById(R.id.blue);
        mOrange = findViewById(R.id.orange);
        mViolet = findViewById(R.id.violet);
        mYellow = findViewById(R.id.yellow);
        mGreen = findViewById(R.id.green);
        mLoginBtn = findViewById(R.id.loginText);
        text1 = (TextView) findViewById(R.id.text1);

        fAuth = FirebaseAuth.getInstance();

        // Check to see if user is already logged in
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        btnToggleDark
                = findViewById(R.id.toggleButton);

        // Saving state of our app
        // using SharedPreferences
        SharedPreferences sharedPreferences
                = getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor
                = sharedPreferences.edit();
        final boolean isDarkModeOn
                = sharedPreferences
                .getBoolean(
                        "isDarkModeOn", false);

        // When user reopens the app
        // after applying dark/light mode
        if (isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);
            btnToggleDark.setText(
                    "Disable Dark Mode");
        }
        else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
            btnToggleDark
                    .setText(
                            "Enable Dark Mode");
        }

        btnToggleDark.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view)
                    {
                        // When user taps the enable/disable
                        // dark mode button
                        if (isDarkModeOn) {

                            // if dark mode is on it
                            // will turn it off
                            AppCompatDelegate
                                    .setDefaultNightMode(
                                            AppCompatDelegate
                                                    .MODE_NIGHT_NO);
                            // it will set isDarkModeOn
                            // boolean to false
                            editor.putBoolean(
                                    "isDarkModeOn", false);
                            editor.apply();

                            // change text of Button
                            btnToggleDark.setText(
                                    "Enable Dark Mode");
                        }
                        else {

                            // if dark mode is off
                            // it will turn it on
                            AppCompatDelegate
                                    .setDefaultNightMode(
                                            AppCompatDelegate
                                                    .MODE_NIGHT_YES);

                            // it will set isDarkModeOn
                            // boolean to true
                            editor.putBoolean(
                                    "isDarkModeOn", true);
                            editor.apply();

                            // change text of Button
                            btnToggleDark.setText(
                                    "Disable Dark Mode");
                        }
                    }
                });



        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Receive and enforce requirements for email and password entries
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String fullName = mFullName.getText().toString();

                if (TextUtils.isDigitsOnly(fullName)) {
                    mFullName.setError("Name can only be letters.");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required.");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password must be at least six characters.");
                }

                // Register user in Firebase

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // On successful registration, make text password entry portion invisible and color entry portion visible
                            mYellow.setVisibility(View.VISIBLE);
                            mRed.setVisibility(View.VISIBLE);
                            mBlue.setVisibility(View.VISIBLE);
                            mOrange.setVisibility(View.VISIBLE);
                            mViolet.setVisibility(View.VISIBLE);
                            mGreen.setVisibility(View.VISIBLE);
                            mRegisterBtn.setVisibility(View.INVISIBLE);
                            mFullName.setVisibility(View.INVISIBLE);
                            mEmail.setVisibility(View.INVISIBLE);
                            mPassword.setVisibility(View.INVISIBLE);
                            mRegisterBtn2.setVisibility(View.VISIBLE);
                            mLoginBtn.setVisibility(View.INVISIBLE);
                            text1.setVisibility(View.VISIBLE);

                        } else {
                            Toast.makeText(Register.this, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }


                });
            }
        });
        // Each button adds a color signifier to the string representing the color entry code
        mOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text1.getText().equals("ABC")) {
                    text1.setText("");
                }

                text1.append("O");
            }
        });
        mGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text1.getText().equals("ABC")) {
                    text1.setText("");
                }

                text1.append("G");
            }
        });
        mRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text1.getText().equals("ABC")) {
                    text1.setText("");
                }

                text1.append("R");
            }
        });
        mYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text1.getText().equals("ABC")) {
                    text1.setText("");
                }

                text1.append("Y");
            }
        });
        mBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text1.getText().equals("ABC")) {
                    text1.setText("");
                }

                text1.append("B");
            }
        });
        mViolet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text1.getText().equals("ABC")) {
                    text1.setText("");
                }

                text1.append("V");
            }
        });
        // On "Already Registered? Login Here" click, switch to the login page
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
        // On "Register" click, store color code string in Firestore using UserID, then load main page
        mRegisterBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Level2Pin").setValue(text1.getText().toString()).
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(Register.this, "Registered", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Register.this,Login.class);
                                finish();
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}