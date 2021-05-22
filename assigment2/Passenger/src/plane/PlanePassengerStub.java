package plane;

import communication.CommunicationChannel;
import communication.Message;

public class PlanePassengerStub implements PlanePassenger {

    private String host;
    private int port;
    private CommunicationChannel channel;
    private Message message;

    public PlanePassengerStub(String host, int port) {
        this.host = host;
        this.port = port;
    }

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
