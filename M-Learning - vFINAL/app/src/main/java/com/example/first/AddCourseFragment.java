package com.example.first;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.Random;

public class AddCourseFragment extends Fragment {

    private FirebaseDatabase dateBase;

    public AddCourseFragment() {

    }

    private void salvareCursInFirebase(final Curs cv){
        dateBase= FirebaseDatabase.getInstance();
        final DatabaseReference myRef=dateBase.getReference("proiect-dam-cursuri");//imi creeaza nodul daca nu exista deja
        myRef.keepSynced(true);//sa am datele mereu sincronizate, in timp real se intampla

        myRef.child("proiect-dam-cursuri").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //facem insert
                cv.setUid(myRef.child("proiect-dam-cursuri").push().getKey());
                //mai atasez un nod copil
                myRef.child(cv.getUid()).setValue(cv);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_course, container, false);

        //luam valorile din edit texts din fragment
        final EditText etTitlu = view.findViewById(R.id.et_titluCurs);
        final EditText etContinut = view.findViewById(R.id.et_continutCurs);
        final RadioGroup radioGroup = view.findViewById(R.id.radioGroup);

        Bundle arguments = getArguments();

        if(arguments != null) {
            final TextView tv = view.findViewById(R.id.tv_adaugaCurs);
            tv.setText("Update curs");

            String t = getArguments().getString("titlu", "");
            String c = getArguments().getString("continut", "");
            String type = getArguments().getString("tip", "");

            etTitlu.setText(t);
            etContinut.setText(c);

            if (type.toUpperCase().equals("ÎNCEPĂTOR"))
                radioGroup.check(R.id.rb_incepator);
            else if (type.toUpperCase().equals("MEDIU"))
                radioGroup.check(R.id.rb_mediu);
            else
                radioGroup.check(R.id.rb_avansat);

            Button btnSave = view.findViewById(R.id.buttonSaveCurs);
            btnSave.setText("Update Curs");
            btnSave.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    String titlu=etTitlu.getText().toString();
                    String continut=etContinut.getText().toString();

                    RadioButton radioButton = view.findViewById(radioGroup.getCheckedRadioButtonId());
                    String tip = radioButton.getText().toString().toUpperCase();

                    int argid = Integer.parseInt(getArguments().getString("id", ""));

                    //updatam cursul in baza de date
                    CursuriDB db = CursuriDB.getInstance(view.getContext().getApplicationContext());
                    db.getCursuriDao().update(titlu, continut, tip, argid);

                    Toast.makeText(view.getContext().getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();

                    getActivity().onBackPressed();
                }
            });
        }
        else {
            Button btnSave = view.findViewById(R.id.buttonSaveCurs);
            btnSave.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (etTitlu.getText() == null || etTitlu.getText().toString().isEmpty() || etTitlu.getText().toString().trim().isEmpty())
                        etTitlu.setError("Introduceti titlul cursului!");
                    else if (etContinut.getText() == null || etContinut.getText().toString().isEmpty() || etContinut.getText().toString().trim().isEmpty())
                        etContinut.setError("Introduceti continutul cursului!");
                    else {
                        //creeam obiectul Curs
                        String titlu = etTitlu.getText().toString();
                        String continut = etContinut.getText().toString();

                        RadioButton radioButton = view.findViewById(radioGroup.getCheckedRadioButtonId());
                        String tip = radioButton.getText().toString().toUpperCase();

                        // Random random=new Random();
                        // User user1=new User("user1","456","user1@email.com");

                        //luam datele userului logat prin shared pref
                        SharedPreferences SH = getActivity().getSharedPreferences("UserPref", Context.MODE_PRIVATE);
                        int userId = SH.getInt("loggedId", 0);
                        String userName = SH.getString("loggedUsername", "");

                        Curs c = new Curs(titlu, continut, tip, userId);
                        c.setUserId(userId);
                        //mesaj in consola sa vedem frumos
                        System.out.println("CURSUL ADAUGAT:--------" + c.toString() + "~~~~~~ DE CATRE USERUL:--------" + Integer.toString(userId) + " " + userName);

                        //salvam cursul in baza de date
                        CursuriDB db = CursuriDB.getInstance(view.getContext().getApplicationContext());
                        //db.getUseriDao().insert(user1);
                        //System.out.println(db.getUseriDao().getAll());
                        //db.getCursuriDao().insert(c);

                        AsyncTask.execute(() -> db.getCursuriDao().insert(c));

                        salvareCursInFirebase(c);

                        Toast.makeText(view.getContext().getApplicationContext(), "Cursul a fost salvat!", Toast.LENGTH_LONG).show();

                        getActivity().onBackPressed();
                    }
                }
            });
        }

        return view;
    }
}