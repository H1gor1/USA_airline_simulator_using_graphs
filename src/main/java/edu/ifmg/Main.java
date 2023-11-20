package edu.ifmg;
import edu.ifmg.structures.Lista;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        try {
            Lista<Airport> airports = Airport.fromJsonFile("infoAirports.json");

            Routes routes = new Routes(airports.size());
            for (int i = 0; i < airports.size(); i++) {
                routes.addAirport(airports.get(i));
            }
            routes.addRoutesFromJsonFile("infoRoutes.json");

            Lista<Schedule> schedulesList = Schedule.fromJsonFile("infoSchedule.json", routes);
            ScheduleGraph scheduleGraph = new ScheduleGraph(airports.size(), airports);
            for (int i = 0; i < schedulesList.size(); i++) {
                Schedule schedule = schedulesList.get(i);
                int sourceIndex = routes.findAirportIndex(schedule.getOrigin_Airport());
                int destinationIndex = routes.findAirportIndex(schedule.getDestination_Airport());
                //if(routes.adjacencyMatrix[sourceIndex][destinationIndex] > 0){
                    scheduleGraph.addEdge(sourceIndex, destinationIndex, schedule);
                //}

            }

            //System.out.println(routes.getDistance(0, 5));
            //scheduleGraph.printAdjacencyMatrix();

            Relatorys.BFS(routes.findAirportIndex("ABQ"), routes.findAirportIndex("ATL"), routes);
            System.out.println();
            Relatorys.showDirectFlightsWithoutConnections(scheduleGraph, routes.findAirportIndex("ABQ"));
            Relatorys.dijkstra(scheduleGraph, routes.findAirportIndex("ABQ"), routes.findAirportIndex("ATL"));

            //routes.printAdjacencyMatrix();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}