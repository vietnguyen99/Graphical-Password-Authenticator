package com.example.passwordauthenticator;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mLoginBtn;
    Button mLoginBtn2;
    TextView mCreateBtn;
    FirebaseAuth fAuth;
    Button mRed;
    Button mBlue;
    Button mGreen;
    Button mViolet;
    Button mOrange;
    Button mYellow;
    String colorCode;
    private FirebaseAuth mAuth;
    // UI references./
    private EditText mEmailView;
    private EditText mPasswordView;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mRed= findViewById(R.id.red2);
        mBlue= findViewById(R.id.blue2);
        mOrange= findViewById(R.id.orange2);
        mViolet= findViewById(R.id.violet2);
        mYellow= findViewById(R.id.yellow2);
        mGreen= findViewById(R.id.green2);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.textPassword);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.regButton);
        mLoginBtn2 = findViewById(R.id.login2);
        mCreateBtn = findViewById(R.id.loginText);
        final TextView text1 = (TextView)findViewById(R.id.text1);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Receive and enforce requirements for email and password entries
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

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

                // Authenticate user credentials

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // On successful authentication, make text password entry portion invisible and color entry portion visible
                            Toast.makeText(Login.this, "Textual Login Success!", Toast.LENGTH_SHORT).show();
                            mYellow.setVisibility(View.VISIBLE);
                            mRed.setVisibility(View.VISIBLE);
                            mBlue.setVisibility(View.VISIBLE);
                            mOrange.setVisibility(View.VISIBLE);
                            mViolet.setVisibility(View.VISIBLE);
                            mGreen.setVisibility(View.VISIBLE);
                            mLoginBtn.setVisibility(View.INVISIBLE);
                            mEmail.setVisibility(View.INVISIBLE);
                            mPassword.setVisibility(View.INVISIBLE);
                            mLoginBtn2.setVisibility(View.VISIBLE);
                            mCreateBtn.setVisibility(View.INVISIBLE);
                            text1.setVisibility(View.VISIBLE);

                        } else {
                            Toast.makeText(Login.this, "Error! You are not registered or have entered the wrong user credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        // On "New Here? Create Account" click, switch to registration page
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
        // Each button adds a color signifier to the string representing the proposed color entry code
        mOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text1.getText().equals("ABC"))
                {
                    text1.setText("");
                }
                text1.append("O");
            }
        });
        mGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text1.getText().equals("ABC"))
                {
                    text1.setText("");
                }
                text1.append("G");
            }
        });
        mRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text1.getText().equals("ABC"))
                {
                    text1.setText("");
                }
                text1.append("R");
            }
        });
        mYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text1.getText().equals("ABC"))
                {
                    text1.setText("");
                }
                text1.append("Y");
            }
        });
        mBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text1.getText().equals("ABC"))
                {
                    text1.setText("");
                }
                text1.append("B");
            }
        });
        mViolet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text1.getText().equals("ABC"))
                {
                    text1.setText("");
                }
                text1.append("V");
            }
        });


        // On "Login" click, load the main page
        mLoginBtn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot != null){
                                    Map<String , Object> map = (HashMap<String, Object>)dataSnapshot.getValue();
                                    if (map != null){
                                        if (text1.getText().toString().equals(map.get("colorSequence"))){
                                            Toast.makeText(Login.this, "You logged in", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        }
                                        else {
                                            Toast.makeText(Login.this, "Wrong Pin", Toast.LENGTH_SHORT).show();
                                            text1.setText("Color Input: ");
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
            }
        });
    }
}
