package com.example.first;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

public class ProfileFragment extends Fragment {

    TextView textView1;
    TextView textViewDelete;
    ImageView imageView;
    EditText editTextUser;
    EditText editTextPass;
    EditText editTextEmail;
    Button button;
    UseriDao useriDao;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onStart();

        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_profile, container, false);

        textView1=view.findViewById(R.id.tv_ratingUser);
        editTextUser=view.findViewById(R.id.ed_UpdateUser);
        editTextPass=view.findViewById(R.id.ed_updatePass);
        editTextEmail=view.findViewById(R.id.ed_updateEmail);
        imageView=view.findViewById(R.id.trenor_gif);
        button=view.findViewById(R.id.button_modificaDate);

        //luam datele userului logat prin shared pref
        SharedPreferences SH = getActivity().getSharedPreferences("UserPrefRating", Context.MODE_PRIVATE);
        String userRating = SH.getString("rating", "");
        final String userName = SH.getString("loggedUsername", "");
        String userEmail = SH.getString("loggedEmail", "");
        final String userPass = SH.getString("loggedPassword", "");
        final int userId = SH.getInt("loggedId", 0);

        //database setup
        useriDao= Room.databaseBuilder(getActivity(),CursuriDB.class,"cursuri.db")
                .allowMainThreadQueries()
                .build()
                .getUseriDao();

        //daca s-a acordat un rating, se afiseaza in tv + giful devine vizibil
        if (userRating != null && !userRating.isEmpty()) {
            imageView.setVisibility(View.VISIBLE);
            textView1.setText("Ati acordat aplicatiei " + userRating + " stele!");
        }

        //Modificare date user daca apesi pe buton
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String nameNou= editTextUser.getText().toString();
                String passNou=editTextPass.getText().toString();
                String emailNou=editTextEmail.getText().toString();

                CursuriDB db = CursuriDB.getInstance(view.getContext().getApplicationContext());
                db.getUseriDao().update(nameNou,passNou,emailNou);

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}