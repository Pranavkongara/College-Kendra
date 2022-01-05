package com.LogicBuild.collegekendra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Anonymousfeedback extends AppCompatActivity {
    String College,Branch;
    DatabaseReference databaseReference;

    ListView listView;
    List<notice> noticeDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymousfeedback);
        getSupportActionBar().setTitle("Anonymous Feedback");

        Bundle bundle = getIntent().getExtras();
        College = bundle.getString("College");
        Branch = bundle.getString("Branch");

        listView = findViewById(R.id.noticeboardlist);
        noticeDisplay = new ArrayList<>();

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://final-year-64353-default-rtdb.firebaseio.com/"
                + College + "/" + Branch + "/" + "AnonymousFeedback");
        Log.d("College",databaseReference.toString());
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
                    Log.d("College",artist.toString());
                }
                noticelist adapter =new noticelist(Anonymousfeedback.this,noticeDisplay);
                Log.d("College",adapter.toString());
                listView.setAdapter(adapter);
                Log.d("College",listView.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}