package communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import repository.Repository;

public class ServerProxyAgent implements Runnable {

    private CommunicationChannel channel;
    private Repository repository;

    public ServerProxyAgent(CommunicationChannel communicationChannel, Repository repository) {
        this.channel = communicationChannel;
        this.repository = repository;
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
        if (message.getMethod() == "updateHostessState") {
            this.repository.updateHostessState(message.getHostessState());
        } else if (message.getMethod() == "updatePilotState") {
            this.repository.updatePilotState(message.getPilotState());
        } else if (message.getMethod() == "updatePassengerState") {
            this.repository.updatePassengerState(message.getPassengerState(), message.getId());
        } else if (message.getMethod() == "updatePassengerState") {
            this.repository.updatePassengerInCheck(message.getId());
        }
        this.channel.writeObject(messageOut);
        this.channel.close();
    }

}
