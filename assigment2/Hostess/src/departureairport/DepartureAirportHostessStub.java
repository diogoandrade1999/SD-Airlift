package departureairport;

import communication.CommunicationChannel;
import communication.Message;

public class DepartureAirportHostessStub implements DepartureAirportHostess {

    private String host;
    private int port;
    private CommunicationChannel channel;
    private Message message;

    public DepartureAirportHostessStub(String host, int port) {
        this.host = host;
        this.port = port;
    }

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
