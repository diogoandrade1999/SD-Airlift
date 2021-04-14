package airlift.main;

import airlift.departureairport.DepartureAirport;
import airlift.destinationairport.DestinationAirport;
import airlift.plane.Plane;
import airlift.entity.Hostess;
import airlift.entity.Passenger;
import airlift.entity.Pilot;

public class Airlift {

    private AirliftLogger airliftLogger = new AirliftLogger();
    private static final int TOTAL_PASSENGERS = 21;
    private static final int NUMBER_MAX_PASSENGERS = 10;
    private static final int NUMBER_MIN_PASSENGERS = 5;
    private DepartureAirport departureAirport;
    private DestinationAirport destinationAirport;
    private Plane plane;
    private Hostess hostess;
    private Pilot pilot;
    private Passenger[] passengers;

    private Airlift() {
        this.initSimulation();
    }

    private void initSimulation() {
        // Services
        this.departureAirport = new DepartureAirport(this.airliftLogger);
        this.plane = new Plane(this.airliftLogger);
        this.destinationAirport = new DestinationAirport();

        // Entities
        this.hostess = new Hostess(this.departureAirport, this.destinationAirport, this.plane, TOTAL_PASSENGERS,
                NUMBER_MIN_PASSENGERS, NUMBER_MAX_PASSENGERS);
        this.pilot = new Pilot(this.destinationAirport, this.plane, TOTAL_PASSENGERS);

        this.passengers = new Passenger[TOTAL_PASSENGERS];
        for (int i = 0; i < TOTAL_PASSENGERS; i++) {
            this.passengers[i] = new Passenger(this.departureAirport, this.destinationAirport, this.plane, i);
        }

        // Start Log
        this.airliftLogger.setDepartureAirport(this.departureAirport);
        this.airliftLogger.setDestinationAirport(this.destinationAirport);
        this.airliftLogger.setPlane(this.plane);
        this.airliftLogger.setPilot(this.pilot);
        this.airliftLogger.setHostess(this.hostess);
        this.airliftLogger.setPassengers(this.passengers);
        this.airliftLogger.initLog();

        // Start Entities
        new Thread(hostess, "hostess").start();
        new Thread(pilot, "pilot").start();
        for (int i = 0; i < TOTAL_PASSENGERS; i++) {
            new Thread(this.passengers[i], "passenger" + i).start();
        }
    }

    public static void main(String[] args) {
        new Airlift();
    }
}
