package departureairport;

import communication.CommunicationChannel;
import communication.Message;

public class DepartureAirportHostessStub implements DepartureAirportHostess {

    private final static int PORT = 2001;
    private CommunicationChannel channel;
    private Message message;

    @Override
    public boolean waitForNextPassenger() {
        this.message = new Message();
        this.message.setMethod("waitForNextPassenger");

        this.channel = new CommunicationChannel(PORT);
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

        this.channel = new CommunicationChannel(PORT);
        this.channel.open();
        this.channel.writeObject(this.message);
        Message messageOut = this.channel.readObject();
        this.channel.close();
        return messageOut.getId();
    }

}
