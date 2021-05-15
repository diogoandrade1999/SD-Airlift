package plane;

import communication.CommunicationChannel;
import communication.Message;

public class PlanePassengerStub implements PlanePassenger {

    private final static int PORT = 2003;
    private CommunicationChannel channel;
    private Message message;

    @Override
    public void boardThePlane(int passengerId) {
        this.message = new Message();
        this.message.setMethod("informPlaneReadyToTakeOff");
        this.message.setId(passengerId);

        this.channel = new CommunicationChannel(PORT);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

    @Override
    public void waitForEndOfFlight(int passengerId) {
        this.message = new Message();
        this.message.setMethod("informPlaneReadyToTakeOff");
        this.message.setId(passengerId);

        this.channel = new CommunicationChannel(PORT);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

    @Override
    public void leaveThePlane(int passengerId) {
        this.message = new Message();
        this.message.setMethod("informPlaneReadyToTakeOff");
        this.message.setId(passengerId);

        this.channel = new CommunicationChannel(PORT);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

}
