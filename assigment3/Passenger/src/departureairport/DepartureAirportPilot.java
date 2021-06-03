package departureairport;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Departure Airport Pilot Interface
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 * @see Remote
 */
public interface DepartureAirportPilot extends Remote {
    /**
     * This method is used to pilot park at transfer gate.
     * 
     * @return true if need to wait otherwise false
     * @throws RemoteException
     */
    boolean parkAtTransferGate() throws RemoteException;

}
