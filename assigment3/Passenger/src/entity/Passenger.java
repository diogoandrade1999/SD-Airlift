package entity;

import java.rmi.RemoteException;
import java.util.Random;

import departureairport.DepartureAirportPassenger;
import destinationairport.DestinationAirportPassenger;
import plane.PlanePassenger;

/**
 * Passenger
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 * @see Runnable
 */
public class Passenger implements Runnable {

    private DepartureAirportPassenger departureAirport;
    private DestinationAirportPassenger destinationAirport;
    private PlanePassenger plane;
    private int id;

    /**
     * Creates an Passenger.
     * 
     * @param departureAirport   The departure airport.
     * @param destinationAirport The destination airport.
     * @param plane              The alane.
     * @param id                 The passenger id.
     */
    public Passenger(DepartureAirportPassenger departureAirport, DestinationAirportPassenger destinationAirport,
            PlanePassenger plane, int id) {
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.plane = plane;
        this.id = id;
    }

    /**
     * This method is used when the thread starts.
     * 
     * @throws InterruptedException
     * @throws RemoteException
     */
    @Override
    public void run() {
        // travelToAirport
        try {
            Thread.sleep((new Random().nextInt(9) + 1) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // waitInQueue
        try {
            this.departureAirport.waitInQueue(this.id);

            // showDocuments
            this.departureAirport.showDocuments(this.id);

            // boardThePlane
            this.plane.boardThePlane(this.id);

            // waitForEndOfFlight
            this.plane.waitForEndOfFlight(this.id);

            // atDestination
            this.destinationAirport.atDestination(this.id);

            // leaveThePlane
            this.plane.leaveThePlane(this.id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
