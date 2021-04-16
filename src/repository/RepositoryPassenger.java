package repository;

import entity.PassengerState;

public interface RepositoryPassenger {

    void updatePassengerState(PassengerState passengerState, int passengerId);

    void updatePassengerInCheck(int passengerId);

}
