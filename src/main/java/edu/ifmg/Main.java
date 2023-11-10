package edu.ifmg;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        try {
            Lista<Airport> airports = Airport.fromJsonFile("infoAirports.json");

            Routes routes = new Routes(airports.size());
            for (int i = 0; i < airports.size(); i++) {
                routes.addAirport(airports.get(i));
            }

            routes.addRoutesFromJsonFile("infoRoutes.json");

            System.out.println(routes.getDistance(0, 5));

            //routes.printAdjacencyMatrix();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}