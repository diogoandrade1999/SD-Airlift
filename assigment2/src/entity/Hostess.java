package entity;

import departureairport.DepartureAirportHostess;
import plane.PlaneHostess;

public class Hostess implements Runnable {

    private DepartureAirportHostess departureAirport;
    private PlaneHostess plane;

    public Hostess(DepartureAirportHostess departureAirport, PlaneHostess plane) {
        this.departureAirport = departureAirport;
        this.plane = plane;
    }

    @Override
    public void run() {
        while (true) {
            // waitForNextFlight
            if (!this.plane.waitForNextFlight()) {
                break;
            }

            int passengerId = -1;
            while (true) {
                // waitForNextPassenger
                if (!this.departureAirport.waitForNextPassenger()) {
                    break;
                }

                // checkDocuments
                passengerId = this.departureAirport.checkDocuments();
            }

            // informPlaneReadyToTakeOff
            this.plane.informPlaneReadyToTakeOff(passengerId);
        }
    }
}
