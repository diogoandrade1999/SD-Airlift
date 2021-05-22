package departureairport;

import communication.CommunicationChannel;
import communication.Message;

public class DepartureAirportPilotStub implements DepartureAirportPilot {

    private String host;
    private int port;
    private CommunicationChannel channel;
    private Message message;

    public DepartureAirportPilotStub(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public boolean parkAtTransferGate() {
        this.message = new Message();
        this.message.setMethod("parkAtTransferGate");

        this.channel = new CommunicationChannel(this.host, this.port);
        this.channel.open();
        this.channel.writeObject(this.message);
        Message messageOut = this.channel.readObject();
        this.channel.close();
        return messageOut.getWait();
    }

}
