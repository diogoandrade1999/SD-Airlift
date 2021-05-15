package entity;

import java.util.Random;

import departureairport.DepartureAirportPilot;
import plane.PlanePilot;

public class Pilot implements Runnable {

    private DepartureAirportPilot departureAirport;
    private PlanePilot plane;

    public Pilot(DepartureAirportPilot departureAirport, PlanePilot plane) {
        this.departureAirport = departureAirport;
        this.plane = plane;
    }

    @Override
    public void run() {
        while (true) {
            // parkAtTransferGate
            if (!this.departureAirport.parkAtTransferGate()) {
                break;
            }

            // informPlaneReadyForBoarding
            this.plane.informPlaneReadyForBoarding();

            // waitForAllInBoard
            this.plane.waitForAllInBoard();

            // flyToDestinationPoint
            int flyingTime = (new Random().nextInt(10) + 1) * 1000;
            try {
                Thread.sleep(flyingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // announceArrival
            this.plane.announceArrival();

            // flyToDeparturePoint
            try {
                Thread.sleep(flyingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
