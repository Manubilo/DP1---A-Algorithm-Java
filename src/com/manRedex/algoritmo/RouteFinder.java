package com.manRedex.algoritmo;

import data.lectura.Nodo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

public class RouteFinder<T extends GraphNode> {
    private final Graph<T> graph;
    private final Scorer<T> nextNodeScorer;
    private final Scorer<T> targetScorer;


    private static ArrayList<String> readAirports() throws IOException {
        ArrayList<String> airports = new ArrayList<String>();
        String csvAirports = "D:/DP1---A-Algorithm-Java/src/com/manRedex/algoritmo/nombres_aereopuerto.csv";
        BufferedReader csvReader = new BufferedReader(new FileReader(csvAirports));
        String row;
        while((row = csvReader.readLine()) != null){
            String[] data = row.split(",");
            airports.add(data[0]);
        }
        csvReader.close();
        return airports;
    }

    public static int searchIndex(String airportCode, ArrayList<String> airports){
        for (int i = 0; i < airports.size(); i++) {
            if(airportCode.equals(airports.get(i))) return i;
        }
        return -1;
    }

    public static ArrayList<ArrayList<Nodo>> readNodes(ArrayList<String> airports) throws IOException {
        ArrayList<ArrayList<Nodo>> matrizCosto = new ArrayList<ArrayList<Nodo>>();
        for(int i = 0; i < airports.size(); i++){
            ArrayList<Nodo> auxList = new ArrayList<Nodo>();
            for(int j = 0; j < airports.size(); j++){
                auxList.add(new Nodo());
            }
            matrizCosto.add(auxList);
        }

        String csvAirports = "D:/DP1---A-Algorithm-Java/src/com/manRedex/algoritmo/vuelos.csv";
        BufferedReader csvReader = new BufferedReader(new FileReader(csvAirports));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            int indexA = searchIndex(data[0], airports);
            int indexB = searchIndex(data[1], airports);
            matrizCosto.get(indexA).get(indexB).addNewFlight(Integer.parseInt(data[4]),data[2],data[3]);
        }
        csvReader.close();

        return matrizCosto;
    }



    public RouteFinder(Graph<T> graph, Scorer<T> nextNodeScorer, Scorer<T> targetScorer) {
        this.graph = graph;
        this.nextNodeScorer = nextNodeScorer;
        this.targetScorer = targetScorer;
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> airports = readAirports();
        ArrayList<ArrayList<Nodo>> matrizCosto = readNodes(airports);

        int ran1 = (int) (Math.random() * airports.size());
        int ran2 = (int) (Math.random() * airports.size());

        System.out.println(ran1);
        System.out.println(ran2);

        City A = new City(airports.get(ran1), ran1);
        City B = new City(airports.get(ran2), ran2);

        String cad2 = "From " + airports.get(ran1) + " to " + airports.get(ran2);
        System.out.println(cad2);

        /*Hasta aqui todo ok*/

    }

    public List<T> findRoute(T from, T to) {
        Map<T, RouteNode<T>> allNodes = new HashMap<>();
        Queue<RouteNode> openSet = new PriorityQueue<>();

        RouteNode<T> start = new RouteNode<>(from, null, 0d, targetScorer.computeCost(from, to));
        allNodes.put(from, start);
        openSet.add(start);

        while (!openSet.isEmpty()) {
            System.out.println("Open Set contains: " + openSet.stream().map(RouteNode::getCurrent).collect(Collectors.toSet()));
            RouteNode<T> next = openSet.poll();
            System.out.println("Looking at node: " + next);
            if (next.getCurrent().equals(to)) {
                System.out.println("Found our destination!");

                List<T> route = new ArrayList<>();
                RouteNode<T> current = next;
                do {
                    route.add(0, current.getCurrent());
                    current = allNodes.get(current.getPrevious());
                } while (current != null);

                System.out.println("Route: " + route);
                return route;
            }

            graph.getConnections(next.getCurrent()).forEach(connection -> {
                double newScore = next.getRouteScore() + nextNodeScorer.computeCost(next.getCurrent(), connection);
                RouteNode<T> nextNode = allNodes.getOrDefault(connection, new RouteNode<>(connection));
                allNodes.put(connection, nextNode);

                if (nextNode.getRouteScore() > newScore) {
                    nextNode.setPrevious(next.getCurrent());
                    nextNode.setRouteScore(newScore);
                    nextNode.setEstimatedScore(newScore + targetScorer.computeCost(connection, to));
                    openSet.add(nextNode);
                    System.out.println("Found a better route to node: " + nextNode);
                }
            });
        }

        throw new IllegalStateException("No route found");
    }
}

