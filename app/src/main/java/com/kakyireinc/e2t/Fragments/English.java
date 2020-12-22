package com.kakyireinc.e2t.Fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kakyireinc.e2t.R;

/**
 * Created by Kakyire LastBorn on 3/2/2019.
 */

public class English  extends Fragment{

    ArrayAdapter arrayAdapter;



    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.english_fragment,container,false);

        listView=view.findViewById(R.id.listview);

        return view;
    }
}
