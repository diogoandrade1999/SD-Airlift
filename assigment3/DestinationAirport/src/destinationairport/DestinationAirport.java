package destinationairport;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

import entity.PassengerState;
import main.Main;
import repository.RepositoryInt;

/**
 * Destination Airport
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public class DestinationAirport implements DestinationAirportInt {

    private RepositoryInt repository;
    private ReentrantLock lock = new ReentrantLock(true);
    private Queue<Integer> passengers = new LinkedList<>();

    /**
     * Creates an Destination Airport.
     * 
     * @param repository The repository.
     */
    public DestinationAirport(RepositoryInt repository) {
        this.repository = repository;
    }

    /**
     * This method is used to passenger to tell he is at destination.
     * 
     * @param passengerId The passenger id.
     * @throws RemoteException
     */
    @Override
    public void atDestination(int passengerId) {
        this.lock.lock();
        try {
            // update passenger state
            this.repository.updatePassengerState(PassengerState.AT_DESTINATION, passengerId);

            // add passenger to queue
            this.passengers.add(passengerId);
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * This method is used to pilot shutdown servers.
     * 
     * @throws RemoteException
     */
    @Override
    public void shutdown() throws RemoteException {
        this.lock.lock();
        try {
            Main.shutdown();
        } finally {
            this.lock.unlock();
        }
    }

}
