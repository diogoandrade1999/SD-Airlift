package communication;

import destinationairport.SharedRegionInt;

public class ServerProxyAgent implements Runnable {

    private CommunicationChannel channel;
    private SharedRegionInt sharedRegionInt;

    public ServerProxyAgent(CommunicationChannel communicationChannel, SharedRegionInt sharedRegionInt) {
        this.channel = communicationChannel;
        this.sharedRegionInt = sharedRegionInt;
    }

    @Override
    public void run() {
        Message messageIn = this.channel.readObject();
        Message messageOut = sharedRegionInt.processAndReply(messageIn);
        this.channel.writeObject(messageOut);
        this.channel.close();
    }

}
