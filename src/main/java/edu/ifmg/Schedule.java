package edu.ifmg;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Schedule {
    private String Airline;
    private int Flight;
    private String Origin_Airport;
    private String Departure_Time;
    private String Destination_Airport;
    private String Arrival_Time;
    private int Stops_during_flight;
    private int duration_Time;
    private int distance;

    public Schedule(String airline, int flight, String origin_Airport, String departure_Time, String destination_Airport, String arrival_Time, int stops_during_flight, Routes routes) {
        Airline = airline;
        Flight = flight;
        Origin_Airport = origin_Airport;
        Departure_Time = departure_Time;
        Destination_Airport = destination_Airport;
        Arrival_Time = arrival_Time;
        Stops_during_flight = stops_during_flight;
        duration_Time = calculateFlightDuration(departure_Time, arrival_Time);
        distance = routes.calculateDistance(routes.findAirportIndex(origin_Airport), routes.findAirportIndex(destination_Airport));
    }

    public static Lista<Schedule> fromJsonFile(String filePath) throws IOException {
        // Ler o conteúdo do arquivo
        String json = new String(Files.readAllBytes(Paths.get(filePath)));

        // Converter o JSON para uma lista de aeroportos
        Schedule[] schedules = new Gson().fromJson(json, Schedule[].class);

        // Adicionar os aeroportos à lista
        Lista<Schedule> lista = new Lista<>();
        for (Schedule schedule : schedules) {
            lista.add(schedule);
        }

        return lista;
    }

    public static int calculateFlightDuration(String departureTime, String arrivalTime) {
        int departureMinutes = convertTimeToMinutes(departureTime);
        int arrivalMinutes = convertTimeToMinutes(arrivalTime);

        // Se o tempo de chegada for menor que o tempo de partida, isso significa que o voo passou da meia-noite.
        if (arrivalMinutes < departureMinutes) {
            arrivalMinutes += 24 * 60;
        }

        return arrivalMinutes - departureMinutes;
    }

    public static int convertTimeToMinutes(String time) {
        int hours = Integer.parseInt(time.substring(0, time.length() - 4));
        int minutes = Integer.parseInt(time.substring(time.length() - 4, time.length() - 1));

        // Se o tempo terminar com 'P', isso significa PM, então adicionamos 12 horas.
        if (time.endsWith("P") && hours != 12) {
            hours += 12;
        }

        // Se o tempo terminar com 'A' e as horas forem 12, isso significa meia-noite, então definimos as horas como 0.
        if (time.endsWith("A") && hours == 12) {
            hours = 0;
        }

        return hours * 60 + minutes;
    }

    public String getAirline() {
        return Airline;
    }

    public int getFlight() {
        return Flight;
    }

    public String getOrigin_Airport() {
        return Origin_Airport;
    }

    public String getDeparture_Time() {
        return Departure_Time;
    }

    public String getDestination_Airport() {
        return Destination_Airport;
    }

    public String getArrival_Time() {
        return Arrival_Time;
    }

    public int getStops_during_flight() {
        return Stops_during_flight;
    }

    public int getDuration_Time() {
        return duration_Time;
    }

    public int getDistance() {
        return distance;
    }
}


