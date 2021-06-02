package entity;

import java.rmi.RemoteException;

import departureairport.DepartureAirportHostess;
import plane.PlaneHostess;

/**
 * Hostess
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 * @see Runnable
 */
public class Hostess implements Runnable {

    private DepartureAirportHostess departureAirport;
    private PlaneHostess plane;

    /**
     * Creates an Hostess.
     * 
     * @param departureAirport The departure airport.
     * @param plane            The plane.
     */
    public Hostess(DepartureAirportHostess departureAirport, PlaneHostess plane) {
        this.departureAirport = departureAirport;
        this.plane = plane;
    }

    /**
     * This method is used when the thread starts.
     * 
     * @throws RemoteException
     */
    @Override
    public void run() {
        try {
            while (true) {
                // waitForNextFlight
                if (!this.plane.waitForNextFlight()) {
                    break;
                }

                int passengerId = -1;
                while (true) {
                    // waitForNextPassenger
                    if (!this.departureAirport.waitForNextPassenger()) {
                        break;
                    }

                    // checkDocuments
                    passengerId = this.departureAirport.checkDocuments();
                }

                // informPlaneReadyToTakeOff
                this.plane.informPlaneReadyToTakeOff(passengerId);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
