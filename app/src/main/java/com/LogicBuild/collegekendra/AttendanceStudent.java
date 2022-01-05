package com.LogicBuild.collegekendra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AttendanceStudent extends AppCompatActivity {

    EditText etOTP;
    Button btnSend;
    ProgressDialog progressDialog;
    String attendanceCount = "1";
    String otp = "";
    boolean b1 = false, b2 = false;
    DatabaseReference otpRef, attendanceRef;
    String Name, College, Branch;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    int present = 1, total = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_student);

        getSupportActionBar().setTitle("Attendance");
        Bundle bundle = getIntent().getExtras();
        Name = bundle.getString("Name");
        College = bundle.getString("College");
        Branch = bundle.getString("Branch");

//        Toast.makeText(this, ""+user.getDisplayName(), Toast.LENGTH_SHORT).show();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Processing"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        otpRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://final-year-64353-default-rtdb.firebaseio.com/"
                + College + "/" + Branch + "/" + "otp");

        attendanceRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://final-year-64353-default-rtdb.firebaseio.com/"
                + College + "/" + Branch + "/" + "attendance" + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid());


        otpRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                otp = snapshot.getValue(String.class);
//                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        attendanceRef.child("attendance").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int c = Integer.parseInt(snapshot.getValue(String.class));
                    attendanceCount = String.valueOf(c + 1);

                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


        etOTP = findViewById(R.id.etOTPStud);
        btnSend = findViewById(R.id.btnSendOTPStud);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etOTP.getText().toString().equals(otp)) {
                    AttendanceDetailsClass obj = new AttendanceDetailsClass(Name, attendanceCount);
                    attendanceRef.setValue(obj);
                }
            }
        });
    }

    public void showAttendanceClicked(View view) {
        DatabaseReference totAttRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://final-year-64353-default-rtdb.firebaseio.com/"
                + College + "/" + Branch + "/" + "totAttendance");

        totAttRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                total = Integer.parseInt(snapshot.getValue(String.class));
                b1 = true;
                Log.d("College",total+"");
                if (b2) {
                    Intent i = new Intent(AttendanceStudent.this, Student_attendance.class);
                    i.putExtra("present", present);
                    i.putExtra("total", total);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        attendanceRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://final-year-64353-default-rtdb.firebaseio.com/"
                + College + "/" + Branch + "/" + "attendance" + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid()+"/attendance");
        attendanceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("College",attendanceRef.toString());
                present = Integer.parseInt(snapshot.getValue(String.class));
                Log.d("College",present+" -");
//                Toast.makeText(AttendanceStudent.this, present+"/", Toast.LENGTH_SHORT).show();
                b2 = true;
                if (b1) {
                    Intent i = new Intent(AttendanceStudent.this, Student_attendance.class);
                    i.putExtra("present", present);
                    i.putExtra("total", total);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}