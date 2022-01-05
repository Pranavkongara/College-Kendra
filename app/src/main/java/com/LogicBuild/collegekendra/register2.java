package com.LogicBuild.collegekendra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String Name, Phone, Gender, College, Category, Branch;
    EditText rollno, email, password, repassword;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        Bundle bundle = getIntent().getExtras();
        getSupportActionBar().setTitle("Registration");

        Name = bundle.getString("Name");
        Phone = bundle.getString("Phone");
        Gender = bundle.getString("Gender");
        College = bundle.getString("College");
        Category = bundle.getString("Category");

        rollno = findViewById(R.id.rollno);
        email = findViewById(R.id.email);
        password = findViewById(R.id.Password);
        repassword = findViewById(R.id.confirm);
        Spinner branchspinner = findViewById(R.id.Branch);
        ArrayAdapter<CharSequence> branchadapter = ArrayAdapter.createFromResource(this,
                R.array.Branches, android.R.layout.simple_spinner_item);
        branchadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchspinner.setAdapter(branchadapter);
        branchspinner.setOnItemSelectedListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void Register(View view) {
        if(Category.equals("Professor") && !rollno.getText().toString().equals("7260")){
            Toast.makeText(register2.this,"Invalid Key for Professor registration!! Please contact ADMIN",Toast.LENGTH_LONG).show();
        }
        else {
            firebaseAuth.createUserWithEmailAndPassword(email.getText().toString().trim(),
                    password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(register2.this, "User Created", Toast.LENGTH_LONG).show();
                        SetDetails();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(register2.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void SetDetails() {
        DatabaseReference main, sub;
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        main = FirebaseDatabase.getInstance().getReferenceFromUrl
                ("https://final-year-64353-default-rtdb.firebaseio.com/Users/" + firebaseUser.getUid());
        sub = main.child("Name");
        sub.setValue(Name);
        sub = main.child("Phone");
        sub.setValue(Phone);
        sub = main.child("Category");
        sub.setValue(Category);
        sub = main.child("College");
        sub.setValue(College);
        sub = main.child("Gender");
        sub.setValue(Gender);
        sub = main.child("Branch");
        sub.setValue(Branch);
        sub = main.child("Rollno");
        sub.setValue(rollno.getText().toString());
        sub = main.child("Email");
        sub.setValue(email.getText().toString());
        main = FirebaseDatabase.getInstance().getReferenceFromUrl("https://final-year-64353-default-rtdb.firebaseio.com/"
                + College + "/" + Branch + "/" +"Users/"+ firebaseUser.getUid());
        main.setValue(true);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Branch = parent.getSelectedItem().toString().trim();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
