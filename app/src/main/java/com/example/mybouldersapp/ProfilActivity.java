package com.example.mybouldersapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ProfilActivity extends AppCompatActivity {

        private Button btnLogOut;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profil);

            btnLogOut = findViewById(R.id.btnLogOut);
            btnLogOut.setOnClickListener(v -> onLogoutButtonClick());
        }

        private void onLogoutButtonClick() {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(ProfilActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ProfilActivity.this, MainActivity.class));
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0, "Accueil");
        menu.add(0,2,0, "Météo");
        menu.add(0,3,0, "Map");
        menu.add(0,3,0, "Mes Blocs");
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
            Intent intent = new Intent(this, BlocsActivity.class);

            startActivity(intent);

            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}