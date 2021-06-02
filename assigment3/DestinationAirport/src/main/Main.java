package main;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import destinationairport.DestinationAirport;
import destinationairport.DestinationAirportPassenger;
import register.Register;
import repository.RepositoryInt;

/**
 * Main Destination Airport
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public class Main {

    private int port;
    private String registryHost;
    private int registryPort;

    /**
     * Creates an Main Destination Airport. And starts the simulation.
     * 
     * @param port         The Destination Airport Port.
     * @param registryHost The Registry Host.
     * @param registryPort The Registry Port.
     */
    private Main(int port, String registryHost, int registryPort) {
        this.port = port;
        this.registryHost = registryHost;
        this.registryPort = registryPort;
        this.initSimulation();
    }

    /**
     * Starts the simulation. Create security manager, registry, Repository and
     * Destination Airport.
     * 
     * @throws RemoteException
     * @throws NotBoundException
     * @throws AlreadyBoundException
     */
    private void initSimulation() {
        // create and install the security manager
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
        System.out.println("Security manager was installed!");

        // Registry
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(this.registryHost, this.registryPort);
        } catch (RemoteException e) {
            System.err.println("RMI registry creation exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("RMI registry was created!");

        // Repository
        RepositoryInt repository = null;
        try {
            repository = (RepositoryInt) registry.lookup("Repository");
        } catch (RemoteException e) {
            System.err.println("Pilot look up exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.err.println("Pilot not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        // Service
        DestinationAirport destinationAirport = new DestinationAirport(repository);
        DestinationAirportPassenger destinationAirportPassenger = null;
        try {
            destinationAirportPassenger = (DestinationAirportPassenger) UnicastRemoteObject
                    .exportObject(destinationAirport, this.port);
        } catch (RemoteException e) {
            System.out.println("Destination Airport stub generation exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Stub was generated!");

        // Register
        Register register = null;
        try {
            register = (Register) registry.lookup("RegisterHandler");
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try {
            register.bind("DestinationAirportPassenger", destinationAirportPassenger);
        } catch (RemoteException e) {
            System.out.println("Destination Airport registration exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("Destination Airport already bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Destination Airport object was registered!");

    }

    /**
     * Validates de command line arguments and create the Main Destination Airport.
     * 
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        int port = 0;
        String registryHost;
        int registryPort = 0;

        if (args.length != 3) {
            System.err.println("Error: Number of Arguments is Wrong!");
            System.exit(1);
        }

        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Argument " + args[0] + " must be an integer!");
            System.exit(1);
        }
        registryHost = args[1];
        try {
            registryPort = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Argument " + args[2] + " must be an integer!");
            System.exit(1);
        }

        new Main(port, registryHost, registryPort);
    }
}
