package departureairport;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Departure Airport Passenger Interface
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 * @see Remote
 */
public interface DepartureAirportPassenger extends Remote{
    /**
     * This method is used to passenger wait in queue.
     * 
     * @param passengerId The passenger id.
     * @throws RemoteException
     */
    void waitInQueue(int passengerId) throws RemoteException;

    /**
     * This method is used to passenger show the documents.
     * 
     * @param passengerId The passenger id.
     * @throws RemoteException
     */
    void showDocuments(int passengerId) throws RemoteException;

}
