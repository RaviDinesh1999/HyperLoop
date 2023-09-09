package com.org.hyperloop;

import com.org.hyperloop.models.HyperloopNetwork;
import com.org.hyperloop.models.Passenger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Runner {
    static List<Passenger> passengers = new ArrayList<>();
    static List<Passenger> sortedPassengers = new ArrayList<>();


    public static void main(String[] args) {

        HyperloopBookingSystemWithDijkstra hyperloopBookingSystem = new HyperloopBookingSystemWithDijkstra();
        Scanner scan = new Scanner(System.in);
        List<String> station = new ArrayList<>();
        List<String> uniqueStation = new ArrayList<>();
        HyperloopNetwork network = new HyperloopNetwork();
        List<String> start = new ArrayList<>();
        List<String> end = new ArrayList<>();
        List<Integer> distance = new ArrayList<>();
        boolean isInitialized = false;

        boolean isPathFound;
        boolean loop = true;
        String starting_point = null;

        while (loop) {
            String choice;
            isPathFound = false;
            choice = scan.next();
            switch (choice) {
                case "INIT":
                    int noOfConnection = scan.nextInt();
                    starting_point = scan.next();
                    for (int i = 0; i < noOfConnection; i++) {
                        start.add(scan.next());
                        end.add(scan.next());
                        station.add(start.get(i));
                        station.add(end.get(i));
                        distance.add(scan.nextInt());
                        scan.nextLine();
                    }
                    uniqueStation = station.stream().distinct().collect(Collectors.toList());
                    isInitialized = hyperloopBookingSystem.hyperLoopInit(network, uniqueStation, start, end, distance);
                    break;

                case "ADD_PASSENGER":
                    if (isInitialized) {
                        int noOfPassenger = scan.nextInt();
                        for (int i = 0; i < noOfPassenger; i++) {
                            Passenger passenger = new Passenger();
                            passenger.setName(scan.next());
                            passenger.setAge(scan.nextInt());
                            passenger.setDestination(scan.next());
                            passengers.add(passenger);
                        }
                        sortedPassengers = passengers.stream()
                                .sorted(Comparator.comparingInt(Passenger::getAge).reversed())
                                .collect(Collectors.toList());
                    } else {
                        System.out.println("Please initialize the network first");
                    }
                    break;

                case "START_POD":
                    if (isInitialized) {
                        int noOfPodToStart = scan.nextInt();
                        if (noOfPodToStart > passengers.size()) {
                            noOfPodToStart = passengers.size();
                            System.out.println("Passenger count is less than Pod count \nOnly " + noOfPodToStart + "started as the passenger count is " + passengers.size());
                        }
                        for (int i = 0; i < noOfPodToStart; i++) {
                            int j = 0;
                            isPathFound = hyperloopBookingSystem.startPod(network, starting_point, sortedPassengers.get(j));
                            passengers.remove(sortedPassengers.get(j));
                            sortedPassengers.remove(j);
                            if (!isPathFound && passengers.size() != 0 && noOfPodToStart<=passengers.size()) {
                                noOfPodToStart++;

                            }
                        }
                    } else {
                        System.out.println("Please initialize the network first");
                    }

                    break;

                case "PRINT_Q":
                    if (isInitialized) {
                        System.out.println(passengers.size());
                        for (Passenger passenger : passengers) {
                            System.out.println(passenger.getName() + " " + passenger.getAge());
                        }
                    } else {
                        System.out.println("Please initialize the network first");
                    }

                    break;

                case "EXIT":
                    loop = false;
                    System.out.println("Thank you for using Hyperloop System");
            }

        }
    }
}
