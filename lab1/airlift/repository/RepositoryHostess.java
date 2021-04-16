package airlift.repository;

import airlift.entity.HostessState;

public interface RepositoryHostess {

    void updateHostessState(HostessState hostessState);

    void updatePassengerInCheck(int passengerId);

}
