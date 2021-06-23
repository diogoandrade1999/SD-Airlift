package main;

import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import departureairport.DepartureAirport;
import departureairport.DepartureAirportInt;
import register.Register;
import repository.RepositoryInt;

/**
 * Main Departure Airport
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public class Main {

    private static final int TOTAL_PASSENGERS = 21;
    private static final int NUMBER_MAX_PASSENGERS = 10;
    private static final int NUMBER_MIN_PASSENGERS = 5;
    private int port;
    private String registryHost;
    private int registryPort;
    private static boolean end = false;

    /**
     * Creates an Main Departure Airport. And starts the simulation.
     * 
     * @param port         The Departure Airport Port.
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
     * Departure Airport.
     * 
     * @throws RemoteException
     * @throws NotBoundException
     * @throws AlreadyBoundException
     * @throws NoSuchObjectException
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
            System.err.println("Departure Airport look up exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.err.println("Departure Airport not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        // Service
        DepartureAirport departureAirport = new DepartureAirport(repository, TOTAL_PASSENGERS, NUMBER_MAX_PASSENGERS,
                NUMBER_MIN_PASSENGERS);
        DepartureAirportInt departureAirportStub = null;
        try {
            departureAirportStub = (DepartureAirportInt) UnicastRemoteObject.exportObject(departureAirport, this.port);
        } catch (RemoteException e) {
            System.out.println("Departure Airport stub generation exception: " + e.getMessage());
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
            register.bind("DepartureAirport", departureAirportStub);
        } catch (RemoteException e) {
            System.out.println("Departure Airport registration exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("Departure Airport already bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Departure Airport object was registered!");

        // wait for the end of operations
        System.out.println("Departure Airport is in operation!");
        try {
            while (!end)
                synchronized (Class.forName("main.Main")) {
                    try {
                        (Class.forName("main.Main")).wait();
                    } catch (InterruptedException e) {
                        System.out.println("Departure Airport main thread was interrupted!");
                    }
                }
        } catch (ClassNotFoundException e) {
            System.out.println("The data type Departure Airport was not found (blocking)!");
            e.printStackTrace();
            System.exit(1);
        }

        // server shutdown
        boolean shutdownDone = false;
        try {
            register.unbind("DepartureAirport");
        } catch (RemoteException e) {
            System.out.println("Departure Airport deregistration exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("Departure Airport not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Departure Airport was deregistered!");

        try {
            shutdownDone = UnicastRemoteObject.unexportObject(departureAirport, true);
        } catch (NoSuchObjectException e) {
            System.out.println("Departure Airport unexport exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        if (shutdownDone)
            System.out.println("Departure Airport was shutdown!");
    }

    /**
     * Shutdown the simulation.
     * 
     * @throws ClassNotFoundException
     */
    public static void shutdown() {
        end = true;
        try {
            synchronized (Class.forName("main.Main")) {
                (Class.forName("main.Main")).notify();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("The data type Departure Airport was not found (waking up)!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Validates de command line arguments and create the Main Departure Airport.
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
