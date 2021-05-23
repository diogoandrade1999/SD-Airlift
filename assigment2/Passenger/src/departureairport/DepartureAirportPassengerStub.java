package departureairport;

import communication.CommunicationChannel;
import communication.Message;

/**
 * Departure Airport Passenger Stub
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public class DepartureAirportPassengerStub implements DepartureAirportPassenger {

    private String host;
    private int port;
    private CommunicationChannel channel;
    private Message message;

    /**
     * Creates an Departure Airport Passenger Stub.
     * 
     * @param host The departure airport host.
     * @param port The departure airport port.
     */
    public DepartureAirportPassengerStub(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * This method is used to passenger wait in queue.
     * 
     * @param passengerId The passenger id.
     */
    @Override
    public void waitInQueue(int passengerId) {
        this.message = new Message();
        this.message.setMethod("waitInQueue");
        this.message.setId(passengerId);

        this.channel = new CommunicationChannel(this.host, this.port);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

    /**
     * This method is used to passenger show the documents.
     * 
     * @param passengerId The passenger id.
     */
    @Override
    public void showDocuments(int passengerId) {
        this.message = new Message();
        this.message.setMethod("showDocuments");
        this.message.setId(passengerId);

        this.channel = new CommunicationChannel(this.host, this.port);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

}
