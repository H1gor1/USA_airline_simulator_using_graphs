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
        this.adjacencyMatrix[source][destination].add(schedule);
    }


}
