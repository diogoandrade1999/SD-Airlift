package plane;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Plane Hostess Interface
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 * @see Remote
 */
public interface PlaneHostess extends Remote {
    /**
     * This method is used to hostess wait for next flight.
     * 
     * @throws RemoteException
     */
    boolean waitForNextFlight() throws RemoteException;

    /**
     * This method is used to inform plane ready to take off.
     * 
     * @param passengerId The id of last passenger.
     * @throws RemoteException
     */
    void informPlaneReadyToTakeOff(int passengerId) throws RemoteException;

}
