package main;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import register.Register;
import register.RegisterRemoteObject;

/**
 * Main Register
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public class Main {

    private int port;
    private String registryHost;
    private int registryPort;

    /**
     * Creates an Main Departure Airport. And starts the simulation.
     * 
     * @param port         The Register Port.
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
     * Starts the simulation. Create security manager, registry and Register.
     * 
     * @throws RemoteException
     * @throws NotBoundException
     */
    private void initSimulation() {
        // create and install the security manager
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
        System.out.println("Security manager was installed!");

        // Register
        RegisterRemoteObject regEngine = new RegisterRemoteObject(this.registryHost, this.registryPort);
        Register register = null;
        try {
            register = (Register) UnicastRemoteObject.exportObject(regEngine, this.port);
        } catch (RemoteException e) {
            System.err.println("RegisterRemoteObject stub generation exception: " + e.getMessage());
            System.exit(1);
        }
        System.out.println("Stub was generated!");

        // Registry
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(this.registryHost, this.registryPort);
        } catch (RemoteException e) {
            System.err.println("RMI registry creation exception: " + e.getMessage());
            System.exit(1);
        }
        System.out.println("RMI registry was created!");

        try {
            registry.rebind("RegisterHandler", register);
        } catch (RemoteException e) {
            System.err.println("RegisterRemoteObject remote exception on registration: " + e.getMessage());
            System.exit(1);
        }
        System.out.println("RegisterRemoteObject object was registered!");
    }

    /**
     * Validates de command line arguments and create the Main Registry.
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
