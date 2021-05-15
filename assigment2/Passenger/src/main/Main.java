package main;

import departureairport.DepartureAirportPassengerStub;
import destinationairport.DestinationAirportPassengerStub;
import entity.Passenger;
import plane.PlanePassengerStub;

public class Main {

    private static final int TOTAL_PASSENGERS = 21;

    private Main() {
        this.initSimulation();
    }

    private void initSimulation() {
        // Services
        DepartureAirportPassengerStub departureAirport = new DepartureAirportPassengerStub();
        DestinationAirportPassengerStub destinationAirport = new DestinationAirportPassengerStub();
        PlanePassengerStub plane = new PlanePassengerStub();

        // Entitie
        for (int i = 0; i < TOTAL_PASSENGERS; i++) {
            Passenger passenger = new Passenger(departureAirport, destinationAirport, plane, i);
            new Thread(passenger, "passenger" + i).start();
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
