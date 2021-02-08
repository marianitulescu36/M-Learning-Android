package com.example.first;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT = "com.example.application.example.EXTRA_TEXT";
    private Button button;
    UseriDao db;
    CursuriDB database;
    private EditText editText1;
    private EditText editText2;
    private TextView textViewRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         editText1=findViewById(R.id.ed_username);
         editText2=findViewById(R.id.ed_password);
         textViewRegister=findViewById(R.id.tv_noaccount);

         //database setup
        database = Room.databaseBuilder(this,CursuriDB.class,"cursuri.db")
                .allowMainThreadQueries()
                .build();
        db = database.getUseriDao();
        button = (Button) findViewById(R.id.btnlogin);

        //TEXTVIEW PENTRU REGISTER
        textViewRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

            //BUTONUL DE LOGIN
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //verificam daca s-au introdus datele :
                    if (editText1.getText()==null || editText1.getText().toString().isEmpty() || editText1.getText().toString().trim().isEmpty())
                        editText1.setError("Introduceți username!");
                    else if (editText2.getText()==null || editText2.getText().toString().isEmpty() || editText2.getText().toString().trim().isEmpty())
                        editText2.setError("Introduceți parola!");
                    else {
                        String username = editText1.getText().toString();
                        String password = editText2.getText().toString();
                        User user = db.getUser(username, password);

                        //niste date default ca sa intru in aplicatie fara register sau daca nu exista useri deja
                        String userAdmin = "admin";
                        String userAdminPass = "123";

                        if (user != null || (username.equals(userAdmin) && password.equals(userAdminPass))) {
                            //SHARED preferences
                            SharedPreferences sharedPreferences = getSharedPreferences("UserPref", MODE_PRIVATE);
                            SharedPreferences.Editor myEditor = sharedPreferences.edit();
                            //salvam numele userului si id-ul
                            myEditor.putString("loggedUsername", editText1.getText().toString());
                            myEditor.putInt("loggedId", user.getId());
                            myEditor.commit();

                            //daca e corect, deschidem meniul
                            openMenu();
                        } else {
                            Toast.makeText(MainActivity.this, "Userul nu există sau datele sunt greșite", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

    }

    //functie ce deschide activitatea meniu
    public void openMenu() {
        editText1=findViewById(R.id.ed_username);
        String name = "Bună, " + editText1.getText().toString();

        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra(EXTRA_TEXT, name);
        startActivity(intent);
    }
}