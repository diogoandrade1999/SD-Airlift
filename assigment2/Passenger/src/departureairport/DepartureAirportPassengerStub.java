package departureairport;

import communication.CommunicationChannel;
import communication.Message;

public class DepartureAirportPassengerStub implements DepartureAirportPassenger {

    private String host;
    private int port;
    private CommunicationChannel channel;
    private Message message;

    public DepartureAirportPassengerStub(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void waitInQueue(int passengerId) {
        this.message = new Message();
        this.message.setMethod("waitInQueue");
        this.message.setId(passengerId);

        this.channel = new CommunicationChannel(this.host, this.port);
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

        this.channel = new CommunicationChannel(this.host, this.port);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

}
