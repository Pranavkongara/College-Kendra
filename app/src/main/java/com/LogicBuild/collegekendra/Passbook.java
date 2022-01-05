package com.LogicBuild.collegekendra;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Passbook extends AppCompatActivity {
    String Category,College,Branch;

    ListView listfund;
    DatabaseReference databaseReference;
    List<databas> statements;
    TextView textViewbalance;
    Integer add=0,deduse=0;
    Integer result;
    Button button;

    public Integer getResult() {
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passbook);
        statements = new ArrayList<>();
        getSupportActionBar().setTitle("Passbook");

        Bundle bundle = getIntent().getExtras();
        Category = bundle.getString("Category");
        College = bundle.getString("College");
        Branch = bundle.getString("Branch");


        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://final-year-64353-default-rtdb.firebaseio.com/"
                + College + "/" + Branch + "/" + "Passbook");

        listfund = findViewById(R.id.listviewfund);

        button = findViewById(R.id.button);

        if (Category.equals("Student"))
            button.setVisibility(View.GONE);

        textViewbalance = findViewById(R.id.textViewbalance);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                statements.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    databas databas = snapshot.getValue(databas.class);

                    String amount1= databas.getAmounts();

                    String reason1=databas.getAmtreasons();
                    if(reason1.equals("Money added"))
                    {
                        add=add+Integer.parseInt(amount1);

                    }
                    else
                    {
                        deduse+=Integer.parseInt(databas.getAmounts());
                    }

                    statements.add(databas);
                }
                fundstatement adaptor = new fundstatement(Passbook.this, statements);
                listfund.setAdapter(adaptor);
                result = add-deduse;
                textViewbalance.setText("Balance = "+result.toString());
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
    }
    public void add_fund(View view) {
        Intent intent = new Intent(getApplicationContext(),add_fund.class);
        intent.putExtra("College", College);
        intent.putExtra("Branch", Branch);
        startActivity(intent);
    }
}