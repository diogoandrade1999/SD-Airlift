package communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import plane.Plane;

public class ServerProxyAgent implements Runnable {

    private CommunicationChannel channel;
    private Plane plane;

    public ServerProxyAgent(CommunicationChannel communicationChannel, Plane plane) {
        this.channel = communicationChannel;
        this.plane = plane;
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
        if (message.getMethod() == "waitForNextFlight") {
            boolean wait = this.plane.waitForNextFlight();
            messageOut.setWait(wait);
        } else if (message.getMethod() == "informPlaneReadyToTakeOff") {
            this.plane.informPlaneReadyToTakeOff(message.getId());
        } else if (message.getMethod() == "informPlaneReadyForBoarding") {
            this.plane.informPlaneReadyForBoarding();
        } else if (message.getMethod() == "waitForAllInBoard") {
            this.plane.waitForAllInBoard();
        } else if (message.getMethod() == "announceArrival") {
            this.plane.announceArrival();
        } else if (message.getMethod() == "boardThePlane") {
            this.plane.boardThePlane(message.getId());
        } else if (message.getMethod() == "waitForEndOfFlight") {
            this.plane.waitForEndOfFlight(message.getId());
        } else if (message.getMethod() == "leaveThePlane") {
            this.plane.leaveThePlane(message.getId());
        }
        this.channel.writeObject(messageOut);
        this.channel.close();
    }

}
