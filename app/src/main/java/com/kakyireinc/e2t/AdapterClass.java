package com.kakyireinc.e2t;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterClass extends ArrayAdapter {

    ArrayList<String> notesTitle;
    TextView titleText;


    //adapter constructor
    public AdapterClass(Context context, ArrayList<String> word) {
        super(context, R.layout.listview, word);

        this.notesTitle = word;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.listview, parent, false);

        //initializing view
        titleText = view.findViewById(R.id.listviewText);

        titleText.setTextColor(Color.BLUE);
        titleText.setText(String.valueOf(notesTitle.get(position)));


        return view;
    }
}
