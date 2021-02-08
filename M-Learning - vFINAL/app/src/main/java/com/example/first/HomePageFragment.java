package com.example.first;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class HomePageFragment extends Fragment {

    private Button button;

    public HomePageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        Intent intent = getActivity().getIntent();
        String name = intent.getStringExtra(MainActivity.EXTRA_TEXT);
        TextView textView = (TextView) view.findViewById(R.id.hello);
        textView.setText(name);

        button = (Button) view.findViewById(R.id.btnCursuri);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CursuriList.class);
                startActivity(intent);
            }
        });
        return view;
    }

}