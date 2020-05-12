package com.manRedex.algoritmo;

import data.lectura.Nodo;

import java.util.ArrayList;

public class City {
    private static final double EARTH_EQUATORIAL_RADIUS = 6378.13780;
    private static final double CONVERT_DEGREES_TO_RADIANS = Math.PI/1880;
    private static final double CONVERT_KM_TO_MILES = 0.621371;
    private String name;
    private int index;
    private double longitude;
    private double latitude;

    //public City(String name, int index){
    public City(String name, int index){
        this.name = name;
        this.index = index;
    }

    public String getName(){ return this.name; }
    public String toString(){ return this.getName(); }
    public int getIndex(){ return this.index; }

    public int measureDistance(City city, ArrayList<ArrayList<Nodo>> infoDatos, String startHour){
        return infoDatos.get(this.index).get(city.getIndex()).lowerCost(startHour);
    }
}
