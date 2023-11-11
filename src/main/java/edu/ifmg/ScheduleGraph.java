package edu.ifmg;

public class ScheduleGraph extends Graph {
    private Lista<Schedule>[][] adjacencyMatrix;

    public ScheduleGraph(int numberOfAirports) {
        super(numberOfAirports);
        this.adjacencyMatrix = new Lista<Schedule>[numberOfAirports][numberOfAirports];
    }

}
