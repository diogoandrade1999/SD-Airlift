package repository;

import entity.HostessState;
import entity.PassengerState;
import entity.PilotState;

public interface RepositoryInt {

    void updateHostessState(HostessState hostessState);

    void updatePilotState(PilotState pilotState);

    void updatePassengerState(PassengerState passengerState, int passengerId);

    void updatePassengerInCheck(int passengerInCheck);

}
