package main;

import departureairport.DepartureAirportHostessStub;
import entity.Hostess;
import plane.PlaneHostessStub;

public class Main {

    private Main() {
        this.initSimulation();
    }

    private void initSimulation() {
        // Services
        DepartureAirportHostessStub departureAirport = new DepartureAirportHostessStub();
        PlaneHostessStub plane = new PlaneHostessStub();

        // Entitie
        Hostess hostess = new Hostess(departureAirport, plane);
        new Thread(hostess, "hostess").start();
    }

    public static void main(String[] args) {
        new Main();
    }
}
