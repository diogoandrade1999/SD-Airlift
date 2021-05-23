package plane;

import communication.CommunicationChannel;
import communication.Message;

/**
 * Plane Pilot Stub
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public class PlanePilotStub implements PlanePilot {

    private String host;
    private int port;
    private CommunicationChannel channel;
    private Message message;

    /**
     * Creates an Plane Pilot Stub.
     * 
     * @param host The plane Host.
     * @param port The plane Port.
     */
    public PlanePilotStub(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * This method is used to inform that the plane is ready for boarding.
     */
    @Override
    public void informPlaneReadyForBoarding() {
        this.message = new Message();
        this.message.setMethod("informPlaneReadyForBoarding");

        this.channel = new CommunicationChannel(this.host, this.port);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

    /**
     * This method is used to pilot wait for all in board.
     */
    @Override
    public void waitForAllInBoard() {
        this.message = new Message();
        this.message.setMethod("waitForAllInBoard");

        this.channel = new CommunicationChannel(this.host, this.port);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

    /**
     * This method is used to announce arrival.
     */
    @Override
    public void announceArrival() {
        this.message = new Message();
        this.message.setMethod("announceArrival");

        this.channel = new CommunicationChannel(this.host, this.port);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

}
