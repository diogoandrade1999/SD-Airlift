package departureairport;

import communication.CommunicationChannel;
import communication.Message;

public class DepartureAirportPilotStub implements DepartureAirportPilot {

    private final static int PORT = 2001;
    private CommunicationChannel channel;
    private Message message;

    @Override
    public boolean parkAtTransferGate() {
        this.message = new Message();
        this.message.setMethod("informPlaneReadyForBoarding");

        this.channel = new CommunicationChannel(PORT);
        this.channel.open();
        this.channel.writeObject(this.message);
        Message messageOut = this.channel.readObject();
        this.channel.close();
        return messageOut.getWait();
    }

}
