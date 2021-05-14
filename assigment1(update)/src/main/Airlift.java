package main;

import departureairport.DepartureAirport;
import destinationairport.DestinationAirport;
import plane.Plane;
import repository.Repository;
import entity.Hostess;
import entity.Passenger;
import entity.Pilot;

public class Airlift {

    private static final int TOTAL_PASSENGERS = 21;
    private static final int NUMBER_MAX_PASSENGERS = 10;
    private static final int NUMBER_MIN_PASSENGERS = 5;

    private Airlift() {
        this.initSimulation();
    }

    private void initSimulation() {
        // Services
        Repository repository = new Repository(TOTAL_PASSENGERS);

        DepartureAirport departureAirport = new DepartureAirport(repository, TOTAL_PASSENGERS, NUMBER_MAX_PASSENGERS,
                NUMBER_MIN_PASSENGERS);

        Plane plane = new Plane(repository, TOTAL_PASSENGERS);

        DestinationAirport destinationAirport = new DestinationAirport(repository);

        // Entities
        Hostess hostess = new Hostess(departureAirport, plane);

        Pilot pilot = new Pilot(departureAirport, plane);

        Passenger[] passengers = new Passenger[TOTAL_PASSENGERS];
        for (int i = 0; i < TOTAL_PASSENGERS; i++) {
            passengers[i] = new Passenger(departureAirport, destinationAirport, plane, i);
        }

        // Start Entities
        new Thread(hostess, "hostess").start();
        new Thread(pilot, "pilot").start();
        for (int i = 0; i < TOTAL_PASSENGERS; i++) {
            new Thread(passengers[i], "passenger" + i).start();
        }
    }

    public static void main(String[] args) {
        new Airlift();
    }
}
