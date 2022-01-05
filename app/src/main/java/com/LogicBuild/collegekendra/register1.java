package com.LogicBuild.collegekendra;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class register1 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String Gender, Category, CollegeName;
    EditText name, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        getSupportActionBar().setTitle("Registration");

        name = findViewById(R.id.Name);
        phone = findViewById(R.id.Phone);
        Spinner collegespinner = findViewById(R.id.College);

        ArrayAdapter<CharSequence> collegeadapter = ArrayAdapter.createFromResource(this,
                R.array.College_names, android.R.layout.simple_spinner_item);
        collegeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        collegespinner.setAdapter(collegeadapter);
        collegespinner.setOnItemSelectedListener(this);
    }

    public void Next(View view) {
        Intent intent = new Intent(getApplicationContext(), register2.class);
        intent.putExtra("Name", name.getText().toString());
        intent.putExtra("Phone", phone.getText().toString());
        intent.putExtra("Gender", Gender);
        intent.putExtra("College", CollegeName);
        intent.putExtra("Category", Category);
        startActivity(intent);
    }

    @SuppressLint("NonConstantResourceId")
    public void Gender(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.Male:
                if (checked)
                    Gender = "Male";
                Log.i("College", Gender);
                break;
            case R.id.Female:
                if (checked)
                    Gender = "Female";
                Log.i("College", Gender);
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void Category(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.Professor:
                if (checked)
                    Category = "Professor";
                Log.i("College", Category);
                break;
            case R.id.Student:
                if (checked)
                    Category = "Student";
                Log.i("College", Category);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CollegeName = parent.getSelectedItem().toString().trim();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}