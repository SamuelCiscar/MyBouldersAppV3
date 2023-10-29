package com.example.mybouldersapp;

import com.example.mybouldersapp.beans.WeatherBean;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//utilitaire ou je mets mes requêtes
public class RequestUtils {

    private final static String URL_API_WEATHER =
            "https://api.openweathermap.org/data/2.5/weather?appid=b80967f0a6bd10d23e44848547b26550&units=metric&lang=fr&q=";

    public static WeatherBean loadWeather(String cityName) throws Exception {
        //Réaliser la requête.
        String json = sendGet(URL_API_WEATHER + cityName);

        //Parser le JSON avec le bon bean et GSON
        WeatherBean data = new Gson().fromJson(json, WeatherBean.class);

        //Retourner la donnée
        return data;
    }

    public static String sendGet(String url) throws Exception {
        System.out.println("url : " + url);
        OkHttpClient client = new OkHttpClient();

        //Création de la requête
        Request request = new Request.Builder().url(url).build();

        //Le try-with ressource doc ici
        //Nous permet de fermer la réponse en cas de succès ou d'échec (dans le finally)
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }
}