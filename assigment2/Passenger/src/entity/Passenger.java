package entity;

import java.util.Random;

import departureairport.DepartureAirportPassenger;
import destinationairport.DestinationAirportPassenger;
import plane.PlanePassenger;

public class Passenger implements Runnable {

    private DepartureAirportPassenger departureAirport;
    private DestinationAirportPassenger destinationAirport;
    private PlanePassenger plane;
    private int id;

    public Passenger(DepartureAirportPassenger departureAirport, DestinationAirportPassenger destinationAirport,
            PlanePassenger plane, int id) {
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.plane = plane;
        this.id = id;
    }

    @Override
    public void run() {
        // travelToAirport
        try {
            Thread.sleep((new Random().nextInt(30) + 10) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // waitInQueue
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
    }
}
