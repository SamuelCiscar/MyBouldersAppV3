package com.example.mybouldersapp.beans;

import java.util.ArrayList;

public class WeatherBean {

    private ArrayList<DescriptionBean> weather;
    private TempBean main;
    private WindBean wind;
    private String name;

    public ArrayList<DescriptionBean> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<DescriptionBean> weather) {
        this.weather = weather;
    }

    public TempBean getMain() {
        return main;
    }

    public void setMain(TempBean main) {
        this.main = main;
    }

    public WindBean getWind() {
        return wind;
    }

    public void setWind(WindBean wind) {
        this.wind = wind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}