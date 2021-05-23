package main;

import communication.CommunicationChannel;
import communication.ServerProxyAgent;
import repository.Repository;
import repository.SharedRegionInt;

/**
 * Main Repository
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public class Main {

    private static final int TOTAL_PASSENGERS = 21;
    private String host;
    private int port;

    /**
     * Creates an Main Repository. And starts the simulation.
     * 
     * @param host The Repository Host.
     * @param port The Repository Port.
     */
    private Main(String host, int port) {
        this.host = host;
        this.port = port;
        this.initSimulation();
    }

    /**
     * Starts the simulation. Create the Communication Channel, Repository, Shared
     * Region Interface and the Server Proxy Agent. Starts the server.
     */
    private void initSimulation() {
        // Communication Channel
        CommunicationChannel communicationChannel = new CommunicationChannel(this.host, this.port);

        // Service
        Repository repository = new Repository(TOTAL_PASSENGERS);
        SharedRegionInt sharedRegionInt = new SharedRegionInt(repository);

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
     * Validates de command line arguments and create the Main Repository.
     * 
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        String host;
        int port = 0;

        if (args.length != 2) {
            System.err.println("Error: Number of Arguments is Wrong!");
            System.exit(1);
        }

        host = args[0];
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Argument " + args[1] + " must be an integer!");
            System.exit(1);
        }

        new Main(host, port);
    }
}
