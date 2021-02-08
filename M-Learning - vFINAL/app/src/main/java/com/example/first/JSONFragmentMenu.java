package com.example.first;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class JSONFragmentMenu extends Fragment {
    private Intent intent;
    private Button btnJSONAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_jsonmenu, container, false);

        btnJSONAdd = (Button) view.findViewById(R.id.button_extrageJSON);

        btnJSONAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                //deschidem activitatea ce adauga un json
                intent = new Intent(getActivity(), AddJSON.class);
                startActivity(intent);
            }
        });

        return view;
    }
}