package edu.ifmg;

import edu.ifmg.structures.Lista;

public class ScheduleGraph extends Graph {
    public Lista<Schedule>[][] adjacencyMatrix;

    public ScheduleGraph(int numberOfAirports, Lista<Airport> airports) {
        super(numberOfAirports);
        this.adjacencyMatrix = new Lista[numberOfAirports][numberOfAirports];
        this.airports = airports;

        for (int i = 0; i < numberOfAirports; i++) {
            for (int j = 0; j < numberOfAirports; j++) {
                this.adjacencyMatrix[i][j] = new Lista<Schedule>();
            }
        }
    }

    public void addEdge(int source, int destination, Schedule schedule) {
        if (source >= 0 && destination >= 0) {
            this.adjacencyMatrix[source][destination].add(schedule);
        } else {
            // Lida com a situação em que source ou destination é -1
            System.out.println("Aeroporto não encontrado: source=" + source + ", destination=" + destination);
        }
    }

    public void printAdjacencyMatrix() {
        System.out.print("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗\n");
        for (Lista<Schedule>[] matrix : adjacencyMatrix) {
            System.out.print("║");
            for (Lista<Schedule> schedules : matrix) {
                if (schedules.isEmpty()) {
                    // Se não houver voos, imprime 0 para indicar ausência de conexão
                    System.out.print(String.format("%4s ║", "0"));
                } else {
                    // Se houver voos, imprime a distância do primeiro voo formatada
                    System.out.print(String.format("%4d ║", schedules.get(0).getDuration_Time()));
                }
            }
            System.out.println(); // Move para a próxima linha após imprimir as informações para este aeroporto
        }
        System.out.print("╚═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝\n");
    }

}
