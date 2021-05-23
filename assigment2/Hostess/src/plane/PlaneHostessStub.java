package plane;

import communication.CommunicationChannel;
import communication.Message;

/**
 * Plane Hostess Stub
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public class PlaneHostessStub implements PlaneHostess {

    private String host;
    private int port;
    private CommunicationChannel channel;
    private Message message;

    /**
     * Creates an Plane Hostess Stub.
     * 
     * @param host The plane Host.
     * @param port The plane Port.
     */
    public PlaneHostessStub(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * This method is used to hostess wait for next flight.
     */
    @Override
    public boolean waitForNextFlight() {
        this.message = new Message();
        this.message.setMethod("waitForNextFlight");

        this.channel = new CommunicationChannel(this.host, this.port);
        this.channel.open();
        this.channel.writeObject(this.message);
        Message messageOut = this.channel.readObject();
        this.channel.close();
        return messageOut.getWait();
    }

    /**
     * This method is used to inform plane ready to take off.
     * 
     * @param passengerId The id of last passenger.
     */
    @Override
    public void informPlaneReadyToTakeOff(int passengerId) {
        this.message = new Message();
        this.message.setMethod("informPlaneReadyToTakeOff");
        this.message.setId(passengerId);

        this.channel = new CommunicationChannel(this.host, this.port);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

}
