package airlift.destinationairport;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class DestinationAirport
        implements DestinationAirportPilot, DestinationAirportHostess, DestinationAirportPassenger {

    private ReentrantLock lock = new ReentrantLock(true);
    private Queue<Integer> passengers = new LinkedList<>();

    @Override
    public void atDestination(int passengerId) {
        this.lock.lock();
        try {
            this.passengers.add(passengerId);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public int numberPassengersInDestination() {
        this.lock.lock();
        try {
            return this.passengers.size();
        } finally {
            this.lock.unlock();
        }
    }
}
