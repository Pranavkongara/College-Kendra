package com.LogicBuild.collegekendra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AnonymousStudent extends AppCompatActivity {
    EditText textInput;
    Button btnSend;
    String College,Branch;

    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymous_student);
        Bundle bundle = getIntent().getExtras();
        College = bundle.getString("College");
        Branch = bundle.getString("Branch");

        textInput = findViewById(R.id.editTextInput);
        btnSend = findViewById(R.id.buttonSendInput);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://final-year-64353-default-rtdb.firebaseio.com/"
                + College + "/" + Branch + "/" + "AnonymousFeedback");

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = textInput.getText().toString().trim();
                textInput.setText("");


                if (message != null) {
                    String id = databaseReference.push().getKey();
                    notice a = new notice(message);
                    databaseReference.child(id).setValue(a);
                    Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_SHORT).show();

                }
            }
        });




    }
}