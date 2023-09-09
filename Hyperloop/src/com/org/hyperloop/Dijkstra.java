package com.org.hyperloop;


import com.org.hyperloop.models.HyperloopConnection;
import com.org.hyperloop.models.HyperloopNetwork;
import com.org.hyperloop.models.HyperloopStation;

import java.util.*;

class Dijkstra{
    public static Map<HyperloopStation, Integer> findShortestPath(HyperloopNetwork network, HyperloopStation start, HyperloopStation end) {
        Map<HyperloopStation, Integer> shortestDistances = new HashMap<>();
        Map<HyperloopStation, HyperloopStation> predecessors = new HashMap<>();
        PriorityQueue<HyperloopStation> queue = new PriorityQueue<>(Comparator.comparingDouble(shortestDistances::get));

        for (HyperloopStation station : network.getStations()) {
            shortestDistances.put(station, Integer.MAX_VALUE);
        }

        shortestDistances.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            HyperloopStation current = queue.poll();
            for (HyperloopConnection connection : network.getConnections()) {
                if (connection.getSource().equals(current)) {
                    HyperloopStation neighbor = connection.getDestination();
                    Integer tentativeDistance = shortestDistances.get(current) + connection.getDistance();

                    if (tentativeDistance < shortestDistances.get(neighbor)) {
                        shortestDistances.put(neighbor, tentativeDistance);
                        predecessors.put(neighbor, current);
                        queue.add(neighbor);
                    }
                }
            }
        }

        if (!predecessors.containsKey(end)) {
            return null; // No path found
        }

        List<HyperloopStation> path = new ArrayList<>();
        HyperloopStation current = end;

        while (current != null) {
            path.add(current);
            current = predecessors.get(current);
        }

        Collections.reverse(path);

        Map<HyperloopStation, Integer> result = new LinkedHashMap<>();
        for (HyperloopStation station : path) {
            result.put(station, shortestDistances.get(station));
        }

        return result;
    }
}
