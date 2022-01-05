package com.LogicBuild.collegekendra;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AttendanceProfessor extends AppCompatActivity {

    Button btnAttendanceList, btnSendOTP;
    EditText etOTP;
    String otp="";
    String Name,College,Branch;
    DatabaseReference otpRef, totAttendRef;
    String totAttendance="0";
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_professor);

        getSupportActionBar().setTitle("Attendance");
        Bundle bundle = getIntent().getExtras();
        Name = bundle.getString("Name");
        College = bundle.getString("College");
        Branch = bundle.getString("Branch");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Processing"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        otpRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://final-year-64353-default-rtdb.firebaseio.com/"
                + College + "/" + Branch + "/" + "otp");

        totAttendRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://final-year-64353-default-rtdb.firebaseio.com/"
                + College + "/" + Branch + "/" + "totAttendance");

        etOTP =findViewById(R.id.etOTPStud);

        btnAttendanceList =findViewById(R.id.btnAttendanceList);
        btnSendOTP =findViewById(R.id.btnSendOTPStud);

        totAttendRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                int totAtt = Integer.parseInt(snapshot.getValue(String.class));
                totAttendance = String.valueOf(totAtt+1);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });


        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etOTP.getText().toString()))
                {

                }
                else
                {

                    totAttendRef.setValue(totAttendance);

                    otp=etOTP.getText().toString();
                    otpRef.setValue(otp).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(AttendanceProfessor.this, "OTP Uploaded Succesfully", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        btnAttendanceList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AttendanceList.class);
                intent.putExtra("Name", Name);
                intent.putExtra("College", College);
                intent.putExtra("Branch", Branch);
                startActivity(intent);
            }
        });
    }
}