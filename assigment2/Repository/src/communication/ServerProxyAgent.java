package communication;

import repository.SharedRegionInt;

/**
 * Server Proxy Agent
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 * @see Runnable
 */
public class ServerProxyAgent implements Runnable {

    private CommunicationChannel channel;
    private SharedRegionInt sharedRegionInt;

    /**
     * Creates an Server Proxy Agent.
     * 
     * @param communicationChannel The Communication Channel.
     * @param sharedRegionInt      The Shared Region Interface.
     */
    public ServerProxyAgent(CommunicationChannel communicationChannel, SharedRegionInt sharedRegionInt) {
        this.channel = communicationChannel;
        this.sharedRegionInt = sharedRegionInt;
    }

    /**
     * This method is used when the thread starts.
     */
    @Override
    public void run() {
        Message messageIn = this.channel.readObject();
        Message messageOut = sharedRegionInt.processAndReply(messageIn);
        this.channel.writeObject(messageOut);
        this.channel.close();
    }

}
