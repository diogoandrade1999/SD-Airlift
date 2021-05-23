package main;

import departureairport.DepartureAirportHostessStub;
import entity.Hostess;
import plane.PlaneHostessStub;

/**
 * Main Hostess
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public class Main {

    private String departureAirportHost;
    private String planeHost;
    private int departureAirportPort;
    private int planePort;

    /**
     * Creates an Main Hostess. And starts the simulation.
     * 
     * @param departureAirportHost The Departure Airport Host.
     * @param planeHost            The Plane Host.
     * @param departureAirportPort The Departure Airport Port.
     * @param planePort            The Plane Port.
     */
    private Main(String departureAirportHost, String planeHost, int departureAirportPort, int planePort) {
        this.departureAirportHost = departureAirportHost;
        this.planeHost = planeHost;
        this.departureAirportPort = departureAirportPort;
        this.planePort = planePort;
        this.initSimulation();
    }

    /**
     * Starts the simulation. Create the Services and Entitie. Starts the threads
     * Hostess.
     */
    private void initSimulation() {
        // Services
        DepartureAirportHostessStub departureAirport = new DepartureAirportHostessStub(this.departureAirportHost,
                this.departureAirportPort);
        PlaneHostessStub plane = new PlaneHostessStub(this.planeHost, this.planePort);

        // Entitie
        Hostess hostess = new Hostess(departureAirport, plane);
        new Thread(hostess, "hostess").start();
    }

    /**
     * Validates de command line arguments and create the Main Hostess.
     * 
     * @param args The command line arguments.
     */
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
