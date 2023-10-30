package com.example.mybouldersapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class BlocsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocs);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0, "Accueil");
        menu.add(0,2,0, "Météo");
        menu.add(0,3,0, "Map");
        menu.add(0,4,0, "Mon Profil");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 1){
            Intent intent = new Intent(this, SecondActivity.class);

            startActivity(intent);

            finish();
        } else if (item.getItemId() == 2) {
            Intent intent = new Intent(this, WeatherActivity.class);

            startActivity(intent);

            finish();
        } else if (item.getItemId() == 3) {
            Intent intent = new Intent(this, MapActivity.class);

            startActivity(intent);

            finish();

        } else if (item.getItemId() == 4) {
            Intent intent = new Intent(this, ProfilActivity.class);

            startActivity(intent);

            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}