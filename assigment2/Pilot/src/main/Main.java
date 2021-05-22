package main;

import departureairport.DepartureAirportPilotStub;
import plane.PlanePilotStub;
import entity.Pilot;

public class Main {

    private String departureAirportHost;
    private String planeHost;
    private int departureAirportPort;
    private int planePort;

    private Main(String departureAirportHost, String planeHost, int departureAirportPort, int planePort) {
        this.departureAirportHost = departureAirportHost;
        this.planeHost = planeHost;
        this.departureAirportPort = departureAirportPort;
        this.planePort = planePort;
        this.initSimulation();
    }

    private void initSimulation() {
        // Services
        DepartureAirportPilotStub departureAirport = new DepartureAirportPilotStub(this.departureAirportHost,
                this.departureAirportPort);
        PlanePilotStub plane = new PlanePilotStub(this.planeHost, this.planePort);

        // Entitie
        Pilot pilot = new Pilot(departureAirport, plane);
        new Thread(pilot, "pilot").start();
    }

    public static void main(String[] args) {
        String departureAirportHost;
        String planeHost;
        int departureAirportPort = 0;
        int planePort = 0;

        if (args.length != 4) {
            System.err.println("Error: Number of Arguments is Wrong!");
            System.exit(1);
        }

        departureAirportHost = args[0];
        planeHost = args[1];
        try {
            departureAirportPort = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Argument " + args[2] + " must be an integer!");
            System.exit(1);
        }
        try {
            planePort = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Argument " + args[3] + " must be an integer!");
            System.exit(1);
        }

        new Main(departureAirportHost, planeHost, departureAirportPort, planePort);
    }
}
