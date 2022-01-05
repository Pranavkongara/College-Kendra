package com.LogicBuild.collegekendra;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Calculator extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView binary, octal, decimal, hex;
    Spinner spinner;
    int num;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        getSupportActionBar().setTitle("Calculator");

        spinner = findViewById(R.id.spinner);
        binary = findViewById(R.id.binarytextview);
        octal = findViewById(R.id.octaltextview);
        decimal = findViewById(R.id.decimaltextview);
        hex = findViewById(R.id.hextextview);
        input = findViewById(R.id.inputnum);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Bit_con, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void convert(View view) {
        if (input.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Please enter a valid number", Toast.LENGTH_LONG).show();
        else {
            switch (num) {
                case 0:
                    Binary();
                    break;
                case 1:
                    Octal();
                    break;
                case 2:
                    Decimal();
                    break;
                case 3:
                    Hex();
                    break;
            }
        }
        closeKeyboard();
    }

    public void back(View view) {
        startActivity(new Intent(getApplicationContext(), Homescreen.class));
    }

    @SuppressLint("SetTextI18n")
    private void Binary() {
        String temp = input.getText().toString();
        int d = Integer.parseInt(temp, 2);
        String b = Integer.toBinaryString(d);
        String o = Integer.toOctalString(d);
        String h = Integer.toHexString(d);
        binary.setText("Binary : " + b);
        octal.setText("Octal : " + o);
        decimal.setText("Decimal : " + d);
        hex.setText("Hex : " + h.toUpperCase());
        input.setText("");
    }

    @SuppressLint("SetTextI18n")
    private void Octal() {
        String temp = input.getText().toString();
        int d = Integer.parseInt(temp, 8);
        String b = Integer.toBinaryString(d);
        String o = Integer.toOctalString(d);
        String h = Integer.toHexString(d);
        binary.setText("Binary : " + b);
        octal.setText("Octal : " + o);
        decimal.setText("Decimal : " + d);
        hex.setText("Hex : " + h.toUpperCase());
        input.setText("");
    }

    @SuppressLint("SetTextI18n")
    private void Decimal() {
        String temp = input.getText().toString();
        String b = Integer.toBinaryString(Integer.parseInt(temp));
        String o = Integer.toOctalString(Integer.parseInt(temp));
        String h = Integer.toHexString(Integer.parseInt(temp));
        binary.setText("Binary : " + b);
        octal.setText("Octal : " + o);
        decimal.setText("Decimal : " + temp);
        hex.setText("Hex : " + h.toUpperCase());
        input.setText("");
    }

    @SuppressLint("SetTextI18n")
    private void Hex() {
        String temp = input.getText().toString();
        int d = Integer.parseInt(temp, 16);
        String b = Integer.toBinaryString(d);
        String o = Integer.toOctalString(d);
        String h = Integer.toHexString(d);
        binary.setText("Binary : " + b);
        octal.setText("Octal : " + o);
        decimal.setText("Decimal : " + d);
        hex.setText("Hex : " + h.toUpperCase());
        input.setText("");
    }

    private void closeKeyboard() {
        // this will give us the view
        // which is currently focus
        // in this layout
        View view = this.getCurrentFocus();

        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {

            // now assign the system
            // service to InputMethodManager
            InputMethodManager manager
                    = (InputMethodManager)
                    getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i("College", "Item selected : " + parent.getSelectedItem());
        num = parent.getSelectedItemPosition();
        switch (num) {
            case 0:
                input.setKeyListener(DigitsKeyListener.getInstance("01"));
                input.setHint("Enter Binary Number");
                break;
            case 1:
                input.setKeyListener(DigitsKeyListener.getInstance("01234567"));
                input.setHint("Enter Octal Number");
                break;
            case 2:
                input.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
                input.setHint("Enter Decimal Number");
                break;
            case 3:
                input.setKeyListener(DigitsKeyListener.getInstance("0123456789abcdefABCDEF"));
                input.setHint("Enter Hex Number");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}