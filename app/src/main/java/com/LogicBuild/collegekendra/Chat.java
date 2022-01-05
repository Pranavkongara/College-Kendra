package com.LogicBuild.collegekendra;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Chat extends AppCompatActivity {
    Button sendBtn;
    EditText msgEt;
    ListView chatlistView;
    ArrayAdapter msgAdapter;
    String Name,College,Branch;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().setTitle("Chat Room");
        Bundle bundle = getIntent().getExtras();
        Name = bundle.getString("Name");
        College = bundle.getString("College");
        Branch = bundle.getString("Branch");

        databaseReference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://final-year-64353-default-rtdb.firebaseio.com/"
                + College + "/" + Branch + "/" + "ChatRoom");

        sendBtn = findViewById(R.id.sendBtn);
        msgEt = findViewById(R.id.msgEt);
        chatlistView = findViewById(R.id.chatlistview);

        ArrayList<String> msglist= new ArrayList<>();
        msgAdapter=new ArrayAdapter(this,R.layout.listitem,msglist);
        chatlistView.setAdapter(msgAdapter);

       sendBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String message=msgEt.getText().toString();
               if(message.length()>0)
               {
                   databaseReference.push().setValue(Name+" :"+message);

                   msgEt.setText("");
               }
           }
       });
       LoadMessages();
    }

    private void LoadMessages() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                msgAdapter.add(snapshot.getValue().toString());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}