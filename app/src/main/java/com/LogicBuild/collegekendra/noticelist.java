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

public class noticelist extends ArrayAdapter<notice> {
    Activity context;
    List<notice> noticeList;

    public noticelist(Activity context,List<notice> noticeList){
        super(context,R.layout.noticeboardlistview,noticeList);
        this.context=context;
        this.noticeList=noticeList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.noticeboardlistview,null,true);
        TextView textviewname= listViewItem.findViewById(R.id.textViewname);

        notice notice=noticeList.get(position);
        textviewname.setText(notice.getMessage());

        return listViewItem;
    }

}
