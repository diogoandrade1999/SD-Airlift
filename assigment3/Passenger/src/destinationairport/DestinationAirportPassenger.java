package destinationairport;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Destination Airport Passenger Interface
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 * @see Remote
 */
public interface DestinationAirportPassenger extends Remote {
    /**
     * This method is used to passenger to tell he is at destination.
     * 
     * @param passengerId The passenger id.
     * @throws RemoteException
     */
    void atDestination(int passengerId) throws RemoteException;

}
