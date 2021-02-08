package com.example.first;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarChartActivity extends AppCompatActivity {
    List<Curs> cursuriFB = new ArrayList<>();
    LinearLayout layout;
    Map<String, Integer> source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = rootRef.child("proiect-dam-cursuri");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String titlu = ds.child("titlu").getValue(String.class);
                    String continut = ds.child("continut").getValue(String.class);
                    String tipCurs = ds.child("tipCurs").getValue(String.class);
                    Long userId = ds.child("userId").getValue(Long.class);

                    Curs c = new Curs(titlu, continut, tipCurs, userId);
                    cursuriFB.add(c);
                }

                source = getSource(cursuriFB);

                layout = findViewById(R.id.layoutBar);
                layout.addView(new BarChartView(getApplicationContext(), source));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        myRef.addListenerForSingleValueEvent(eventListener);

    }

    private Map<String, Integer> getSource(List<Curs> cursuri) {
        if(cursuri==null || cursuri.isEmpty())
            return new HashMap<>();
        else {
            Map<String, Integer> results = new HashMap<>();
            for(Curs curs : cursuri)
                if(results.containsKey(curs.getTipCurs()))
                    results.put(curs.getTipCurs(), results.get(curs.getTipCurs())+1);
                else
                    results.put(curs.getTipCurs(), 1);
            return results;
        }
    }
}
