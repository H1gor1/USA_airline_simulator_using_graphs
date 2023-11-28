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
        ListaEncadeada<Integer> queue = new ListaEncadeada<>();

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

        // Loop para percorrer todos os aeroportos
        for (int i = 0; i < scheduleGraph.airports.size(); i++) {
            // Loop para percorrer todos os voos do aeroporto de origem 's' para o aeroporto 'i'
            for (int j = 0; j < scheduleGraph.adjacencyMatrix[s][i].size(); j++) {
                // Obtém o voo atual
                Schedule currentSchedule = scheduleGraph.adjacencyMatrix[s][i].get(j);

                // Se a duração do voo é diferente de zero e não há paradas durante o voo
                if (currentSchedule.getDuration_Time() != 0 && currentSchedule.getStops_during_flight() == 0) {
                    // Imprime o aeroporto de destino e a duração do voo
                    System.out.println(currentSchedule.getDestination_Airport() + " " + " Duração:" + currentSchedule.getDuration_Time());
                }
            }
        }
        System.out.println();
    }

    //5.3
    public static void dijkstra(ScheduleGraph graph, int source, int destination) {
        int numberOfAirports = graph.airports.size();

        // Inicializa as variáveis que serão usadas para armazenar informações sobre o grafo
        int[] duration = new int[numberOfAirports];  // Armazena a duração do voo de cada aeroporto
        int[] KMDistances = new int[numberOfAirports];  // Armazena a distância em KM de cada aeroporto
        boolean[] visited = new boolean[numberOfAirports];  // Armazena se um aeroporto já foi visitado
        int[] previousAirport = new int[numberOfAirports];  // Armazena o aeroporto anterior no caminho mais curto
        Schedule[] previousFlight = new Schedule[numberOfAirports];  // Armazena o voo anterior no caminho mais curto

        // Inicialização das distâncias, vértices não visitados e vértices anteriores
        for (int i = 0; i < numberOfAirports; i++) {
            duration[i] = Integer.MAX_VALUE;  // Inicializa a duração com um valor muito alto
            KMDistances[i] = Integer.MAX_VALUE;  // Inicializa a distância em KM com um valor muito alto
            visited[i] = false;  // Marca todos os aeroportos como não visitados
            previousAirport[i] = -1;  // Inicializa com -1 para indicar que ainda não foi visitado
        }

        // A duração e a distância do aeroporto de origem para ele mesmo são 0
        duration[source] = 0;
        KMDistances[source] = 0;

        // Loop para visitar todos os aeroportos
        for (int i = 0; i < numberOfAirports - 1; i++) {
            // Seleciona o aeroporto com a menor distância, dentre os não visitados
            int currentAirport = minDistance(duration, visited);

            // Marca o aeroporto selecionado como visitado
            visited[currentAirport] = true;

            // Atualiza a distância e a duração dos aeroportos vizinhos do aeroporto selecionado
            for (int neighborAirport = 0; neighborAirport < numberOfAirports; neighborAirport++) {
                for (int j = 0; j < graph.adjacencyMatrix[currentAirport][neighborAirport].size(); j++) {
                    Schedule schedule = graph.adjacencyMatrix[currentAirport][neighborAirport].get(j);

                    // Calcula a nova distância e duração considerando o voo atual
                    int newDistance = duration[currentAirport] + schedule.getDuration_Time();
                    int newKmDistance = KMDistances[currentAirport] + schedule.getDistance();

                    // Se a nova distância for menor, atualiza a distância, a duração, o aeroporto anterior e o voo anterior
                    if (!visited[neighborAirport] && newDistance < duration[neighborAirport]) {
                        duration[neighborAirport] = newDistance;
                        KMDistances[neighborAirport] = newKmDistance;
                        previousAirport[neighborAirport] = currentAirport;
                        previousFlight[neighborAirport] = schedule;
                    }
                }
            }
        }

        // A partir deste ponto, distances contém as menores distâncias da origem para todos os destinos
        // E previousAirport contém os vértices anteriores no caminho mais curto

        // Agora você pode usar as informações para determinar o caminho com menor custo em termos de tempo de voo
        int shortestDistance = duration[destination];
        int totalDistance = KMDistances[destination];
        System.out.println("Menor tempo de voo de " + graph.getAirport(source).getAbbreviation() +
                " para " + graph.getAirport(destination).getAbbreviation() + ": " + shortestDistance + " minutos | Distancia: " + totalDistance + " KM");

        // Construir o caminho percorrendo os vértices anteriores
        Lista<Integer> path = new Lista<>();
        int currentAirport = destination;
        while (currentAirport != -1) {
            path.add(currentAirport);
            if (previousAirport[currentAirport] == -1) {
                break;
            }
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
            if (i < reversedPath.size() - 1) {  // Não imprima o voo para o último aeroporto
                System.out.println(" - Voo: " + previousFlight[reversedPath.get(i+1)].getFlight());
            }
        }
    }

    public static void dijkstraKM(ScheduleGraph graph, int source, int destination) {
        int numberOfAirports = graph.airports.size();

        int[] duration = new int[numberOfAirports];
        int[] KMDistances = new int[numberOfAirports];
        boolean[] visited = new boolean[numberOfAirports];
        int[] previousAirport = new int[numberOfAirports];
        Schedule[] previousFlight = new Schedule[numberOfAirports];

        for (int i = 0; i < numberOfAirports; i++) {
            duration[i] = Integer.MAX_VALUE;
            KMDistances[i] = Integer.MAX_VALUE;
            visited[i] = false;
            previousAirport[i] = -1;
        }

        duration[source] = 0;
        KMDistances[source] = 0;

        for (int i = 0; i < numberOfAirports - 1; i++) {
            int currentAirport = minDistance(KMDistances, visited);

            visited[currentAirport] = true;

            for (int neighborAirport = 0; neighborAirport < numberOfAirports; neighborAirport++) {
                for (int j = 0; j < graph.adjacencyMatrix[currentAirport][neighborAirport].size(); j++) {
                    Schedule schedule = graph.adjacencyMatrix[currentAirport][neighborAirport].get(j);

                    int newDistance = duration[currentAirport] + schedule.getDuration_Time();
                    int newKmDistance = KMDistances[currentAirport] + schedule.getDistance();

                    if (!visited[neighborAirport] && newKmDistance < KMDistances[neighborAirport]) {
                        duration[neighborAirport] = newDistance;
                        KMDistances[neighborAirport] = newKmDistance;
                        previousAirport[neighborAirport] = currentAirport;
                        previousFlight[neighborAirport] = schedule;
                    }
                }
            }
        }

        int shortestDistance = KMDistances[destination];
        int totalDuration = duration[destination];
        System.out.println("Menor distancia de voo de " + graph.getAirport(source).getAbbreviation() +
                " para " + graph.getAirport(destination).getAbbreviation() + ": " + shortestDistance + " KM | Duração: " + totalDuration + " minutos");

        Lista<Integer> path = new Lista<>();
        int currentAirport = destination;
        while (currentAirport != -1) {
            path.add(currentAirport);
            if (previousAirport[currentAirport] == -1) {
                break;
            }
            currentAirport = previousAirport[currentAirport];
        }

        Lista<Integer> reversedPath = new Lista<>();
        for (int i = path.size() - 1; i >= 0; i--) {
            reversedPath.add(path.get(i));
        }

        System.out.println("Caminho:");
        for (int i = 0; i < reversedPath.size(); i++) {
            int airportIndex = reversedPath.get(i);
            System.out.println(" - Aeroporto: " + graph.getAirport(airportIndex).getAbbreviation());
            if (i < reversedPath.size() - 1) {
                System.out.println(" - Voo: " + previousFlight[reversedPath.get(i+1)].getFlight());
            }
        }
    }

    private static int minDistance(int[] distances, boolean[] visited) {
        // Inicializa o valor mínimo com um valor muito alto e o índice mínimo com -1
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        // Loop para percorrer todos os vértices
        for (int i = 0; i < distances.length; i++) {
            // Se o vértice não foi visitado e a distância é menor ou igual ao valor mínimo atual
            if (!visited[i] && distances[i] <= min) {
                // Atualiza o valor mínimo e o índice mínimo
                min = distances[i];
                minIndex = i;
            }
        }

        // Retorna o índice do vértice com a menor distância
        return minIndex;
    }
    
    // 5.4 Método para verificar se todos os aeroportos estão conectados
    public static boolean isConnected(Routes routes, int startAirportIndex) {
        // Cria um array para armazenar quais aeroportos foram visitados
        boolean[] visited = new boolean[routes.airports.size()];
        // Inicia a busca em profundidade a partir do primeiro aeroporto
        dfs(routes, startAirportIndex, visited);
        // Verifica se todos os aeroportos foram visitados
        for (boolean visit : visited) {
            if (!visit) {
                // Se algum aeroporto não foi visitado, retorna false
                return false;
            }
        }
        // Se todos os aeroportos foram visitados, retorna true
        return true;
    }

    // Método para realizar a busca em profundidade
    private static void dfs(Routes routes, int airportIndex, boolean[] visited) {
        // Marca o aeroporto atual como visitado
        visited[airportIndex] = true;
        // Percorre todos os aeroportos
        for (int i = 0; i < routes.airports.size(); i++) {
            // Se o aeroporto atual tem uma rota para o aeroporto i e o aeroporto i ainda não foi visitado
            if (routes.adjacencyMatrix[airportIndex][i] != 0 && !visited[i]) {
                // Continua a busca em profundidade a partir do aeroporto i
                dfs(routes, i, visited);
            }
        }
    }

    // Método para obter a lista de aeroportos críticos
    public static Lista<Airport> getCriticalAirports(Routes routes, int startAirportIndex) {
        // Cria uma lista para armazenar os aeroportos críticos
        Lista<Airport> criticalAirports = new Lista<>();
        // Percorre todos os aeroportos
        for (int i = 0; i < routes.airports.size(); i++) {
            if (i == startAirportIndex) {
                continue;
            }
            // Cria um array para armazenar quais aeroportos foram visitados
            boolean[] visited = new boolean[routes.airports.size()];
            // Inicia a busca em profundidade a partir do primeiro aeroporto que não seja o aeroporto i
            dfs(routes, startAirportIndex == 0 ? 1 : 0, visited, i);
            // Verifica se todos os aeroportos, exceto o aeroporto i, foram visitados
            for (int j = 0; j < routes.airports.size(); j++) {
                if (j != i && !visited[j]) {
                    // Se algum aeroporto não foi visitado, adiciona o aeroporto i à lista de aeroportos críticos
                    criticalAirports.add(routes.airports.get(i));
                    break;
                }
            }
        }
        // Retorna a lista de aeroportos críticos
        return criticalAirports;
    }

    // Método para realizar a busca em profundidade ignorando um aeroporto específico
    private static void dfs(Routes routes, int airportIndex, boolean[] visited, int skipIndex) {
        // Se o aeroporto atual é o aeroporto a ser ignorado, retorna imediatamente
        if (airportIndex == skipIndex) {
            return;
        }
        // Marca o aeroporto atual como visitado
        visited[airportIndex] = true;
        // Percorre todos os aeroportos
        for (int i = 0; i < routes.airports.size(); i++) {
            // Se o aeroporto atual tem uma rota para o aeroporto i, o aeroporto i não é o aeroporto a ser ignorado e o aeroporto i ainda não foi visitado
            if (i != skipIndex && routes.adjacencyMatrix[airportIndex][i] != 0 && !visited[i]) {
                // Continua a busca em profundidade a partir do aeroporto i
                dfs(routes, i, visited, skipIndex);
            }
        }
    }

    //5.5

    public static void findRoute(Routes routes, String startAirport) {
        // Encontra o índice do aeroporto de partida
        int startIndex = routes.findAirportIndex(startAirport);
        // Se o aeroporto de partida não for encontrado, imprime uma mensagem e retorna
        if (startIndex == -1) {
            System.out.println("Aeroporto não encontrado.");
            return;
        }

        // Inicializa o array de visitados e o caminho
        boolean[] visited = new boolean[routes.numberOfAirports];
        Lista<Integer> path = new Lista<>();

        // Realiza a busca em profundidade a partir do aeroporto de partida
        routes.dfs(startIndex, visited, path);

        // Verifica se todos os aeroportos foram visitados
        for (int i = 0; i < routes.numberOfAirports; i++) {
            if (!visited[i]) {
                System.out.println("Não foi possível visitar todos os aeroportos a partir do aeroporto selecionado.");
                return;
            }
        }

        // Imprime o caminho encontrado pela busca em profundidade
        System.out.println("O caminho encontrado pelo DFS é:");
        for (int i = 0; i < path.size() - 1; i++) {
            if(i % 10 == 0){
                System.out.println();
            }
            System.out.print(routes.airports.get(path.get(i)).getAbbreviation() + " -> ");
        }
        System.out.print(routes.airports.get(path.get(path.size() - 1)).getAbbreviation());

        // Verifica se a rota é hamiltoniana
        int[] arrayHamiltoniano = new int[routes.numberOfAirports];
        boolean isHamiltonian = true;

        for (int i = 0; i < path.size(); i++) {
            arrayHamiltoniano[path.get(i)]++;
            if (arrayHamiltoniano[path.get(i)] > 1) {
                isHamiltonian = false;
                break;
            }
        }

        if (isHamiltonian) {
            System.out.println("\nA rota é hamiltoniana!");
        } else {
            System.out.println("\nA rota não é hamiltoniana!");
        }
    }
}
