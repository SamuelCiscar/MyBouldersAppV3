package com.example.mybouldersapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mybouldersapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Déclaration de la variable contenant les références des composants graphiques
    ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Création de l'interface graphique
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(R.layout.activity_main);

        //Affichage de l'interface graphique
        setContentView(binding.getRoot());



//        Un clic sur Valider, affiche le texte du RadioButton sélectionné dans l’EditText
        binding.btnConnect.setOnClickListener(v ->{

            Intent intent = new Intent(this, SecondActivity.class);

// Lance le workflow de changement d'écran
            startActivity(intent);

// Lance le workflow de destruction d'écran
            finish();
        });
    }



    @Override
    public void onClick(View v) {

    }
}