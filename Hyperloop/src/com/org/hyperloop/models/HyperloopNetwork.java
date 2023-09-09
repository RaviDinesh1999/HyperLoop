package com.org.hyperloop.models;

import java.util.ArrayList;
import java.util.List;

public class HyperloopNetwork {
    private List<HyperloopStation> stations;
    private List<HyperloopConnection> connections;

    public HyperloopNetwork() {
        this.stations = new ArrayList<>();
        this.connections = new ArrayList<>();
    }

    public void addStation(HyperloopStation station) {
        stations.add(station);
    }

    public void addConnection(HyperloopStation source, HyperloopStation destination, int distance) {
        HyperloopConnection connection = new HyperloopConnection(source, destination, distance);
        connections.add(connection);
    }

    public List<HyperloopStation> getStations() {
        return stations;
    }

    public List<HyperloopConnection> getConnections() {
        return connections;
    }
}
