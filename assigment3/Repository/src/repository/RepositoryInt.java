package repository;

import java.rmi.Remote;
import java.rmi.RemoteException;

import entity.HostessState;
import entity.PassengerState;
import entity.PilotState;

/**
 * Repository Interface
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 * @see Remote
 */
public interface RepositoryInt extends Remote {

    /**
     * This method is used to update the Hostess State in repository.
     * 
     * @param hostessState The Hostess State.
     * @throws RemoteException
     */
    void updateHostessState(HostessState hostessState) throws RemoteException;

    /**
     * This method is used to update the Pilot State in repository.
     * 
     * @param pilotState The Pilot State.
     * @throws RemoteException
     */
    void updatePilotState(PilotState pilotState) throws RemoteException;

    /**
     * This method is used to update the Passenger State in repository.
     * 
     * @param passengerState The Passenger State.
     * @param passengerId    The Passenger Id.
     * @throws RemoteException
     */
    void updatePassengerState(PassengerState passengerState, int passengerId) throws RemoteException;

    /**
     * This method is used to update the Id of Passenger in Check in repository.
     * 
     * @param passengerInCheck The Passenger ID.
     * @throws RemoteException
     */
    void updatePassengerInCheck(int passengerInCheck) throws RemoteException;

    /**
     * This method is used to shutdown servers.
     * 
     * @throws RemoteException
     */
    void shutdown() throws RemoteException;
}
