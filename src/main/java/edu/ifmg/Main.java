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
                    scheduleGraph.addEdge(sourceIndex, destinationIndex, schedule);
            }

            //System.out.println(routes.getDistance(0, 5));
            //scheduleGraph.printAdjacencyMatrix();

            //5.1
            Relatorys.BFS(routes.findAirportIndex("MSY"), routes.findAirportIndex("BOS"), routes);
            System.out.println();
            //5.2
            Relatorys.showDirectFlightsWithoutConnections(scheduleGraph, routes.findAirportIndex("ATL"));

            //5.3
            /*for (int i = 0; i < airports.size(); i++) {
                for (int j = 0; j < airports.size(); j++) {
                    Relatorys.dijkstra(scheduleGraph,i,j);
                }
            }
            */
            Relatorys.dijkstra(scheduleGraph, routes.findAirportIndex("ATL"), routes.findAirportIndex("BNA"));
            Relatorys.dijkstraKM(scheduleGraph, routes.findAirportIndex("MSY"), routes.findAirportIndex("LAX"));

            //5.4
            boolean isConnected = Relatorys.isConnected(routes, routes.findAirportIndex("ABQ"));
            Lista<Airport> criticalAirports = Relatorys.getCriticalAirports(routes, routes.findAirportIndex("ABQ"));

            if (isConnected){
                System.out.println("A partir desse aeroporto é possível atingir todos os outros!" );
            } else {
                System.out.println("A partir desse aeroporto não é possível atingir todos os outros!");
            }

            System.out.println("Aeroportos críticos: ");
            for (int i = 0; i < criticalAirports.size(); i++) {
                System.out.println(criticalAirports.get(i).getAbbreviation());
            }



            //Relatorys.dijkstra(scheduleGraph, routes.findAirportIndex("MSY"), routes.findAirportIndex("LAX"));

            //routes.printAdjacencyMatrix();
            //scheduleGraph.printAdjacencyMatrix();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}