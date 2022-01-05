package com.LogicBuild.collegekendra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AttendanceList extends AppCompatActivity {
    ListView l;
    String Name,College,Branch;
    String totAttendance ="1";
    ProgressDialog progressDialog;
    DatabaseReference attendanceRef, totAttendanceRef;
    ArrayList<AttendanceDetailsClass> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);

        getSupportActionBar().setTitle("Attendance List");
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

        l=findViewById(R.id.listView1);

        attendanceRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://final-year-64353-default-rtdb.firebaseio.com/"
                + College + "/" + Branch + "/" + "attendance");

        totAttendanceRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://final-year-64353-default-rtdb.firebaseio.com/"
                + College + "/" + Branch +"/" + "totAttendance");

        totAttendanceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totAttendance=snapshot.getValue(String.class);
                progressDialog.dismiss();
//                Log.d("myTag",totAttendance);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final BaseAdapter baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater lin=(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View row=lin.inflate(R.layout.each_row_attendance_details,parent,false);
                TextView tvName=row.findViewById(R.id.name1);
                TextView tvAttendance=row.findViewById(R.id.attendance1);
                AttendanceDetailsClass obj = list.get(position);
                tvName.setText("Name: "+obj.getName());
                tvAttendance.setText("Attendance: "+obj.getAttendance()+"/"+totAttendance);


                return row;
            }
        };


//        Log.d("myTag",attendanceRef.toString());


        attendanceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    AttendanceDetailsClass obj2=ds.getValue(AttendanceDetailsClass.class);
                    list.add(obj2);
                }
                baseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        l.setAdapter(baseAdapter);


    }
}