package destinationairport;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

import entity.PassengerState;
import repository.Repository;

public class DestinationAirport implements DestinationAirportPassenger {

    private Repository repository;
    private ReentrantLock lock = new ReentrantLock(true);
    private Queue<Integer> passengers = new LinkedList<>();

    public DestinationAirport(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void atDestination(int passengerId) {
        this.lock.lock();
        try {
            // update passenger state
            this.repository.updatePassengerState(PassengerState.AT_DESTINATION, passengerId);

            // add passenger to queue
            this.passengers.add(passengerId);
        } finally {
            this.lock.unlock();
        }
    }

}
