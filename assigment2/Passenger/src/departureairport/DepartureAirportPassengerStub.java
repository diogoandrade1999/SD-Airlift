package departureairport;

import communication.CommunicationChannel;
import communication.Message;

public class DepartureAirportPassengerStub implements DepartureAirportPassenger {

    private final static int PORT = 2001;
    private CommunicationChannel channel;
    private Message message;

    @Override
    public void waitInQueue(int passengerId) {
        this.message = new Message();
        this.message.setMethod("waitInQueue");
        this.message.setId(passengerId);

        this.channel = new CommunicationChannel(PORT);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

    @Override
    public void showDocuments(int passengerId) {
        this.message = new Message();
        this.message.setMethod("showDocuments");
        this.message.setId(passengerId);

        this.channel = new CommunicationChannel(PORT);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

}
