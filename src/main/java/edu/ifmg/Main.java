package edu.ifmg;
import edu.ifmg.structures.Lista;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static String capturaAeroporto(Routes routes){
        Scanner scan = new Scanner(System.in);
        String abreviattion;
        do {
            System.out.println("Digite o aeroporto que deseja: ");
            abreviattion = scan.next();
        } while (!routes.hasAirport(abreviattion));
        return abreviattion;
    }
    public static void main(String[] args){
        try {
            int escolha;
            String abreviattion;
            Scanner scan = new Scanner(System.in);
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
            //routes.printAdjacencyMatrix();
            //scheduleGraph.printAdjacencyMatrix();

            do {
                System.out.println("\n-------------------- MENU --------------------\n");
                System.out.println("""
                        Escolha o que deseja fazer:
                        1- Verificar caminho entre aeroportos
                        2- Verificar voos diretos saindo de um aeroporto
                        3- Verificar menor viagem entre aeroportos
                        4- Verificar grafo conexo e aeroportos críticos
                        5- Verificar rota que passa por todos os aeroportos
                        6- Sair""");
                System.out.print("----------------------------------------------\n");
                escolha = scan.nextInt();

                switch (escolha){
                    case 1:
                        //5.1
                        System.out.println("Primeiro aeroporto: ");
                        String aeroporto = capturaAeroporto(routes);
                        System.out.println("Segundo aeroporto: ");
                        String aeroporto2 = capturaAeroporto(routes);
                        Relatorys.BFS(routes.findAirportIndex(aeroporto), routes.findAirportIndex(aeroporto2), routes);
                        System.out.println();
                        break;
                    case 2:
                        //5.2
                        aeroporto = capturaAeroporto(routes);
                        Relatorys.showDirectFlightsWithoutConnections(scheduleGraph, routes.findAirportIndex(aeroporto));
                        break;
                    case 3:
                        System.out.println("Escolha o que deseja: ");
                        System.out.println("1- Menor viagem por duração\n" +
                                           "2- Menor viagem por distância");
                        int escolha1 = scan.nextInt();
                        if (escolha1 == 1){
                            System.out.println("Primeiro aeroporto: ");
                            aeroporto = capturaAeroporto(routes);
                            System.out.println("Segundo aeroporto: ");
                            aeroporto2 = capturaAeroporto(routes);
                            Relatorys.dijkstra(scheduleGraph, routes.findAirportIndex(aeroporto), routes.findAirportIndex(aeroporto2));
                        } else if (escolha1 == 2){
                            System.out.println("Primeiro aeroporto: ");
                            aeroporto = capturaAeroporto(routes);
                            System.out.println("Segundo aeroporto: ");
                            aeroporto2 = capturaAeroporto(routes);
                            Relatorys.dijkstraKM(scheduleGraph, routes.findAirportIndex(aeroporto), routes.findAirportIndex(aeroporto2));
                        } else {
                            System.out.println("Escolha não existe!");
                        }
                        break;
                    case 4:
                        //5.4
                        aeroporto = capturaAeroporto(routes);
                        boolean isConnected = Relatorys.isConnected(routes, routes.findAirportIndex(aeroporto));
                        Lista<Airport> criticalAirports = Relatorys.getCriticalAirports(routes, routes.findAirportIndex(aeroporto));

                        if (isConnected){
                            System.out.println("A partir desse aeroporto é possível atingir todos os outros!" );
                        } else {
                            System.out.println("A partir desse aeroporto não é possível atingir todos os outros!");
                        }

                        System.out.println("Aeroportos críticos: ");
                        for (int i = 0; i < criticalAirports.size(); i++) {
                            System.out.println(criticalAirports.get(i).getAbbreviation());
                        }
                        break;
                    case 5:
                        //5.5
                        aeroporto = capturaAeroporto(routes);
                        Relatorys.findRoute(routes, aeroporto);
                        break;
                }
            } while (escolha != 6);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}