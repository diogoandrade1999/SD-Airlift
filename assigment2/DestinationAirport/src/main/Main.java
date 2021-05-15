package main;

import communication.CommunicationChannel;
import communication.ServerProxyAgent;
import destinationairport.DestinationAirport;
import repository.RepositoryInt;
import repository.RepositoryStub;

public class Main {

    private static final int PORT = 2002;

    private Main() {
        this.initSimulation();
    }

    private void initSimulation() {
        // Communication Channel
        CommunicationChannel communicationChannel = new CommunicationChannel(PORT);

        // Repository
        RepositoryInt repository = new RepositoryStub();

        // Service
        DestinationAirport destinationAirport = new DestinationAirport(repository);

        // Server Proxy Agent
        ServerProxyAgent serverProxyAgent;

        // Start Communication Channel
        communicationChannel.start();

        while (true) {
            CommunicationChannel channel = communicationChannel.accept();
            if (channel != null) {
                serverProxyAgent = new ServerProxyAgent(channel, destinationAirport);
                new Thread(serverProxyAgent, "proxyAgent").start();
            } else {
                break;
            }
        }

        // End Communication Channel
        communicationChannel.end();
    }

    public static void main(String[] args) {
        new Main();
    }
}
