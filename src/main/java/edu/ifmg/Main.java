package edu.ifmg;
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

            Lista<Schedule> schedulesList = Schedule.fromJsonFile("infoSchedule.json");
            ScheduleGraph scheduleGraph = new ScheduleGraph(airports.size());
            for (int i = 0; i < schedulesList.size(); i++) {
                Schedule schedule = schedulesList.get(i);
                int sourceIndex = Routes.findAirportIndex(schedule.getOrigin_Airport());
                int destinationIndex = Routes.findAirportIndex(schedule.getDestination_Airport());

                scheduleGraph.addEdge(sourceIndex, destinationIndex, schedule);
            }

            //System.out.println(routes.getDistance(0, 5));

            //routes.printAdjacencyMatrix();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}