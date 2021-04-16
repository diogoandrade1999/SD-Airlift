package entity;

import java.util.Random;

import destinationairport.DestinationAirportPilot;
import plane.PlanePilot;
import repository.RepositoryPilot;

public class Pilot implements Runnable {

    public PilotState state = PilotState.AT_TRANSFER_GATE;
    private DestinationAirportPilot destinationAirport;
    private PlanePilot plane;
    private RepositoryPilot repository;
    private final int totalPassengers;
    private int flyingTime;

    public Pilot(DestinationAirportPilot destinationAirport, PlanePilot plane, RepositoryPilot repository,
            int totalPassengers) {
        this.destinationAirport = destinationAirport;
        this.plane = plane;
        this.repository = repository;
        this.totalPassengers = totalPassengers;
    }

    @Override
    public void run() {
        while (true) {
            // parkAtTransferGate
            this.state = PilotState.AT_TRANSFER_GATE;
            this.repository.updatePilotState(this.state);
            if (!this.parkAtTransferGate()) {
                break;
            }

            // informPlaneReadyForBoarding
            this.state = PilotState.READY_FOR_BOARDING;
            this.repository.updatePilotState(this.state);
            this.plane.informPlaneReadyForBoarding();

            // waitForAllInBoard
            this.state = PilotState.WAIT_FOR_BOARDING;
            this.repository.updatePilotState(this.state);
            this.plane.waitForAllInBoard();

            // flyToDestinationPoint
            this.state = PilotState.FLYING_FORWARD;
            this.repository.updatePilotState(this.state);
            this.flyToDestinationPoint();

            // announceArrival
            this.state = PilotState.DEBOARDING;
            this.repository.updatePilotState(this.state);
            this.plane.announceArrival();

            // flyToDeparturePoint
            this.state = PilotState.FLYING_BACK;
            this.repository.updatePilotState(this.state);
            this.flyToDeparturePoint();
        }
    }

    private boolean parkAtTransferGate() {
        return this.destinationAirport.numberPassengersInDestination() != this.totalPassengers;
    }

    private void flyToDestinationPoint() {
        try {
            this.flyingTime = (new Random().nextInt(10) + 1) * 1000;
            Thread.sleep(flyingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void flyToDeparturePoint() {
        try {
            Thread.sleep(this.flyingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
