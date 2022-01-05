package com.LogicBuild.collegekendra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_fund extends AppCompatActivity {
    EditText amount,reason,addamount;
    Button deduce,add;
    DatabaseReference databaseReference;
    String College,Branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fund);

        Bundle bundle = getIntent().getExtras();
        College = bundle.getString("College");
        Branch = bundle.getString("Branch");

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://final-year-64353-default-rtdb.firebaseio.com/"
                + College + "/" + Branch + "/" + "Passbook");


        amount= findViewById(R.id.editamount);
        reason= findViewById(R.id.editTextreason);
        deduce= findViewById(R.id.buttondeduce);
        add= findViewById(R.id.buttonadd);
        addamount= findViewById(R.id.editTextadd);

        deduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deduce();
                amount.setText("");
                reason.setText("");
                onBackPressed();

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
                addamount.setText("");
                onBackPressed();


            }
        });
    }
    private void deduce(){
        String amt=amount.getText().toString();
        String amtreason=reason.getText().toString().trim();

        if (!TextUtils.isEmpty(amtreason)){
            String id= databaseReference.push().getKey();
            databas databas=new databas(amt,amtreason,id);
            databaseReference.child(id).setValue(databas);
            Toast.makeText(this,"Amount deduced",Toast.LENGTH_SHORT).show();

        }
        else {

            Toast.makeText(this,"Should enter a reason to duduce money from class fund",Toast.LENGTH_LONG).show();
        }
    }
    public void add(){
        String amt=addamount.getText().toString();
        String amtreason = "Money added";
        if (!TextUtils.isEmpty(amt)){
            String id= databaseReference.push().getKey();
            databas databas=new databas(amt,amtreason,id);
            databaseReference.child(id).setValue(databas);
            Toast.makeText(this, "Amount Added", Toast.LENGTH_SHORT).show();
        }else {

            Toast.makeText(this,"Should enter a amount to add funds",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i =new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
