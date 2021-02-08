package com.example.first;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AddJSON extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_j_s_o_n);

        listView = findViewById(R.id.listViewCursuriFinalizate);

        ExtractJSON extractCFinalizatJSON = new ExtractJSON(){

            @Override
            protected void onPostExecute(String s) {
                listaCursuriFinalizate.addAll(ExtractJSON.listaCursuriFinalizate);

                CursFinalizatAdapter adapter = new CursFinalizatAdapter(getApplicationContext(), R.layout.layout_cursuri_finalizate_listview,
                        listaCursuriFinalizate, getLayoutInflater()){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);

                        return view;
                    }
                };
                listView.setAdapter(adapter);
            }
        };
        try {
            extractCFinalizatJSON.execute(new URL("https://pastebin.com/raw/eBLzMLnw"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}