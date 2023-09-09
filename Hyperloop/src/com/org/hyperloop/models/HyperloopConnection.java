package com.org.hyperloop.models;


public class HyperloopConnection {
    private HyperloopStation source;
    private HyperloopStation destination;
    private int distance;

    public HyperloopConnection(HyperloopStation source, HyperloopStation destination, int distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    public HyperloopStation getSource() {
        return source;
    }

    public HyperloopStation getDestination() {
        return destination;
    }

    public int getDistance() {
        return distance;
    }
}
