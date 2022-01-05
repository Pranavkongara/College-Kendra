package com.LogicBuild.collegekendra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Student_attendance extends AppCompatActivity {
    int present, total;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);

        getSupportActionBar().setTitle("Attendance");
        Bundle bundle = getIntent().getExtras();
        present = bundle.getInt("present");
        total = bundle.getInt("total");

        textView=findViewById(R.id.textView);

        textView.setText("Attendance : "+present+"/"+total+"\nPercentage : "+((float)present/(float)total)*100);
    }
}