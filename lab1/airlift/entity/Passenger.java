package airlift.entity;

import java.util.Random;

import airlift.departureairport.DepartureAirportPassenger;
import airlift.destinationairport.DestinationAirportPassenger;
import airlift.plane.PlanePassenger;

public class Passenger implements Runnable {

    private PassengerState state = PassengerState.GOING_TO_AIRPORT;
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

    public String getId() {
        String id = String.valueOf(this.id);
        if (this.id < 10) {
            id = 0 + id;
        }
        return id;
    }

    public PassengerState getState() {
        return this.state;
    }

    @Override
    public void run() {
        // travelToAirport
        this.state = PassengerState.GOING_TO_AIRPORT;
        this.travelToAirport();

        // waitInQueue
        this.state = PassengerState.IN_QUEUE;
        this.departureAirport.waitInQueue(this.id);

        // showDocuments
        this.departureAirport.showDocuments(this.id);

        // boardThePlane
        this.state = PassengerState.IN_FLIGHT;
        this.plane.boardThePlane(this.id);

        // waitForEndOfFlight
        this.plane.waitForEndOfFlight(this.id);

        // atDestination
        this.state = PassengerState.AT_DESTINATION;
        this.destinationAirport.atDestination(this.id);

        // leaveThePlane
        this.plane.leaveThePlane(this.id);
    }

    private void travelToAirport() {
        try {
            Thread.sleep((new Random().nextInt(30) + 10) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
