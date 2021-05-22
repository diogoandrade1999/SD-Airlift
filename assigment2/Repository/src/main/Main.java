package main;

import communication.CommunicationChannel;
import communication.ServerProxyAgent;
import repository.Repository;

public class Main {

    private static final int TOTAL_PASSENGERS = 21;
    private String host;
    private int port;

    private Main(String host, int port) {
        this.host = host;
        this.port = port;
        this.initSimulation();
    }

    private void initSimulation() {
        // Communication Channel
        CommunicationChannel communicationChannel = new CommunicationChannel(this.host, this.port);

        // Service
        Repository repository = new Repository(TOTAL_PASSENGERS);

        // Server Proxy Agent
        ServerProxyAgent serverProxyAgent;

        // Start Communication Channel
        communicationChannel.start();

        while (true) {
            CommunicationChannel channel = communicationChannel.accept();
            if (channel != null) {
                serverProxyAgent = new ServerProxyAgent(channel, repository);
                new Thread(serverProxyAgent, "proxyAgent").start();
            } else {
                break;
            }
        }

        // End Communication Channel
        communicationChannel.end();
    }

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
