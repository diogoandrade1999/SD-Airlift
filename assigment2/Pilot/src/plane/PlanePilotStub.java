package plane;

import communication.CommunicationChannel;
import communication.Message;

public class PlanePilotStub implements PlanePilot {

    private final static int PORT = 2003;
    private CommunicationChannel channel;
    private Message message;

    @Override
    public void informPlaneReadyForBoarding() {
        this.message = new Message();
        this.message.setMethod("informPlaneReadyForBoarding");

        this.channel = new CommunicationChannel(PORT);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

    @Override
    public void waitForAllInBoard() {
        this.message = new Message();
        this.message.setMethod("waitForAllInBoard");

        this.channel = new CommunicationChannel(PORT);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

    @Override
    public void announceArrival() {
        this.message = new Message();
        this.message.setMethod("announceArrival");

        this.channel = new CommunicationChannel(PORT);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

}
