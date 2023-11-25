package edu.ifmg;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import edu.ifmg.structures.Lista;

public class Routes extends Graph {
    public int[][] adjacencyMatrix;
    public int numberOfAirports;

    public Routes(int numberOfAirports) {
        super(numberOfAirports);
        this.numberOfAirports = numberOfAirports;
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

        // Converter o array
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

    public int findAirportIndex(String abbreviation) {
        for (int i = 0; i < super.airports.size(); i++) {
            if (abbreviation.equals(super.airports.get(i).getAbbreviation())){
                return i;
            }
        }
        return -1;
    }

    public int calculateDistance(int source, int destination) {
        Airport sourceAirport = airports.get(source);
        Airport destinationAirport = airports.get(destination);
        int xDifference = sourceAirport.getxCoordinate() - destinationAirport.getxCoordinate();
        int yDifference = sourceAirport.getyCoordinate() - destinationAirport.getyCoordinate();
        return (int) Math.sqrt((xDifference * xDifference) + (yDifference * yDifference));
    }
    public void printAdjacencyMatrix() {
        System.out.print("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗\n");
        for (int[] matrix : adjacencyMatrix) {
            System.out.print("║");
            for (int i : matrix) {
                System.out.printf("%4d ║", i);
            }
            System.out.println();
        }
        System.out.print("╚═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝\n");
    }

    public void dfs(int airportIndex, boolean[] visited, Lista<Integer> path) {
        // Marcar o aeroporto como visitado
        visited[airportIndex] = true;
        path.add(airportIndex);

        // Visitar todos os aeroportos conectados a este aeroporto
        for (int i = 0; i < numberOfAirports; i++) {
            if (adjacencyMatrix[airportIndex][i] != 0 && !visited[i]) {
                dfs(i, visited, path);
                path.add(airportIndex);
            }
        }
    }
    public boolean hasAirport(String abbreviation) {
        return findAirportIndex(abbreviation) != -1;
    }


}
