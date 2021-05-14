package entity;

import departureairport.DepartureAirportHostess;
import destinationairport.DestinationAirportHostess;
import plane.PlaneHostess;
import repository.RepositoryHostess;

public class Hostess implements Runnable {

    private HostessState state;
    private DepartureAirportHostess departureAirport;
    private DestinationAirportHostess destinationAirport;
    private PlaneHostess plane;
    private RepositoryHostess repository;
    private final int totalPassengers;
    private final int numberMinPassengers;
    private final int numberMaxPassengers;

    public Hostess(DepartureAirportHostess departureAirport, DestinationAirportHostess destinationAirport,
            PlaneHostess plane, RepositoryHostess repository, int totalPassengers, int numberMinPassengers,
            int numberMaxPassengers) {
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.plane = plane;
        this.repository = repository;
        this.totalPassengers = totalPassengers;
        this.numberMinPassengers = numberMinPassengers;
        this.numberMaxPassengers = numberMaxPassengers;
    }

    @Override
    public void run() {
        while (true) {
            // waitForNextFlight
            this.state = HostessState.WAIT_FOR_NEXT_FLIGHT;
            this.repository.updateHostessState(this.state);
            if (!this.waitForNextFlight()) {
                break;
            }
            this.plane.waitForNextFlight();

            while (true) {
                // waitForNextPassenger
                this.state = HostessState.WAIT_FOR_PASSENGER;
                this.repository.updateHostessState(this.state);
                boolean wait = this.waitForNextPassenger();
                this.departureAirport.waitForNextPassenger(wait);
                if (!wait) {
                    break;
                }

                // checkDocuments
                this.state = HostessState.CHECK_PASSENGER;
                this.repository.updatePassengerInCheck(this.departureAirport.getPassengerInCheck());
                this.repository.updateHostessState(this.state);
                this.departureAirport.checkDocuments();
            }

            // informPlaneReadyToTakeOff
            this.state = HostessState.READY_TO_FLY;
            this.repository.updateHostessState(this.state);
            this.plane.informPlaneReadyToTakeOff();
        }
    }

    private boolean waitForNextFlight() {
        return this.destinationAirport.numberPassengersInDestination()
                + this.plane.numberPassengersInPlane() != this.totalPassengers;
    }

    private boolean waitForNextPassenger() {
        int min = this.numberMinPassengers;
        int max = this.numberMaxPassengers;
        int numberPassengersInPlane = this.departureAirport.numberPassengersChecked();
        int passengersTransferred = this.destinationAirport.numberPassengersInDestination();
        int totalTransferred = this.totalPassengers;
        int passengersInQueue = this.departureAirport.numberPassengersInQueue();
        boolean firstCondition = (numberPassengersInPlane < min)
                || (passengersInQueue != 0 && numberPassengersInPlane < max);
        boolean secondCondition = (passengersTransferred + numberPassengersInPlane != totalTransferred);
        return (firstCondition && secondCondition);
    }
}
