package plane;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Plane Pilot Interface
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 * @see Remote
 */
public interface PlanePilot extends Remote {
    /**
     * This method is used to inform that the plane is ready for boarding.
     * 
     * @throws RemoteException
     */
    void informPlaneReadyForBoarding() throws RemoteException;

    /**
     * This method is used to pilot wait for all in board.
     * 
     * @throws RemoteException
     */
    void waitForAllInBoard() throws RemoteException;

    /**
     * This method is used to announce arrival.
     * 
     * @throws RemoteException
     */
    void announceArrival() throws RemoteException;
}
