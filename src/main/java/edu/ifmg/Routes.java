package edu.ifmg;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import edu.ifmg.structures.Lista;
import edu.ifmg.structures.ListaEncadeada;

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
        for (int[] matrix : adjacencyMatrix) {
            for (int i : matrix) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
    public boolean hasAirport(String abbreviation) {
        return findAirportIndex(abbreviation) != -1;
    }


    void BFS(int s, int d) {
        if (s == d) {
            System.out.println("O vértice de origem é igual ao vértice de destino.");
            return;
        }

        // Marque todos os vértices como não visitados (por padrão, definido como falso)
        boolean[] visited = new boolean[numberOfAirports];
        int[] parent = new int[numberOfAirports];

        // Crie uma fila para BFS
        ListaEncadeada<Integer> queue = new ListaEncadeada<Integer>();

        // Marque o nó atual como visitado e coloque-o na fila
        visited[s] = true;
        queue.add(s);
        parent[s] = -1;  // O vértice de origem não tem pai

        while (!queue.isEmpty()) {
            // Desenfileire um vértice da fila
            int currentVertex = queue.poll();

            // Obtenha todos os vértices adjacentes ao vértice desenfileirado currentVertex.
            // Se um adjacente não foi visitado, marque-o como visitado e enfileire-o
            for (int n = 0; n < numberOfAirports; n++) {
                if (adjacencyMatrix[currentVertex][n] != 0 && !visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                    parent[n] = currentVertex;

                    // Se o destino for encontrado, pare a busca
                    if (n == d) {
                        printPath(parent, s, d);
                        return;
                    }
                }
            }
        }

        System.out.println("Não existe caminho do vértice " + s + " para o vértice " + d);
    }

    void printPath(int[] parent, int s, int d) {
        if (s == d) {
            System.out.print(airports.get(s).getAbbreviation());
        } else {
            printPath(parent, s, parent[d]);
            System.out.print(" -> " + airports.get(d).getAbbreviation());
        }
    }

}
