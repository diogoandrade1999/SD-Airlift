package airlift.entity;

import java.util.Random;

import airlift.destinationairport.DestinationAirportPilot;
import airlift.plane.PlanePilot;

public class Pilot implements Runnable {

    public PilotState state = PilotState.AT_TRANSFER_GATE;
    private DestinationAirportPilot destinationAirport;
    private PlanePilot plane;
    private final int totalPassengers;

    public Pilot(DestinationAirportPilot destinationAirport, PlanePilot plane, int totalPassengers) {
        this.destinationAirport = destinationAirport;
        this.plane = plane;
        this.totalPassengers = totalPassengers;
    }

    public PilotState getState() {
        return this.state;
    }

    @Override
    public void run() {
        while (true) {
            // parkAtTransferGate
            this.state = PilotState.AT_TRANSFER_GATE;
            boolean park = this.parkAtTransferGate();
            this.plane.parkAtTransferGate(park);
            if (!park) {
                break;
            }

            // informPlaneReadyForBoarding
            this.state = PilotState.READY_FOR_BOARDING;
            this.plane.informPlaneReadyForBoarding();

            // waitForAllInBoard
            this.state = PilotState.WAIT_FOR_BOARDING;
            this.plane.waitForAllInBoard();

            // flyToDestinationPoint
            this.state = PilotState.FLYING_FORWARD;
            int flyingTime = (new Random().nextInt(10) + 1) * 1000;
            this.plane.flyToDestinationPoint(flyingTime);

            // announceArrival
            this.state = PilotState.DEBOARDING;
            this.plane.announceArrival();

            // flyToDeparturePoint
            this.state = PilotState.FLYING_BACK;
            this.plane.flyToDeparturePoint(flyingTime);
        }
    }

    private boolean parkAtTransferGate() {
        return this.destinationAirport.numberPassengersInDestination() != this.totalPassengers;
    }
}
