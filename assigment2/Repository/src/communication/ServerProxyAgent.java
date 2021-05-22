package communication;

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
        Message message = this.channel.readObject();

        Message messageOut = new Message();
        if (message.getMethod().equals("updateHostessState")) {
            this.repository.updateHostessState(message.getHostessState());
        } else if (message.getMethod().equals("updatePilotState")) {
            this.repository.updatePilotState(message.getPilotState());
        } else if (message.getMethod().equals("updatePassengerState")) {
            this.repository.updatePassengerState(message.getPassengerState(), message.getId());
        } else if (message.getMethod().equals("updatePassengerInCheck")) {
            this.repository.updatePassengerInCheck(message.getId());
        }
        this.channel.writeObject(messageOut);
        this.channel.close();
    }

}
