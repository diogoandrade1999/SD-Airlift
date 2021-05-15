package main;

import departureairport.DepartureAirportPilotStub;
import plane.PlanePilotStub;
import entity.Pilot;

public class Main {

    private Main() {
        this.initSimulation();
    }

    private void initSimulation() {
        // Services
        DepartureAirportPilotStub departureAirport = new DepartureAirportPilotStub();
        PlanePilotStub plane = new PlanePilotStub();

        // Entitie
        Pilot pilot = new Pilot(departureAirport, plane);
        new Thread(pilot, "pilot").start();
    }

    public static void main(String[] args) {
        new Main();
    }
}
