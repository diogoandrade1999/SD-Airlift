package destinationairport;

import communication.CommunicationChannel;
import communication.Message;

public class DestinationAirportPassengerStub implements DestinationAirportPassenger {

    private final static int PORT = 2002;
    private CommunicationChannel channel;
    private Message message;

    @Override
    public void atDestination(int passengerId) {
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
