package edu.ifmg;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import edu.ifmg.Lista;

public class Airport {
    private String abbreviation;
    private String timeZone;
    private int xCoordinate;
    private int yCoordinate;
    private String cityAirport;

    public Airport(String abbreviation, String timeZone, String xCoordinate, String yCoordinate, String cityAirport) {
        this.abbreviation = abbreviation;
        this.timeZone = timeZone;
        this.xCoordinate = Integer.parseInt(xCoordinate.isEmpty() ? "0" : xCoordinate.trim());
        this.yCoordinate = Integer.parseInt(yCoordinate.isEmpty() ? "0" : yCoordinate.trim());
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
