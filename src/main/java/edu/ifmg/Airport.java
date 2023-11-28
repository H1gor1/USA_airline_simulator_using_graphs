package edu.ifmg;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import edu.ifmg.structures.Lista;

public class Airport {
    private String abbreviation;
    private String timeZone;
    private int xCoordinate;
    private int yCoordinate;
    private String cityAirport;

    public Airport(String abbreviation, String timeZone, int xCoordinate, int yCoordinate, String cityAirport) {
        this.abbreviation = abbreviation;
        this.timeZone = timeZone;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.cityAirport = cityAirport;
    }

    public static Lista<Airport> fromJsonFile(String filePath) throws IOException {
        // Ler o conteúdo do arquivo
        String json = new String(Files.readAllBytes(Paths.get(filePath)));

        // Converter o JSON para uma lista de aeroportos
        JSONArray airportsArray = new JSONArray(json);

        // Adicionar os aeroportos à lista
        Lista<Airport> lista = new Lista<>();
        for (int i = 0; i < airportsArray.length(); i++) {
            JSONObject airportObject = airportsArray.getJSONObject(i);
            Airport airport = new Airport(
                    airportObject.getString("abbreviation"),
                    airportObject.getString("time_zone_offset"),
                    airportObject.getInt("x_coordinate"),
                    airportObject.getInt("y_coordinate"),
                    airportObject.getString("city_airport_name")
            );
            lista.add(airport);
        }

        return lista;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }
}