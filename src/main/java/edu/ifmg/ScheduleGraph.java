package edu.ifmg;

public class ScheduleGraph extends Graph {
    private Lista<Schedule>[][] adjacencyMatrix;

    public ScheduleGraph(int numberOfAirports) {
        super(numberOfAirports);
        this.adjacencyMatrix = new Lista[numberOfAirports][numberOfAirports];

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
        for (Lista<Schedule>[] matrix : adjacencyMatrix) {
            for (Lista<Schedule> schedules : matrix) {
                if (schedules.isEmpty()) {
                    System.out.print("0 "); // Se não houver voos, imprime 0 para indicar ausência de conexão
                } else {
                    // Se houver voos, imprime a distância do primeiro voo
                    System.out.print(schedules.get(0).getDuration_Time() + " ");
                }
            }
            System.out.println(); // Move para a próxima linha após imprimir as informações para este aeroporto
        }
    }
}
