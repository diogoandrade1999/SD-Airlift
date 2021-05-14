package entity;

import java.util.Random;

import departureairport.DepartureAirportPassenger;
import destinationairport.DestinationAirportPassenger;
import plane.PlanePassenger;
import repository.RepositoryPassenger;

public class Passenger implements Runnable {

    private PassengerState state;
    private DepartureAirportPassenger departureAirport;
    private DestinationAirportPassenger destinationAirport;
    private PlanePassenger plane;
    private RepositoryPassenger repository;
    private int id;

    public Passenger(DepartureAirportPassenger departureAirport, DestinationAirportPassenger destinationAirport,
            PlanePassenger plane, RepositoryPassenger repository, int id) {
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.plane = plane;
        this.repository = repository;
        this.id = id;
    }

    @Override
    public void run() {
        // travelToAirport
        this.state = PassengerState.GOING_TO_AIRPORT;
        this.travelToAirport();

        // waitInQueue
        this.state = PassengerState.IN_QUEUE;
        this.repository.updatePassengerState(this.state, this.id);
        this.departureAirport.waitInQueue(this.id);

        // showDocuments
        this.departureAirport.showDocuments(this.id);

        // boardThePlane
        this.state = PassengerState.IN_FLIGHT;
        this.repository.updatePassengerState(this.state, this.id);
        this.plane.boardThePlane(this.id);

        // waitForEndOfFlight
        this.plane.waitForEndOfFlight(this.id);

        // atDestination
        this.state = PassengerState.AT_DESTINATION;
        this.repository.updatePassengerState(this.state, this.id);
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
