package plane;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Plane Passenger Interface
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 * @see Remote
 */
public interface PlanePassenger extends Remote {
    /**
     * This method is used to passenger board the plane.
     * 
     * @param passengerId The passenger id.
     * @throws RemoteException
     */
    void boardThePlane(int passengerId) throws RemoteException;

    /**
     * This method is used to passenger wait for end of flight.
     * 
     * @param passengerId The passenger id.
     * @throws RemoteException
     */
    void waitForEndOfFlight(int passengerId) throws RemoteException;

    /**
     * This method is used to passenger leave the plane.
     * 
     * @param passengerId The passenger id.
     * @throws RemoteException
     */
    void leaveThePlane(int passengerId) throws RemoteException;

}
