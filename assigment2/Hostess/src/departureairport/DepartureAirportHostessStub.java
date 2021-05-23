package departureairport;

import communication.CommunicationChannel;
import communication.Message;

/**
 * Departure Airport Hostess Stub
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public class DepartureAirportHostessStub implements DepartureAirportHostess {

    private String host;
    private int port;
    private CommunicationChannel channel;
    private Message message;

    /**
     * Creates an Departure Airport Hostesss Stub.
     * 
     * @param host The departure airport host.
     * @param port The departure airport port.
     */
    public DepartureAirportHostessStub(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * This method is used to hostess wait for next passenger.
     * 
     * @return true if need to wait otherwise false
     */
    @Override
    public boolean waitForNextPassenger() {
        this.message = new Message();
        this.message.setMethod("waitForNextPassenger");

        this.channel = new CommunicationChannel(this.host, this.port);
        this.channel.open();
        this.channel.writeObject(this.message);
        Message messageOut = this.channel.readObject();
        this.channel.close();
        return messageOut.getWait();
    }

    /**
     * This method is used to hostess check documents.
     * 
     * @return The passenger id;
     */
    @Override
    public int checkDocuments() {
        this.message = new Message();
        this.message.setMethod("checkDocuments");

        this.channel = new CommunicationChannel(this.host, this.port);
        this.channel.open();
        this.channel.writeObject(this.message);
        Message messageOut = this.channel.readObject();
        this.channel.close();
        return messageOut.getId();
    }

}
