package repository;

import entity.HostessState;
import entity.PassengerState;
import entity.PilotState;

/**
 * Repository Interface
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public interface RepositoryInt {

    /**
     * This method is used to update the Hostess State in repository.
     * 
     * @param hostessState The Hostess State.
     */
    void updateHostessState(HostessState hostessState);

    /**
     * This method is used to update the Pilot State in repository.
     * 
     * @param pilotState The Pilot State.
     */
    void updatePilotState(PilotState pilotState);

    /**
     * This method is used to update the Passenger State in repository.
     * 
     * @param passengerState The Passenger State.
     * @param passengerId    The Passenger Id.
     */
    void updatePassengerState(PassengerState passengerState, int passengerId);

    /**
     * This method is used to update the Id of Passenger in Check in repository.
     * 
     * @param passengerInCheck The Passenger ID.
     */
    void updatePassengerInCheck(int passengerInCheck);

}
