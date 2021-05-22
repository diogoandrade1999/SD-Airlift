package destinationairport;

import communication.CommunicationChannel;
import communication.Message;

public class DestinationAirportPassengerStub implements DestinationAirportPassenger {

    private String host;
    private int port;
    private CommunicationChannel channel;
    private Message message;

    public DestinationAirportPassengerStub(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void atDestination(int passengerId) {
        this.message = new Message();
        this.message.setMethod("atDestination");
        this.message.setId(passengerId);

        this.channel = new CommunicationChannel(this.host, this.port);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

}
