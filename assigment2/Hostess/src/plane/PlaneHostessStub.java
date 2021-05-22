package plane;

import communication.CommunicationChannel;
import communication.Message;

public class PlaneHostessStub implements PlaneHostess {

    private String host;
    private int port;
    private CommunicationChannel channel;
    private Message message;

    public PlaneHostessStub(String host, int port) {
        this.host = host;
        this.port = port;
    }

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
