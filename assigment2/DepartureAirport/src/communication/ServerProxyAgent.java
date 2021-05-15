package communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import departureairport.DepartureAirport;

public class ServerProxyAgent implements Runnable {

    private CommunicationChannel channel;
    private DepartureAirport departureAirport;

    public ServerProxyAgent(CommunicationChannel communicationChannel, DepartureAirport departureAirport) {
        this.channel = communicationChannel;
        this.departureAirport = departureAirport;
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
        if (message.getMethod() == "waitInQueue") {
            this.departureAirport.waitInQueue(message.getId());
        } else if (message.getMethod() == "showDocuments") {
            this.departureAirport.showDocuments(message.getId());
        } else if (message.getMethod() == "waitForNextPassenger") {
            boolean wait = this.departureAirport.waitForNextPassenger();
            messageOut.setWait(wait);
        } else if (message.getMethod() == "checkDocuments") {
            int id = this.departureAirport.checkDocuments();
            messageOut.setId(id);
        } else if (message.getMethod() == "parkAtTransferGate") {
            boolean wait = this.departureAirport.parkAtTransferGate();
            messageOut.setWait(wait);
        }
        this.channel.writeObject(messageOut);
        this.channel.close();
    }

}
