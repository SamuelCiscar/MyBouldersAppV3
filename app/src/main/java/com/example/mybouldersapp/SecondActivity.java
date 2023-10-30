package com.example.mybouldersapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mybouldersapp.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {
    //Déclaration de la variable contenant les références des composants graphiques
    ActivitySecondBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Création de l'interface graphique
        binding = ActivitySecondBinding.inflate(getLayoutInflater());

        //Invalidate le menu pour forcer la barre d'action à redessiner le menu
        invalidateOptionsMenu();

        setContentView(R.layout.activity_second);

        //Affichage de l'interface graphique
        setContentView(binding.getRoot());




//        Un clic sur Valider, affiche le texte du RadioButton sélectionné dans l’EditText
        binding.btMyBlc.setOnClickListener(v ->{

            Intent intent = new Intent(this, BlocsActivity.class);

// Lance le workflow de changement d'écran
            startActivity(intent);

// Lance le workflow de destruction d'écran
            finish();
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0, "Météo");
        menu.add(0,2,0, "Map");
        menu.add(0,3,0, "Mes blocs");
        menu.add(0,4,0, "Mon profil");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 1){
            Intent intent = new Intent(this, WeatherActivity.class);

            startActivity(intent);

            finish();
        } else if (item.getItemId() == 2) {
            Intent intent = new Intent(this, MapActivity.class);

            startActivity(intent);

            finish();
        } else if (item.getItemId() == 3) {
            Intent intent = new Intent(this, BlocsActivity.class);

            startActivity(intent);

            finish();

        } else if (item.getItemId() == 4) {
            Intent intent = new Intent(this, ProfilActivity.class);

            startActivity(intent);

            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {

    }
}