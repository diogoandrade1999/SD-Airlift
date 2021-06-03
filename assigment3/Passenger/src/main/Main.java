package main;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import departureairport.DepartureAirportInt;
import departureairport.DepartureAirportPassenger;
import destinationairport.DestinationAirportInt;
import destinationairport.DestinationAirportPassenger;
import entity.Passenger;
import plane.PlaneInt;
import plane.PlanePassenger;

/**
 * Main Passengers
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public class Main {

    private static final int TOTAL_PASSENGERS = 21;
    private String registryHost;
    private int registryPort;

    /**
     * Creates an Main Passengers. And starts the simulation.
     * 
     * @param registryHost The Registry Host.
     * @param registryPort The Registry Port.
     */
    private Main(String registryHost, int registryPort) {
        this.registryHost = registryHost;
        this.registryPort = registryPort;
        this.initSimulation();
    }

    /**
     * Starts the simulation. Create the Services and Entitie. Starts the threads
     * Passengers.
     * 
     * @throws RemoteException
     * @throws NotBoundException
     */
    private void initSimulation() {
        // Register
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(this.registryHost, this.registryPort);
        } catch (RemoteException e) {
            System.err.println("RMI registry creation exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        // Services
        DepartureAirportPassenger departureAirport = null;
        DestinationAirportPassenger destinationAirport = null;
        PlanePassenger plane = null;
        try {
            departureAirport = (DepartureAirportInt) registry.lookup("DepartureAirport");
            destinationAirport = (DestinationAirportInt) registry.lookup("DestinationAirport");
            plane = (PlaneInt) registry.lookup("Plane");
        } catch (RemoteException e) {
            System.err.println("Passenger look up exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.err.println("Passenger not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        // Entitie
        Passenger passenger;

        // Start
        for (int i = 0; i < TOTAL_PASSENGERS; i++) {
            passenger = new Passenger(departureAirport, destinationAirport, plane, i);
            new Thread(passenger, "passenger" + i).start();
        }
    }

    /**
     * Validates de command line arguments and create the Main Passengers.
     * 
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        String registryHost;
        int registryPort = 0;

        if (args.length != 2) {
            System.err.println("Error: Number of Arguments is Wrong!");
            System.exit(1);
        }

        registryHost = args[0];
        try {
            registryPort = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Argument " + args[1] + " must be an integer!");
            System.exit(1);
        }

        new Main(registryHost, registryPort);
    }
}
