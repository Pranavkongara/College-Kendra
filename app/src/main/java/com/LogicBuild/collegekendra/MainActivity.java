package com.LogicBuild.collegekendra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    EditText eid, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eid = findViewById(R.id.emailid);
        password = findViewById(R.id.password);
        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Homescreen.class));
            finish();
        }
    }

    public void register(View view) {
        Intent i = new Intent(MainActivity.this, register1.class);
        startActivity(i);
    }

    public void login(View view) {
        String email, pass;
        email = eid.getText().toString().trim();
        pass = password.getText().toString().trim();
        fAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Login Successful ", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Homescreen.class));
                } else {
                    Toast.makeText(MainActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}