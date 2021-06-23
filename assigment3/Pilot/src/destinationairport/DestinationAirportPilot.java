package destinationairport;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Destination Airport Pilot Interface
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 * @see Remote
 */
public interface DestinationAirportPilot extends Remote {
    /**
     * This method is used to shutdown servers.
     * 
     * @throws RemoteException
     */
    void shutdown() throws RemoteException;

}
