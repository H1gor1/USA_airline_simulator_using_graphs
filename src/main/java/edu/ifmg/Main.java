package edu.ifmg;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Lista<Airport> airports = Airport.fromJsonFile("infoAirports.json");
            // Agora você tem uma lista de aeroportos e pode fazer o que quiser com ela
            for (int i = 0; i < airports.size(); i++) {
                Airport airport = airports.get(i);
                System.out.println(airport.getAbbreviation());
            }
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao ler o arquivo JSON: " + e.getMessage());
        }
    }
}