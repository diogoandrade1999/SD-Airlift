package main;

import communication.CommunicationChannel;
import communication.ServerProxyAgent;
import departureairport.DepartureAirport;
import departureairport.SharedRegionInt;
import repository.RepositoryInt;
import repository.RepositoryStub;

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
    private String host;
    private String repositoryHost;
    private int port;
    private int repositoryPort;

    /**
     * Creates an Main Departure Airport. And starts the simulation.
     * 
     * @param host           The Departure Airport Host.
     * @param repositoryHost The Repository Host.
     * @param port           The Departure Airport Port.
     * @param repositoryPort The Repository Port.
     */
    private Main(String host, String repositoryHost, int port, int repositoryPort) {
        this.host = host;
        this.repositoryHost = repositoryHost;
        this.port = port;
        this.repositoryPort = repositoryPort;
        this.initSimulation();
    }

    /**
     * Starts the simulation. Create the Communication Channel, Repository
     * Interface, Departure Airport, Shared Region Interface and the Server Proxy
     * Agent. Starts the server.
     */
    private void initSimulation() {
        // Communication Channel
        CommunicationChannel communicationChannel = new CommunicationChannel(this.host, this.port);

        // Repository
        RepositoryInt repository = new RepositoryStub(this.repositoryHost, this.repositoryPort);

        // Service
        DepartureAirport departureAirport = new DepartureAirport(repository, TOTAL_PASSENGERS, NUMBER_MAX_PASSENGERS,
                NUMBER_MIN_PASSENGERS);
        SharedRegionInt sharedRegionInt = new SharedRegionInt(departureAirport);

        // Server Proxy Agent
        ServerProxyAgent serverProxyAgent;

        // Start Communication Channel
        communicationChannel.start();

        while (true) {
            CommunicationChannel channel = communicationChannel.accept();
            if (channel != null) {
                serverProxyAgent = new ServerProxyAgent(channel, sharedRegionInt);
                new Thread(serverProxyAgent, "proxyAgent").start();
            } else {
                break;
            }
        }

        // End Communication Channel
        communicationChannel.end();
    }

    /**
     * Validates de command line arguments and create the Main Departure Airport.
     * 
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        String host;
        String repositoryHost;
        int port = 0;
        int repositoryPort = 0;

        if (args.length != 4) {
            System.err.println("Error: Number of Arguments is Wrong!");
            System.exit(1);
        }

        host = args[0];
        repositoryHost = args[1];
        try {
            port = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Argument " + args[2] + " must be an integer!");
            System.exit(1);
        }
        try {
            repositoryPort = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Argument " + args[3] + " must be an integer!");
            System.exit(1);
        }

        new Main(host, repositoryHost, port, repositoryPort);
    }
}
