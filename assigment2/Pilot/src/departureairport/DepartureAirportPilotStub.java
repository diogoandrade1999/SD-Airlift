package departureairport;

import communication.CommunicationChannel;
import communication.Message;

/**
 * Departure Airport Pilot Stub
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public class DepartureAirportPilotStub implements DepartureAirportPilot {

    private String host;
    private int port;
    private CommunicationChannel channel;
    private Message message;

    /**
     * Creates an Departure Airport Pilot Stub.
     * 
     * @param host The departure airport host.
     * @param port The departure airport port.
     */
    public DepartureAirportPilotStub(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * This method is used to pilot park at transfer gate.
     * 
     * @return true if need to wait otherwise false
     */
    @Override
    public boolean parkAtTransferGate() {
        this.message = new Message();
        this.message.setMethod("parkAtTransferGate");

        this.channel = new CommunicationChannel(this.host, this.port);
        this.channel.open();
        this.channel.writeObject(this.message);
        Message messageOut = this.channel.readObject();
        this.channel.close();
        return messageOut.getWait();
    }

}
