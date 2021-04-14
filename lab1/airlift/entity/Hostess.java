package airlift.entity;

import airlift.departureairport.DepartureAirportHostess;
import airlift.destinationairport.DestinationAirportHostess;
import airlift.plane.PlaneHostess;

public class Hostess implements Runnable {

    private HostessState state = HostessState.WAIT_FOR_NEXT_FLIGHT;
    private DepartureAirportHostess departureAirport;
    private DestinationAirportHostess destinationAirport;
    private PlaneHostess plane;
    private final int totalPassengers;
    private final int numberMinPassengers;
    private final int numberMaxPassengers;

    public Hostess(DepartureAirportHostess departureAirport, DestinationAirportHostess destinationAirport,
            PlaneHostess plane, int totalPassengers, int numberMinPassengers, int numberMaxPassengers) {
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.plane = plane;
        this.totalPassengers = totalPassengers;
        this.numberMinPassengers = numberMinPassengers;
        this.numberMaxPassengers = numberMaxPassengers;
    }

    public HostessState getState() {
        return this.state;
    }

    @Override
    public void run() {
        while (true) {
            // waitForNextFlight
            this.state = HostessState.WAIT_FOR_NEXT_FLIGHT;
            if (!this.waitForNextFlight()) {
                break;
            }
            this.plane.waitForNextFlight();

            while (true) {
                // waitForNextPassenger
                this.state = HostessState.WAIT_FOR_PASSENGER;
                boolean wait = this.waitForNextPassenger();
                this.departureAirport.waitForNextPassenger(wait);
                if (!wait) {
                    break;
                }

                // checkDocuments
                state = HostessState.CHECK_PASSENGER;
                this.departureAirport.checkDocuments();
            }

            // informPlaneReadyToTakeOff
            this.state = HostessState.READY_TO_FLY;
            this.plane.informPlaneReadyToTakeOff();
        }
    }

    private boolean waitForNextFlight() {
        return this.destinationAirport.numberPassengersInDestination()
                + this.plane.numberPassengersInPlane() != this.totalPassengers;
    }

    private boolean waitForNextPassenger() {
        /**
         * se a fila estiver vazia e o número de passageiros que já embarcarem estiver
         * entre MIN e MAX, ou não houver mais passageiros a serem transferidos, a
         * recepcionista avisa o piloto que o embarque está completo e que o voo pode
         * iniciar;
         */
        int min = this.numberMinPassengers;
        int max = this.numberMaxPassengers;
        int numberPassengersInPlane = this.plane.numberPassengersInPlane() + this.departureAirport.inCheck();
        int passengersTransferred = this.destinationAirport.numberPassengersInDestination();
        int totalTransferred = this.totalPassengers;
        int passengersInQueue = this.departureAirport.numberPassengersInQueue();
        boolean firstCondition = (numberPassengersInPlane < min)
                || (passengersInQueue != 0 && numberPassengersInPlane < max);
        boolean secondCondition = (passengersTransferred + numberPassengersInPlane != totalTransferred);
        return (firstCondition && secondCondition);
    }
}
