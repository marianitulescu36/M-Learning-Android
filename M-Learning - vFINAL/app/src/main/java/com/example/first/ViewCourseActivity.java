package com.example.first;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ViewCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);

        TextView etTitlu = findViewById(R.id.tv_titlu);
        TextView etTip = findViewById(R.id.tv_tip);
        TextView etContinut = findViewById(R.id.tv_continut);

        Intent i = getIntent();
        Curs curs = (Curs) i.getSerializableExtra("curs");

        etTitlu.setText(etTitlu.getText() + curs.getTitlu());
        etTip.setText(etTip.getText() + curs.getTipCurs());
        etContinut.setText(etContinut.getText() + "\n \n" + curs.getContinut());
    }
}