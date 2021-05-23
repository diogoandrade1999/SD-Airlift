package destinationairport;

import communication.CommunicationChannel;
import communication.Message;

/**
 * Destination Airport Passenger Stub
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public class DestinationAirportPassengerStub implements DestinationAirportPassenger {

    private String host;
    private int port;
    private CommunicationChannel channel;
    private Message message;

    /**
     * Creates an Destination Airport Passenger Stub.
     * 
     * @param host The destination airport host.
     * @param port The destination airport port.
     */
    public DestinationAirportPassengerStub(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * This method is used to passenger to tell he is at destination.
     * 
     * @param passengerId The passenger id.
     */
    @Override
    public void atDestination(int passengerId) {
        this.message = new Message();
        this.message.setMethod("atDestination");
        this.message.setId(passengerId);

        this.channel = new CommunicationChannel(this.host, this.port);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

}
