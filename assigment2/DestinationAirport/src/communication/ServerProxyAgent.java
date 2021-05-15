package communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import destinationairport.DestinationAirport;

public class ServerProxyAgent implements Runnable {

    private CommunicationChannel channel;
    private DestinationAirport destinationAirport;

    public ServerProxyAgent(CommunicationChannel communicationChannel, DestinationAirport destinationAirport) {
        this.channel = communicationChannel;
        this.destinationAirport = destinationAirport;
    }

    @Override
    public void run() {
        ObjectInputStream in;
        Message message = null;

        Socket client = this.channel.getClient();

        try {
            in = new ObjectInputStream(client.getInputStream());
            message = (Message) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Logger.getLogger(ServerProxyAgent.class.getName()).log(Level.SEVERE, null, e);
        }

        Message messageOut = new Message();
        if (message.getMethod() == "atDestination") {
            this.destinationAirport.atDestination(message.getId());
        }
        this.channel.writeObject(messageOut);
        this.channel.close();
    }

}
