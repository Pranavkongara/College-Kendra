package com.LogicBuild.collegekendra;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.annotations.Nullable;

import java.util.List;

public class fundstatement extends ArrayAdapter<databas> {
    private final Activity context;
    private final List<databas> fundstatement;

    public fundstatement(@NonNull Activity context, List<databas> fundstatement) {
        super(context,R.layout.list_layout,fundstatement);
        this.context = context;
        this.fundstatement = fundstatement;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem= inflater.inflate(R.layout.list_layout,null,true);
        TextView amt= listViewItem.findViewById(R.id.textViewamt);
        TextView reason= listViewItem.findViewById(R.id.textViewreason);
        databas databas = fundstatement.get(position);
        amt.setText(databas.getAmounts());
        reason.setText(databas.getAmtreasons());
        return listViewItem;
    }
}