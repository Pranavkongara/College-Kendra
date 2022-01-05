package com.LogicBuild.collegekendra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Homescreen extends AppCompatActivity {
    private static final int ON_DO_NOT_DISTURB_CALLBACK_CODE = 0;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    String Name, Phone, Gender, College, Category, Branch;
    TextView testing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        testing = findViewById(R.id.testing);

        requestMutePermissions();


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://final-year-64353-default-rtdb.firebaseio.com/Users/" + firebaseUser.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Name = snapshot.child("Name").getValue().toString();
                Phone = snapshot.child("Phone").getValue().toString();
                Gender = snapshot.child("Gender").getValue().toString();
                College = snapshot.child("College").getValue().toString();
                Category = snapshot.child("Category").getValue().toString();
                Branch = snapshot.child("Branch").getValue().toString();
                testing.setText(Category);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void Calculator(View view) {
        startActivity(new Intent(getApplicationContext(), Calculator.class));
    }


    public void chat(View view) {
        Intent intent = new Intent(getApplicationContext(), Chat.class);
        intent.putExtra("Name", Name);
        intent.putExtra("College", College);
        intent.putExtra("Branch", Branch);
        startActivity(intent);
    }

    public void Notice_board(View view) {
        Intent intent = new Intent(getApplicationContext(), Noticeboard.class);
        intent.putExtra("Category", Category);
        intent.putExtra("College", College);
        intent.putExtra("Branch", Branch);
        startActivity(intent);
    }

    public void Anonymous_feedback(View view) {
        if (Category.equals("Student")) {
            Intent intent = new Intent(getApplicationContext(), AnonymousStudent.class);
            intent.putExtra("College", College);
            intent.putExtra("Branch", Branch);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), Anonymousfeedback.class);
            intent.putExtra("College", College);
            intent.putExtra("Branch", Branch);
            startActivity(intent);
        }
    }

    public void passbook(View view) {
        Intent intent = new Intent(getApplicationContext(), Passbook.class);
        intent.putExtra("Category", Category);
        intent.putExtra("College", College);
        intent.putExtra("Branch", Branch);
        startActivity(intent);
    }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }


    public void attendanceClicked(View view) {
//        Log.d("myTag",Category);
        if (Category.equals("Student")) {
            Intent intent = new Intent(getApplicationContext(), AttendanceStudent.class);
            intent.putExtra("Name", Name);
            intent.putExtra("College", College);
            intent.putExtra("Branch", Branch);
            startActivity(intent);

        } else if (Category.equals("Professor")) {
            Intent intent = new Intent(getApplicationContext(), AttendanceProfessor.class);
            intent.putExtra("Name", Name);
            intent.putExtra("College", College);
            intent.putExtra("Branch", Branch);
            startActivity(intent);
        }
    }


    private void requestMutePermissions() {
        try {
            if (Build.VERSION.SDK_INT < 23) {
                AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            } else if (Build.VERSION.SDK_INT >= 23) {
                this.requestForDoNotDisturbPermissionOrSetDoNotDisturbForApi23AndUp();
            }
        } catch (SecurityException e) {

        }
    }

    private void requestForDoNotDisturbPermissionOrSetDoNotDisturbForApi23AndUp() {

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        // if user granted access else ask for permission
        if (notificationManager.isNotificationPolicyAccessGranted()) {
            AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        } else {
            // Open Setting screen to ask for permisssion
            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivityForResult(intent, ON_DO_NOT_DISTURB_CALLBACK_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ON_DO_NOT_DISTURB_CALLBACK_CODE) {
            this.requestForDoNotDisturbPermissionOrSetDoNotDisturbForApi23AndUp();
        }
    }


    public void Distress_Alert(View view) {
        Toast.makeText(Homescreen.this,"Distress Alert Signal sent!!!!!",Toast.LENGTH_LONG).show();

    }


}