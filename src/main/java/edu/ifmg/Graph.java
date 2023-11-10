package edu.ifmg;

public class Graph {
    protected Lista<Airport> airports;

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
