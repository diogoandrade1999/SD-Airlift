package plane;

import communication.CommunicationChannel;
import communication.Message;

/**
 * Plane Passenger Stub
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public class PlanePassengerStub implements PlanePassenger {

    private String host;
    private int port;
    private CommunicationChannel channel;
    private Message message;

    /**
     * Creates an Plane Passenger Stub.
     * 
     * @param host The plane Host.
     * @param port The plane Port.
     */
    public PlanePassengerStub(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * This method is used to passenger board the plane.
     * 
     * @param passengerId The passenger id.
     */
    @Override
    public void boardThePlane(int passengerId) {
        this.message = new Message();
        this.message.setMethod("boardThePlane");
        this.message.setId(passengerId);

        this.channel = new CommunicationChannel(this.host, this.port);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

    /**
     * This method is used to passenger wait for end of flight.
     * 
     * @param passengerId The passenger id.
     */
    @Override
    public void waitForEndOfFlight(int passengerId) {
        this.message = new Message();
        this.message.setMethod("waitForEndOfFlight");
        this.message.setId(passengerId);

        this.channel = new CommunicationChannel(this.host, this.port);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

    /**
     * This method is used to passenger leave the plane.
     * 
     * @param passengerId The passenger id.
     */
    @Override
    public void leaveThePlane(int passengerId) {
        this.message = new Message();
        this.message.setMethod("leaveThePlane");
        this.message.setId(passengerId);

        this.channel = new CommunicationChannel(this.host, this.port);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

}
