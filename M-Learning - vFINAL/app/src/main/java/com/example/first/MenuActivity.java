package com.example.first;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;


public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final DrawerLayout drawerLayout=findViewById(R.id.drawerLayout);

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        final NavigationView navigationView=findViewById(R.id.navigationView);

        final NavController navController= Navigation.findNavController(this,R.id.navigationHostFragment);

        NavigationUI.setupWithNavController(navigationView,navController);

        final TextView textTitlu=findViewById(R.id.textTitlu);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                textTitlu.setText(destination.getLabel());

                //ALERT DIALOG LOGOUT
                navigationView.getMenu().findItem(R.id.logOff).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //daca clickul e facut pe "LogOff", atunci executa functia logoff ce contine alert dialog
                        logoff();
                        return true;
                    }
                });

                //ALERT DIALOG RATE US
                navigationView.getMenu().findItem(R.id.rate_us).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        withRatingBar(navigationView);
                        return true;
                    }
                });

            }
        });
    }



    //FUNCTIE DE LOGOUT PT ALERT DIALOG
    public void logoff() {
        new AlertDialog.Builder(MenuActivity.this)
                .setTitle("Delogare")
                .setMessage("Esti sigur ca vrei sa te deloghezi?")

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                })

                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    //FUNCTIE RATING ALERTDIALOG CUSTOM
    public void withRatingBar(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setTitle("Ofera o recenzie!");
        View dialogLayout = inflater.inflate(R.layout.layout_rating_alert_dialog, null);

        final RatingBar ratingBar = dialogLayout.findViewById(R.id.rate_us);
        builder.setView(dialogLayout);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //SHARED preferences
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefRating", MODE_PRIVATE);
                SharedPreferences.Editor myEditor = sharedPreferences.edit();
                //salvam numele userului si id-ul
                myEditor.putString("rating", String.valueOf(ratingBar.getRating()));
                myEditor.commit();

                Toast.makeText(getApplicationContext(), "Ne-ai oferit " + ratingBar.getRating(), Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
}