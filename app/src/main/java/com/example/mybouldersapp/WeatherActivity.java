package com.example.mybouldersapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mybouldersapp.beans.WeatherBean;
import com.example.mybouldersapp.databinding.ActivityWeatherBinding;
import com.squareup.picasso.Picasso;//Activity = Controler
public class WeatherActivity extends AppCompatActivity {
    //IHM
    ActivityWeatherBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //instancier
        binding = ActivityWeatherBinding.inflate(getLayoutInflater());
        //Affichage
        setContentView(binding.getRoot());
        //Clic sur le bouton
        binding.btLoad.setOnClickListener(v -> {
            //Afficher la progressBar
            binding.progressBar.setVisibility(View.VISIBLE);
            String city = binding.etCityName.getText().toString();
            //Création d'un Thread
            new Thread(() -> {
                //Appel d'API
                WeatherBean data;
                try {
                    data = RequestUtils.loadWeather(city);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                //Mise à jour graphique
                runOnUiThread(() -> {
                    binding.tvDesc.setText("-");
                    binding.ivTemp.setImageBitmap(null);
                    //Mise à jour des composants graphiques
                    //Version avec donnée
                    binding.tv.setText(data.getName());
                    binding.tvTemp.setText(data.getMain().getTemp() + "");
                    binding.tvMinMax.setText("(" + data.getMain().getTemp_min() + "°/ " + data.getMain().getTemp_max() + "°)");
                    binding.tvWind.setText(data.getWind().getSpeed() + "");

                    //C'est une collection
                    if(!data.getWeather().isEmpty()) {
                        binding.tvDesc.setText(data.getWeather().get(0).getDescription());
                        Picasso.get().load("https://openweathermap.org/img/wn/" + data.getWeather().get(0).getIcon() + "@4x.png").into(binding.ivTemp);
                    }
                    else {
                        binding.tvDesc.setText("-");
                    }
                    //Masque la progressBar
                    binding.progressBar.setVisibility(View.GONE);
                });
            }).start();
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0, "Accueil");
        menu.add(0,2,0, "Map");
        menu.add(0,3,0, "Mes blocs");
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
}