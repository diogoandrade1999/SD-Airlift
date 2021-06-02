package departureairport;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Departure Airport Hostess Interface
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 * @see Remote
 */
public interface DepartureAirportHostess extends Remote {
    /**
     * This method is used to hostess wait for next passenger.
     * 
     * @return true if need to wait otherwise false
     * @throws RemoteException
     */
    boolean waitForNextPassenger() throws RemoteException;

    /**
     * This method is used to hostess check documents.
     * 
     * @return The passenger id;
     * @throws RemoteException
     */
    int checkDocuments() throws RemoteException;

}
