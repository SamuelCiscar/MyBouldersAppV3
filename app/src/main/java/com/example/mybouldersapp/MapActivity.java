package com.example.mybouldersapp;


import android.os.Bundle;
import android.webkit.WebView;
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
        webView.loadUrl("https://kletterspots.de/climbing.html?z=6&lat=47.98256&lon=4.96582&background=osm");


    }
}
