package edu.ifmg;

import edu.ifmg.structures.Lista;
import edu.ifmg.structures.ListaEncadeada;
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
        distance = calculateDistances(routes.findAirportIndex(origin_Airport), routes.findAirportIndex(destination_Airport), routes);
    }

    public static Lista<Schedule> fromJsonFile(String filePath, Routes routes) throws IOException {
        // Lê o conteúdo do arquivo JSON em uma string
        String json = new String(Files.readAllBytes(Paths.get(filePath)));

        // Converte a string JSON em um JSONArray
        JSONArray jsonArray = new JSONArray(json);

        // Cria uma lista de schedules
        Lista<Schedule> schedules = new Lista<>();

        // Loop para percorrer todos os objetos no JSONArray
        for (int i = 0; i < jsonArray.length(); i++) {
            // Obtém o objeto JSON atual
            JSONObject jsonSchedule = jsonArray.getJSONObject(i);

            // Cria um novo objeto Schedule a partir do objeto JSON
            Schedule schedule;
            schedule = new Schedule(
                    jsonSchedule.getString("Airline"),
                    jsonSchedule.getInt("Flight"),
                    jsonSchedule.getString("Origin_Airport"),
                    jsonSchedule.getString("Departure_Time"),
                    jsonSchedule.getString("Destination_Airport"),
                    jsonSchedule.getString("Arrival_Time"),
                    jsonSchedule.getInt("Stops_during_flight"),
                    routes
            );

            // Adiciona o objeto Schedule à lista
            schedules.add(schedule);
        }

        // Retorna a lista
        return schedules;
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
        // Obtém a letra (AM ou PM).
        char amPm = time.charAt(time.length() - 1);

        // Obtém o número removendo a letra da string.
        int numericValue = Integer.parseInt(time.substring(0, time.length() - 1));

        // Divide o número em horas e minutos.
        int hours = numericValue / 100; // Obtém as centenas como horas.
        int minutes = numericValue % 100; // Obtém os dois últimos dígitos como minutos.

        // Se o tempo terminar com 'P' e não for 12 PM, adicionamos 12 horas.
        if (amPm == 'P' && hours != 12) {
            hours += 12;
        }

        // Se o tempo terminar com 'A' e for 12 AM, definimos as horas como 0.
        if (amPm == 'A' && hours == 12) {
            hours = 0;
        }

        return hours * 60 + minutes;
    }

    public static int calculateDistances(int origin, int destination, Routes routes){
        // Cria um array para armazenar as distâncias dos aeroportos até a origem
        int[] distances = new int[routes.numberOfAirports];
        // Inicializa todas as distâncias como infinito
        for (int i = 0; i < routes.numberOfAirports; i++) {
            distances[i] = Integer.MAX_VALUE;
        }
        // A distância do aeroporto de origem até ele mesmo é 0
        distances[origin] = 0;

        // Cria uma lista encadeada para o BFS
        ListaEncadeada<Integer> lista = new ListaEncadeada<>();
        lista.add(origin);

        while (!lista.isEmpty()) {
            int currentAirport = lista.poll();

            // Para cada aeroporto conectado ao aeroporto atual
            for (int i = 0; i < routes.numberOfAirports; i++) {
                if (routes.adjacencyMatrix[currentAirport][i] != 0) {
                    // Se a distância atual até o aeroporto i for maior que a distância até o aeroporto atual mais a distância do aeroporto atual até o aeroporto i
                    if (distances[i] > distances[currentAirport] + routes.adjacencyMatrix[currentAirport][i]) {
                        // Atualiza a distância do aeroporto i
                        distances[i] = distances[currentAirport] + routes.adjacencyMatrix[currentAirport][i];
                        // Adiciona o aeroporto i à lista
                        lista.add(i);
                    }
                }
            }
        }

        // Retorna a distância até o aeroporto de destino
        return distances[destination];
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


