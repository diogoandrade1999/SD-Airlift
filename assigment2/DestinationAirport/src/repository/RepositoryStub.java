package repository;

import communication.CommunicationChannel;
import communication.Message;
import entity.HostessState;
import entity.PassengerState;
import entity.PilotState;

/**
 * Repository Stub
 * 
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public class RepositoryStub implements RepositoryInt {

    private String host;
    private int port;
    private CommunicationChannel channel;
    private Message message;

    /**
     * Creates an Repository Stub.
     * 
     * @param host The repository host.
     * @param port The repository port.
     */
    public RepositoryStub(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * This method is used to update the Hostess State in repository.
     * 
     * @param hostessState The Hostess State.
     */
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

    /**
     * This method is used to update the Pilot State in repository.
     * 
     * @param pilotState The Pilot State.
     */
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

    /**
     * This method is used to update the Passenger State in repository.
     * 
     * @param passengerState The Passenger State.
     * @param passengerId    The Passenger Id.
     */
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

    /**
     * This method is used to update the Id of Passenger in Check in repository.
     * 
     * @param passengerInCheck The Passenger ID.
     */
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
