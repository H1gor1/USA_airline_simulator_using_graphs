package edu.ifmg;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import edu.ifmg.Airport;
import edu.ifmg.Lista;
public class Routes extends Graph {
    private int[][] adjacencyMatrix;

    public Routes(int numberOfAirports) {
        super(numberOfAirports);
        this.adjacencyMatrix = new int[numberOfAirports][numberOfAirports];
    }

    public void addRoute(int source, int destination, int distance) {
        this.adjacencyMatrix[source][destination] = distance;
        this.adjacencyMatrix[destination][source] = distance;
    }

    public int getDistance(int source, int destination) {
        return this.adjacencyMatrix[source][destination];
    }
    public static class Route {
        String source;
        String destination;
    }

    public void addRoutesFromJsonFile(String filePath) throws IOException {
        // Ler o conteúdo do arquivo
        String json = new String(Files.readAllBytes(Paths.get(filePath)));

        // Converter o JSON para uma lista de rotas
        Route[] routesArray = new Gson().fromJson(json, Route[].class);

        // Converter o array para a sua classe Lista
        Lista<Route> routes = new Lista<>();
        for (Route route : routesArray) {
            routes.add(route);
        }

        // Adicionar as rotas ao grafo
        for (int i = 0; i < routes.size(); i++) {
            Route route = routes.get(i);
            int sourceIndex = findAirportIndex(route.source);
            int destinationIndex = findAirportIndex(route.destination);
            if (sourceIndex != -1 && destinationIndex != -1) {
                addRoute(sourceIndex, destinationIndex, calculateDistance(sourceIndex, destinationIndex));
            }
        }

    }

    private int findAirportIndex(String abbreviation) {
        for (int i = 0; i < airports.size(); i++) {
            if (airports.get(i).getAbbreviation().equals(abbreviation)) {
                return i;
            }
        }
        return -1;
    }

    private int calculateDistance(int source, int destination) {
        Airport sourceAirport = airports.get(source);
        Airport destinationAirport = airports.get(destination);
        int xDifference = sourceAirport.getxCoordinate() - destinationAirport.getxCoordinate();
        int yDifference = sourceAirport.getyCoordinate() - destinationAirport.getyCoordinate();
        return (int) Math.sqrt((xDifference * xDifference) + (yDifference * yDifference));
    }
    public void printAdjacencyMatrix() {
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    public boolean hasAirport(String abbreviation) {
        return findAirportIndex(abbreviation) != -1;
    }
}
