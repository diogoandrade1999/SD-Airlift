package airlift.main;

import airlift.departureairport.DepartureAirport;
import airlift.destinationairport.DestinationAirport;
import airlift.plane.Plane;
import airlift.repository.Repository;
import airlift.entity.Hostess;
import airlift.entity.Passenger;
import airlift.entity.Pilot;

public class Airlift {

    private static final int TOTAL_PASSENGERS = 21;
    private static final int NUMBER_MAX_PASSENGERS = 10;
    private static final int NUMBER_MIN_PASSENGERS = 5;

    private Airlift() {
        this.initSimulation();
    }

    private void initSimulation() {
        // Logger
        AirliftLogger airliftLogger = new AirliftLogger();

        // Services
        DepartureAirport departureAirport = new DepartureAirport();

        Plane plane = new Plane();

        DestinationAirport destinationAirport = new DestinationAirport();

        Repository repository = new Repository(airliftLogger, TOTAL_PASSENGERS);

        // Entities
        Hostess hostess = new Hostess(departureAirport, destinationAirport, plane, repository, TOTAL_PASSENGERS,
                NUMBER_MIN_PASSENGERS, NUMBER_MAX_PASSENGERS);

        Pilot pilot = new Pilot(destinationAirport, plane, repository, TOTAL_PASSENGERS);

        Passenger[] passengers = new Passenger[TOTAL_PASSENGERS];
        for (int i = 0; i < TOTAL_PASSENGERS; i++) {
            passengers[i] = new Passenger(departureAirport, destinationAirport, plane, repository, i);
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
