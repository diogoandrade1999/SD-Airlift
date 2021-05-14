package repository;

import entity.HostessState;

public interface RepositoryHostess {

    void updateHostessState(HostessState hostessState);

    void updatePassengerInCheck(int passengerId);

}
