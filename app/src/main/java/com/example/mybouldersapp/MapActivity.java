package com.example.mybouldersapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Initialise le WebView
        WebView  webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        // Charge la carte HTML
        webView.loadUrl("https://mapcomplete.org/climbing?z=4.9&lat=46.840311283471635&lon=3.574799358861128&layer-shops_with_climbing_shoe_repair=false&layer-shops=false&layer-toilet=false&layer-drinking_water=false&layer-guidepost=false");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0, "Accueil");
        menu.add(0,2,0, "Météo");
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
            Intent intent = new Intent(this, WeatherActivity.class);

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
