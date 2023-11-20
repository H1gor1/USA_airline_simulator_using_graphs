package edu.ifmg;

import edu.ifmg.structures.ListaEncadeada;
import edu.ifmg.structures.Lista;

public class Relatorys {
    //5.1. Para dois aeroportos pesquisados mostrar o caminho, como uma sequência de aeroportos com base no grafo das rotas;
    static void BFS(int s, int d, Routes routes) {
        if (s == d) {
            System.out.println("O vértice de origem é igual ao vértice de destino.");
            return;
        }

        // Marque todos os vértices como não visitados (por padrão, definido como falso)
        boolean[] visited = new boolean[routes.numberOfAirports];
        int[] parent = new int[routes.numberOfAirports];

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
            for (int n = 0; n < routes.numberOfAirports; n++) {
                if (routes.adjacencyMatrix[currentVertex][n] != 0 && !visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                    parent[n] = currentVertex;

                    // Se o destino for encontrado, pare a busca
                    if (n == d) {
                        printPath(parent, s, d, routes);
                        return;
                    }
                }
            }
        }

        System.out.println("Não existe caminho do vértice " + s + " para o vértice " + d);
    }

    static void printPath(int[] parent, int s, int d, Routes routes) {
        if (s == d) {
            System.out.print(routes.airports.get(s).getAbbreviation());
        } else {
            printPath(parent, s, parent[d], routes);
            System.out.print(" -> " + routes.airports.get(d).getAbbreviation());
        }
    }

    //5.2. Mostrar, a partir de um aeroporto definido, quais os voos diretos (sem escalas e/ou conexões) que partem dele e a lista desses destinos.

    static void showDirectFlightsWithoutConnections(ScheduleGraph scheduleGraph, int s) {
        System.out.println("Voos diretos a partir deste aeroporto:");

        for (int i = 0; i < scheduleGraph.airports.size(); i++) {
            for (int j = 0; j < scheduleGraph.adjacencyMatrix[s][i].size(); j++) {
                Schedule currentSchedule = scheduleGraph.adjacencyMatrix[s][i].get(j);

                if (currentSchedule.getDuration_Time() != 0 && currentSchedule.getStops_during_flight() == 0) {
                    System.out.print(currentSchedule.getDestination_Airport() + " ");
                }
            }
        }
        System.out.println();
    }


    public static void dijkstra(ScheduleGraph graph, int source, int destination) {
        int numberOfAirports = graph.airports.size();

        int[] distances = new int[numberOfAirports];
        boolean[] visited = new boolean[numberOfAirports];
        int[] previousAirport = new int[numberOfAirports];

        // Inicialização das distâncias, vértices não visitados e vértices anteriores
        for (int i = 0; i < numberOfAirports; i++) {
            distances[i] = Integer.MAX_VALUE;
            visited[i] = false;
            previousAirport[i] = -1; // Inicializa com -1 para indicar que ainda não foi visitado
        }

        distances[source] = 0;

        for (int i = 0; i < numberOfAirports - 1; i++) {
            int currentAirport = minDistance(distances, visited);

            visited[currentAirport] = true;

            for (int neighborAirport = 0; neighborAirport < numberOfAirports; neighborAirport++) {
                for (int j = 0; j < graph.adjacencyMatrix[currentAirport][neighborAirport].size(); j++) {
                    Schedule schedule = graph.adjacencyMatrix[currentAirport][neighborAirport].get(j);

                    int newDistance = distances[currentAirport] + schedule.getDuration_Time();

                    if (!visited[neighborAirport] && newDistance < distances[neighborAirport]) {
                        distances[neighborAirport] = newDistance;
                        previousAirport[neighborAirport] = currentAirport;
                    }
                }
            }
        }

        // A partir deste ponto, distances contém as menores distâncias da origem para todos os destinos
        // E previousAirport contém os vértices anteriores no caminho mais curto

        // Agora você pode usar as informações para determinar o caminho com menor custo em termos de tempo de voo
        int shortestDistance = distances[destination];
        System.out.println("Menor tempo de voo de " + graph.getAirport(source).getAbbreviation() +
                " para " + graph.getAirport(destination).getAbbreviation() + ": " + shortestDistance + " minutos");

        // Construir o caminho percorrendo os vértices anteriores
        Lista<Integer> path = new Lista<>();
        int currentAirport = destination;
        while (currentAirport != -1) {
            path.add(currentAirport);
            currentAirport = previousAirport[currentAirport];
        }

        // Inverter a lista para imprimir na ordem correta
        Lista<Integer> reversedPath = new Lista<>();
        for (int i = path.size() - 1; i >= 0; i--) {
            reversedPath.add(path.get(i));
        }

        // Imprimir o caminho
        System.out.println("Caminho:");
        for (int i = 0; i < reversedPath.size(); i++) {
            int airportIndex = reversedPath.get(i);
            System.out.println(" - Aeroporto: " + graph.getAirport(airportIndex).getAbbreviation());
            // Additional information about the flight, if needed
        }
    }

    private static int minDistance(int[] distances, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < distances.length; i++) {
            if (!visited[i] && distances[i] <= min) {
                min = distances[i];
                minIndex = i;
            }
        }

        return minIndex;
    }


}
