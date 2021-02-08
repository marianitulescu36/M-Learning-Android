package com.example.first;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextEmail;
    EditText editTextConfirmare;
    Button button;
    private UseriDao useriDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername=findViewById(R.id.ed_usernameInregistrare);
        editTextPassword=findViewById(R.id.ed_passwordInregistrare);
        editTextEmail=findViewById(R.id.ed_emailInregistrare);
        editTextConfirmare=findViewById(R.id.ed_passwordConfirmation);
        button=(Button)findViewById(R.id.button_inregistrare);

        useriDao= Room.databaseBuilder(RegisterActivity.this,CursuriDB.class,"cursuri.db")
                .allowMainThreadQueries()
                .build()
                .getUseriDao();

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username=editTextUsername.getText().toString();
                String password=editTextPassword.getText().toString();
                String email=editTextEmail.getText().toString();
                String passwordConfirmation=editTextConfirmare.getText().toString();

                //verificam daca s-au introdus datele :
                if (editTextUsername.getText()==null || editTextUsername.getText().toString().isEmpty() || editTextUsername.getText().toString().trim().isEmpty())
                    editTextUsername.setError("Introduceti username!");

                if (editTextPassword.getText()==null || editTextPassword.getText().toString().isEmpty() || editTextPassword.getText().toString().trim().isEmpty())
                    editTextPassword.setError("Introduceti parola!");

                if (editTextConfirmare.getText()==null || editTextConfirmare.getText().toString().isEmpty() || editTextConfirmare.getText().toString().trim().isEmpty())
                    editTextConfirmare.setError("Introduceti parola!");

                if (editTextEmail.getText()==null || editTextEmail.getText().toString().isEmpty() || editTextEmail.getText().toString().trim().isEmpty())
                    editTextEmail.setError("Introduceti email-ul!");


                if(password.equals(passwordConfirmation)) {
                    User userNou = new User(username, password, email);
                    useriDao.insert(userNou);

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(RegisterActivity.this,"Parolele nu corespund",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}