package com.org.hyperloop;

import com.org.hyperloop.models.HyperloopNetwork;
import com.org.hyperloop.models.HyperloopStation;
import com.org.hyperloop.models.Passenger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HyperloopBookingSystemWithDijkstra {


    HyperloopStation startStation, endStation;

    public boolean hyperLoopInit(HyperloopNetwork network, List<String> stations, List<String> sources, List<String> destinations, List<Integer> distances) {

        for (String station : stations) {
            network.addStation(new HyperloopStation(station));
        }

        // Define connections and distances between stations
        for (int i = 0; i < sources.size(); i++) {
            network.addConnection(findStationByName(network, sources.get(i)), findStationByName(network, destinations.get(i)), distances.get(i));
        }

        return true;

    }

    public boolean startPod(HyperloopNetwork network, String startingPoint, Passenger passenger) {

        startStation = findStationByName(network, startingPoint);
        endStation = null;
        endStation = findStationByName(network, passenger.getDestination());
        if (startStation != null && endStation != null) {
            // Find the shortest path between the specified start and end stations
            Map<HyperloopStation, Integer> shortestPath = Dijkstra.findShortestPath(network, startStation, endStation);
            if (shortestPath != null) {
                List<HyperloopStation> path = new ArrayList<>();

                for (Map.Entry<HyperloopStation, Integer> entry : shortestPath.entrySet()) {
                    HyperloopStation station = entry.getKey();
                    path.add(station);
                }
                System.out.print(passenger.getName() + " ");
                for (HyperloopStation station : path) {

                    System.out.print(station.getName() + " ");
                }
                System.out.println();
                return true;
            } else {
                if (endStation.getName().equals(startingPoint)) {
                    System.out.println(passenger.getName()+ " is already in the destination. \nIf next person is available in the queue will go in this pod");
                } else {
                    System.out.println("No path found between " + startStation.getName() + " and  " + endStation.getName() + " so " + passenger.getName() + " is removed from queue. Please add the passenger again with available path. \nIf next person is available in the queue will go in this pod");
                }
            }
        } else {
            System.out.println("Start or end station not found for " + passenger.getName() + " so he/she is removed from queue. Please add the passenger again with available station. \nIf next person is available in the queue will go in this pod");
        }
        return false;
    }

    private static HyperloopStation findStationByName(HyperloopNetwork network, String stationName) {
        for (HyperloopStation station : network.getStations()) {
            if (station.getName().equalsIgnoreCase(stationName)) {
                return station;
            }
        }
        return null;
    }
}
