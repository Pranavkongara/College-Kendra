package com.LogicBuild.collegekendra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Noticeboard extends AppCompatActivity {
    String Category,College,Branch;
    DatabaseReference databaseReference;

    ListView listView;
    List<notice> noticeDisplay;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticeboard);
        getSupportActionBar().setTitle("Notice Board");

        Bundle bundle = getIntent().getExtras();
        Category = bundle.getString("Category");
        College = bundle.getString("College");
        Branch = bundle.getString("Branch");
        listView = findViewById(R.id.noticeboardlist);
        noticeDisplay = new ArrayList<>();
        button = findViewById(R.id.buttonnotice);
        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://final-year-64353-default-rtdb.firebaseio.com/"
                + College + "/" + Branch + "/" + "NoticeBoard");

        if (Category.equals("Student"))
            button.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                noticeDisplay.clear();
                for(DataSnapshot artistSnaphot: dataSnapshot.getChildren())
                {
                    notice artist=artistSnaphot.getValue(notice.class);
                    noticeDisplay.add(artist);

                }
                noticelist adapter =new noticelist(Noticeboard.this,noticeDisplay);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void send_notice(View view){
        Intent intent=new Intent(getApplicationContext(),add_notice.class);
        intent.putExtra("College", College);
        intent.putExtra("Branch", Branch);
        startActivity(intent);

    }
}