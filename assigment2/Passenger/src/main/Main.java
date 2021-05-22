package main;

import departureairport.DepartureAirportPassengerStub;
import destinationairport.DestinationAirportPassengerStub;
import entity.Passenger;
import plane.PlanePassengerStub;

public class Main {

    private static final int TOTAL_PASSENGERS = 21;
    private String departureAirportHost;
    private String destinationAirportHost;
    private String planeHost;
    private int departureAirportPort;
    private int destinationAirportPort;
    private int planePort;

    private Main(String departureAirportHost, String destinationAirportHost, String planeHost, int departureAirportPort,
            int destinationAirportPort, int planePort) {
        this.departureAirportHost = departureAirportHost;
        this.destinationAirportHost = destinationAirportHost;
        this.planeHost = planeHost;
        this.departureAirportPort = departureAirportPort;
        this.destinationAirportPort = destinationAirportPort;
        this.planePort = planePort;
        this.initSimulation();
    }

    private void initSimulation() {
        // Services
        DepartureAirportPassengerStub departureAirport;
        DestinationAirportPassengerStub destinationAirport;
        PlanePassengerStub plane;

        // Entitie
        Passenger passenger;

        // Start
        for (int i = 0; i < TOTAL_PASSENGERS; i++) {
            // Services
            departureAirport = new DepartureAirportPassengerStub(this.departureAirportHost, this.departureAirportPort);
            destinationAirport = new DestinationAirportPassengerStub(this.destinationAirportHost,
                    this.destinationAirportPort);
            plane = new PlanePassengerStub(this.planeHost, this.planePort);

            // Entitie
            passenger = new Passenger(departureAirport, destinationAirport, plane, i);
            new Thread(passenger, "passenger" + i).start();
        }
    }

    public static void main(String[] args) {
        String departureAirportHost;
        String planeHost;
        String destinationAirportHost;
        int departureAirportPort = 0;
        int destinationAirportPort = 0;
        int planePort = 0;

        if (args.length != 6) {
            System.err.println("Error: Number of Arguments is Wrong!");
            System.exit(1);
        }

        departureAirportHost = args[0];
        destinationAirportHost = args[1];
        planeHost = args[2];
        try {
            departureAirportPort = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Argument " + args[3] + " must be an integer!");
            System.exit(1);
        }
        try {
            destinationAirportPort = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Argument " + args[4] + " must be an integer!");
            System.exit(1);
        }
        try {
            planePort = Integer.parseInt(args[5]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Argument " + args[5] + " must be an integer!");
            System.exit(1);
        }

        new Main(departureAirportHost, destinationAirportHost, planeHost, departureAirportPort, destinationAirportPort,
                planePort);
    }
}
