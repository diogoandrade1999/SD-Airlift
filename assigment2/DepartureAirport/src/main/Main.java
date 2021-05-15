package main;

import communication.CommunicationChannel;
import communication.ServerProxyAgent;
import departureairport.DepartureAirport;
import repository.RepositoryInt;
import repository.RepositoryStub;

public class Main {

    private static final int TOTAL_PASSENGERS = 21;
    private static final int NUMBER_MAX_PASSENGERS = 10;
    private static final int NUMBER_MIN_PASSENGERS = 5;
    private static final int PORT = 2001;

    private Main() {
        this.initSimulation();
    }

    private void initSimulation() {
        // Communication Channel
        CommunicationChannel communicationChannel = new CommunicationChannel(PORT);

        // Repository
        RepositoryInt repository = new RepositoryStub();

        // Service
        DepartureAirport departureAirport = new DepartureAirport(repository, TOTAL_PASSENGERS, NUMBER_MAX_PASSENGERS,
                NUMBER_MIN_PASSENGERS);

        // Server Proxy Agent
        ServerProxyAgent serverProxyAgent;

        // Start Communication Channel
        communicationChannel.start();

        while (true) {
            CommunicationChannel channel = communicationChannel.accept();
            if (channel != null) {
                serverProxyAgent = new ServerProxyAgent(channel, departureAirport);
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
