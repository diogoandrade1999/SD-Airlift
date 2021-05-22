package repository;

import communication.CommunicationChannel;
import communication.Message;
import entity.HostessState;
import entity.PassengerState;
import entity.PilotState;

public class RepositoryStub implements RepositoryInt {

    private String host;
    private int port;
    private CommunicationChannel channel;
    private Message message;

    public RepositoryStub(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void updateHostessState(HostessState hostessState) {
        this.message = new Message();
        this.message.setMethod("updateHostessState");
        this.message.setHostessState(hostessState);

        this.channel = new CommunicationChannel(this.host, this.port);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

    @Override
    public void updatePilotState(PilotState pilotState) {
        this.message = new Message();
        this.message.setMethod("updatePilotState");
        this.message.setPilotState(pilotState);

        this.channel = new CommunicationChannel(this.host, this.port);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

    @Override
    public void updatePassengerState(PassengerState passengerState, int passengerId) {
        this.message = new Message();
        this.message.setMethod("updatePassengerState");
        this.message.setPassengerState(passengerState);
        this.message.setId(passengerId);

        this.channel = new CommunicationChannel(this.host, this.port);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

    @Override
    public void updatePassengerInCheck(int passengerInCheck) {
        this.message = new Message();
        this.message.setMethod("updatePassengerInCheck");
        this.message.setId(passengerInCheck);

        this.channel = new CommunicationChannel(this.host, this.port);
        this.channel.open();
        this.channel.writeObject(this.message);
        this.channel.readObject();
        this.channel.close();
    }

}
