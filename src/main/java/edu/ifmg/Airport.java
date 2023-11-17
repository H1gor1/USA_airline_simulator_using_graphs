package edu.ifmg;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import edu.ifmg.structures.Lista;

public class Airport {
    @SerializedName("abbreviation")
    private String abbreviation;
    @SerializedName("time_zone_offset")
    private String timeZone;
    @SerializedName("x_coordinate")
    private int xCoordinate;
    @SerializedName("y_coordinate")
    private int yCoordinate;
    @SerializedName("city_airport_name")
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
        Airport[] airports = new Gson().fromJson(json, Airport[].class);

        // Adicionar os aeroportos à lista
        Lista<Airport> lista = new Lista<>();
        for (Airport airport : airports) {
            lista.add(airport);
        }

        return lista;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public String getCityAirport() {
        return cityAirport;
    }
}
