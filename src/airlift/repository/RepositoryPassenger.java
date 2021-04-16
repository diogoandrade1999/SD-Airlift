package airlift.repository;

import airlift.entity.PassengerState;

public interface RepositoryPassenger {

    void updatePassengerState(PassengerState passengerState, int passengerId);

    void updatePassengerInCheck(int passengerId);

}
