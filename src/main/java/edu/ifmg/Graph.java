package edu.ifmg;

import edu.ifmg.structures.Lista;

public class Graph {
    public Lista<Airport> airports;

    public Graph(int numberOfAirports) {
        this.airports = new Lista<>();
    }
    public void addAirport(Airport airport) {
        this.airports.add(airport);
    }
    public Airport getAirport(int index) {
        return this.airports.get(index);
    }

}
